package com.idaguan.sports.glideh;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

public class GlideImageViewTarget extends DrawableImageViewTarget {
    private  String url;

    public GlideImageViewTarget(ImageView view) {
        super(view);
    }

    public GlideImageViewTarget(ImageView view,String url) {
        super(view);
        this.url = url;
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        super.onLoadStarted(placeholder);
        Log.e("progress","onLoadStarted");
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        Log.e("progress","onLoadFailed");
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(url);
        }
        super.onLoadFailed(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        Log.e("progress","onResourceReady");
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(url);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(url);
        }
        super.onResourceReady(resource, transition);
    }
}
