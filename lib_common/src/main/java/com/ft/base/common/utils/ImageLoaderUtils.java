package com.ft.base.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ft.base.R;

import java.io.File;

import static com.idaguan.sports.glideh.GlideOptions.bitmapTransform;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.error(error);
        options.dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.placeholder(R.drawable.ic_image_loading);
        options.error(R.drawable.ic_empty_picture);

        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.placeholder(R.drawable.ic_image_loading);
        options.error(R.drawable.ic_empty_picture);

        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }
    public static void displayNoBg(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }
    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.placeholder(R.drawable.ic_image_loading);
        options.error(R.drawable.ic_empty_picture);

        Glide.with(context).asBitmap().load(url)
                .apply(options)
                .thumbnail(0.5f)
                .into(imageView);
    }
    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.placeholder(R.drawable.ic_image_loading);
        options.error(R.drawable.ic_empty_picture);
        options.format(DecodeFormat.PREFER_ARGB_8888);

        Glide.with(context).asBitmap().load(url)
                .apply(options)
                .into(imageView);
    }
    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.dontAnimate();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.placeholder(R.drawable.ic_image_loading);
        options.error(R.drawable.ic_empty_picture);

        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }
    public static void displayRound(Context context,ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = bitmapTransform(new CircleCrop());
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.error(R.drawable.toux2);

        Glide.with(context).load(url)
                .apply(options).into(imageView);
    }
    public static void displayRound(Context context, ImageView imageView, Drawable resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = bitmapTransform(new CircleCrop());
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.error(R.drawable.toux2);
        options.centerCrop();

        Glide.with(context).load(resId)
                .apply(options).into(imageView);
    }

}
