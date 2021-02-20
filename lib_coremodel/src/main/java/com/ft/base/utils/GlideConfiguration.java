package com.ft.base.utils;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/*@GlideModule*/
public class GlideConfiguration extends AppGlideModule {

    //缓存大小 100 * 1024 * 1024 ; // 100 MB
    public long GLIDE_CATCH_SIZE = 100 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        builder.setDiskCache(new DiskLruCacheFactory(
                context.getCacheDir().getAbsolutePath() + "/image_catch",
                GLIDE_CATCH_SIZE));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
