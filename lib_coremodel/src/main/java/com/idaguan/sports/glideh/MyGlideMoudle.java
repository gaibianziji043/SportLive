package com.idaguan.sports.glideh;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;

@GlideModule
public class MyGlideMoudle extends AppGlideModule {
    //缓存大小 100 * 1024 * 1024 ; // 100 MB
    public long GLIDE_CATCH_SIZE = 100 * 1024 * 1024;

    public MyGlideMoudle() {
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
       // super.applyOptions(context, builder);
        builder.setDiskCache(new DiskLruCacheFactory(
                context.getCacheDir().getAbsolutePath() + "/image_catch",
                GLIDE_CATCH_SIZE));
        Log.e("progress","applyOptions");
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

        //registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        Log.e("progress","registerComponents");
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));

    }
}
