package com.idaguan.sports.glideh;

import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.ft.base.utils.gifHelper.CheckPicType;

import org.apache.commons.lang3.SystemUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpStreamFecther implements DataFetcher<InputStream> {

    private  Call.Factory client;
    private  GlideUrl url;
    private Call call;
    private ResponseBody responseBody;
    private InputStream stream;

    public OkHttpStreamFecther(Call.Factory client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {
        Request.Builder requestBuilder = (new Request.Builder()).url(this.url.toStringUrl());
        Iterator request = this.url.getHeaders().entrySet().iterator();

        while (request.hasNext()) {
            Map.Entry headerEnty = (Map.Entry) request.next();
            String key = (String) headerEnty.getKey();
            requestBuilder.addHeader(key, (String) headerEnty.getValue());
        }

        Request request1 = requestBuilder.build();
        this.call = this.client.newCall(request1);
        this.call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (Log.isLoggable("OkHttpFetcher", Log.DEBUG)) {
                    Log.d("OkHttpFetcher", "OkHttp failed to obtain result", e);
                }

                callback.onLoadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                OkHttpStreamFecther.this.responseBody = response.body();
                if (response.isSuccessful()) {
                    long contentLength = OkHttpStreamFecther.this.responseBody.contentLength();
                    OkHttpStreamFecther.this.stream = ContentLengthInputStream.obtain(OkHttpStreamFecther.this.responseBody.byteStream(), contentLength);

                    byte[] bytes = CheckPicType.readStream(OkHttpStreamFecther.this.stream);
                    byte[] bytesType = new byte[4];
                    for (int i = 0; i < bytesType.length; i++) {
                        bytesType[i] = bytes[i];
                    }

                    String imageType = CheckPicType.getImageType(bytesType);
                    //Log.e("listener","onRespone imageType==="+imageType+"url=="+url);
                    isGifManager.LISTENER.isGifListener(url.toStringUrl(),imageType);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                    callback.onDataReady(byteArrayInputStream);

                } else {
                    callback.onLoadFailed(new HttpException(response.message(), response.code()));
                }

        }
    });

}

    @Override
    public void cleanup() {
        try {
            if(this.stream != null) {
                this.stream.close();
            }
        } catch (IOException var2) {
            ;
        }

        if(this.responseBody != null) {
            this.responseBody.close();
        }

    }

    @Override
    public void cancel() {
        Call local = this.call;
        if(local != null) {
            local.cancel();
        }
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
