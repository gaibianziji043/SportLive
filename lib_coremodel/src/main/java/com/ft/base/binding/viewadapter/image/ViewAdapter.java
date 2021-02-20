package com.ft.base.binding.viewadapter.image;


import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ft.base.net.R;
import com.ft.base.utils.GifCallBackListener;
import com.ft.base.utils.GlideRoundTransform;
import com.ft.base.utils.StringUtils;
import com.idaguan.sports.glideh.GlideApp;
import com.idaguan.sports.glideh.isGifManager;

import static com.idaguan.sports.glideh.GlideOptions.bitmapTransform;

//import static com.ft.base.utils.GlideOptions.bitmapTransform;


public final class ViewAdapter {
    @BindingAdapter(value = {"url", "radius", "defaultRes", "isCircle","isSetFitCenter", "isPlayGif"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int radius, int defaultRes,
                                   boolean isCircle, boolean isSetFitCenter, boolean isPlayGif) {

        RequestOptions options = bitmapTransform(new GlideRoundTransform(radius));
        if (isCircle) {
            options = bitmapTransform(new CircleCrop());
        }
        options.placeholder(defaultRes); // 占位图
        options.error(defaultRes);
        if (isSetFitCenter) {
            options.fitCenter();
        }
        //使用Glide框架加载图片
        if (isPlayGif) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = bitmapTransform(new GlideRoundTransform(2));
            options.placeholder(R.drawable.drawable_default_icon_6);
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }


    @BindingAdapter(value = {"res"}, requireAll = false)
    public static void setImageRes(ImageView imageView, int res) {
        imageView.setImageResource(res);
    }

    @BindingAdapter(value = {"adjustWH"})
    public static void adjustWH(ImageView imageView, float ratio) {
        int screenWidth = ScreenUtils.getScreenWidth(); //屏幕宽度
        int viewScreenWidth = screenWidth / 2;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        layoutParams.width = viewScreenWidth;
        layoutParams.height = Math.min((int) (ratio * viewScreenWidth), (int) (1.5 * viewScreenWidth));
        imageView.setLayoutParams(layoutParams);
        imageView.requestLayout();
    }

    @BindingAdapter(value = {"gifUrl", "radius", "defaultRes", "isCircle", "isGifCallBack"}, requireAll = false)
    public static void setGlideUrl(ImageView imageView, final String gifUrl, int radius, int defaultRes, boolean isCircle, final GifCallBackListener isGifCallBack) {

        loadGifUrlPic(radius, isCircle, defaultRes, gifUrl, isGifCallBack, imageView);

    }

    public static void loadGifUrlPic(int radius, boolean isCircle, int defaultRes, String gifUrl, GifCallBackListener isGifCallBack, ImageView imageView) {
        RequestOptions options = bitmapTransform(new GlideRoundTransform(radius));
        if (isCircle) {
            options = bitmapTransform(new CircleCrop());
        }
        options.placeholder(defaultRes); // 占位图
        options.error(defaultRes);
        //使用Glide框架加载图片

        final RequestOptions finalOptions = options;

        if (!StringUtils.isEmpty(gifUrl)) {
            if (isGifManager.isGifSet.contains(gifUrl)) {
                if (isGifCallBack != null) {
                    isGifCallBack.isGifCallback(gifUrl, true);
                }
            } else {
                if (isGifCallBack != null) {
                    isGifCallBack.isGifCallback(gifUrl, false);
                }
            }

            isGifManager.addListener(gifUrl, new isGifManager.isGifListener() {
                @Override
                public void isGifListener(String url, String type) {
                    //Log.e("listener","type=="+type +"url=="+url);
                    if ("gif".equals(type)) {
                        if (isGifCallBack != null) {
                            isGifCallBack.isGifCallback(url, true);
                        }
                        isGifManager.isGifSet.add(url);
                    }
                }
            });
            GlideApp.with(imageView.getContext()).asBitmap().load(new GlideUrl(gifUrl)).apply(finalOptions).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        }
    }

    @BindingAdapter(value = {"startAnimator"})
    public static void showAnimator(ImageView imageView, boolean startAnimator) {
        if (startAnimator) {
            ScaleAnimation animation = new ScaleAnimation(0.8f, 1.2f, 0.8f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, 1, 0.5f);
            animation.setDuration(100);
            //设置持续时间
            animation.setFillAfter(false);
            //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
            animation.setRepeatCount(0);
            //设置循环次数，0为1次
            imageView.startAnimation(animation);
        }
    }
}

