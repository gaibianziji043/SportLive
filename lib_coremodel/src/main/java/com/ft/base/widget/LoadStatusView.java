package com.ft.base.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ft.base.net.R;
import com.ft.base.utils.GlideRoundTransform;

public class LoadStatusView extends RelativeLayout {

    private TextView btn_reload;
    private onReloadClickListener reloadClickListener;
    private ImageView iv_statusPicture;
    private TextView tv_message;
    //  private AnimationDrawable mAnimationDrawable;

    private ImageView load_gif;
    private LinearLayout ll_load;
    private LinearLayout ll_load_status;
    private RelativeLayout.LayoutParams params;

    public LoadStatusView(Context context) {
        this(context,null);
    }

    public LoadStatusView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.widget_load_status, this);
        btn_reload = view.findViewById(R.id.btn_reload);
        tv_message = view.findViewById(R.id.tv_message);
        iv_statusPicture = view.findViewById(R.id.iv_statusPicture);
        btn_reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reloadClickListener !=null){
                    reloadClickListener.OnReloadClick();
                }
            }
        });

        ll_load = view.findViewById(R.id.ll_load);
        load_gif = view.findViewById(R.id.iv_load_gif);
        ll_load_status = view.findViewById(R.id.ll_load_status);

        ll_load_status.setBackgroundColor(getResources().getColor(R.color.white));

        Glide.with(context)
                .load(R.drawable.load_bg)
                .into(load_gif);

        /*mAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.loading_anim);
        iv_statusPicture.setBackgroundDrawable(mAnimationDrawable);
        mAnimationDrawable.start();*/


        hideLoadStatus();
    }


    public void hideLoadStatus(){
        //  mAnimationDrawable.stop();
        setVisibility(View.GONE);
    }
    public void showReload(){
        ll_load.setVisibility(View.VISIBLE);
        load_gif.setVisibility(View.GONE);
        btn_reload.setVisibility(View.VISIBLE);
        //   mAnimationDrawable.stop();
        iv_statusPicture.setBackgroundResource(R.drawable.nodata_img);
        tv_message.setText(R.string.load_error);
        setVisibility(View.VISIBLE);
    }

    public boolean isShow() {
        int visibility = getVisibility();
        if (visibility == View.GONE) {
            return false;
        } else {
            return true;
        }
    }

    public void showNoData(){
        ll_load.setVisibility(View.VISIBLE);
        load_gif.setVisibility(View.GONE);
        btn_reload.setVisibility(View.GONE);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            iv_statusPicture.setBackground(mAnimationDrawable);
        } else {
            iv_statusPicture.setBackgroundDrawable(mAnimationDrawable);
        }
        mAnimationDrawable.start();*/
        tv_message.setText(R.string.loading_tip);
        iv_statusPicture.setBackgroundResource(R.drawable.nodata_img);
        setVisibility(View.VISIBLE);
    }

    public void showNetworkError() {
        ll_load.setVisibility(View.VISIBLE);
        load_gif.setVisibility(View.GONE);
        btn_reload.setVisibility(View.VISIBLE);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            iv_statusPicture.setBackground(mAnimationDrawable);
        } else {
            iv_statusPicture.setBackgroundDrawable(mAnimationDrawable);
        }
        mAnimationDrawable.start();*/
        iv_statusPicture.setBackgroundResource(R.drawable.nonetwork_img);
        tv_message.setText(R.string.network_error);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.VISIBLE);
            }
        }, 20);
//        setVisibility(View.VISIBLE);
    }

    public void setonReloadClickListener(onReloadClickListener listener){
        this.reloadClickListener =listener;
    }

    /**
     * 首次加载显示gif图
      */
    public void showLoading() {
        ll_load_status.setBackgroundColor(getResources().getColor(R.color.white));
        ll_load.setVisibility(View.GONE);
        load_gif.setVisibility(View.VISIBLE);
        setVisibility(View.VISIBLE);
    }

    /**
     * 适配首页热门背景颜色，默认为白色
     * @param color
     */
    public void showLoading(int color) {
        ll_load_status.setBackgroundColor(getResources().getColor(color));
        ll_load.setVisibility(GONE);
        load_gif.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
    }

    /**
     * 适配首页热门网络加载失败显示dia
     * @param color  背景颜色
     * @param visibility gif是否显示
     */
   public void showLoading(int color, int visibility) {
        ll_load_status.setBackgroundColor(getResources().getColor(color));
        load_gif.setVisibility(visibility);

        if (visibility == GONE) {
            ll_load.setVisibility(VISIBLE);
            setVisibility(VISIBLE);
        } else {
            ll_load.setVisibility(GONE);
            setVisibility(GONE);
        }
    }

    public  interface onReloadClickListener {
        void OnReloadClick();
    }
}
