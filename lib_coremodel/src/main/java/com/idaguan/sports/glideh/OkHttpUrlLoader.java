package com.idaguan.sports.glideh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private final Call.Factory client;

    public OkHttpUrlLoader(okhttp3.Call.Factory client) {
        this.client = client;
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl model, int width, int height, @NonNull Options options) {
        return new LoadData(model,new OkHttpStreamFecther(this.client,model));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl,InputStream>{

        private static volatile Call.Factory internalClient;
        private  Call.Factory client;

        public Factory() {
            this(getInternalClient());
        }

        private static Call.Factory getInternalClient() {
            if (internalClient == null){
                Class  var0 = OkHttpUrlLoader.Factory.class;
                synchronized (OkHttpUrlLoader.Factory.class){
                    if (internalClient == null){
                        internalClient = new OkHttpClient();
                    }
                }
            }
            return internalClient;
        }

        public Factory(okhttp3.Call.Factory client){
            this.client = client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(this.client);
        }

        @Override
        public void teardown() {

        }
    }
}
