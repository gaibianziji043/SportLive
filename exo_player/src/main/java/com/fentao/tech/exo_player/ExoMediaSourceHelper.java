package com.fentao.tech.exo_player;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import static com.google.android.exoplayer2.util.Util.inferContentType;

/**
 * @ProjectName: WWTPlayer
 * @Package: com.fentao.tech.exo_player
 * @ClassName: ExoMediaSourceHelper
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/5 20:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/5 20:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public final class ExoMediaSourceHelper {

    private static ExoMediaSourceHelper sInstance;
    private static String mUserAgent;
    private Context mAppContext;
    private HttpDataSource.Factory mHttpDataSpurceFactory;

    private Cache cache;

    private ExoMediaSourceHelper(Context context){
        mAppContext = context.getApplicationContext();
        mUserAgent = Util.getUserAgent(mAppContext,mAppContext.getApplicationInfo().name);
    }


    public static ExoMediaSourceHelper getInstance(Context context){

        if(sInstance == null){
            synchronized (ExoMediaSourceHelper.class){
                if(sInstance == null){
                    sInstance = new ExoMediaSourceHelper(context);
                }
            }
        }
        return sInstance;
    }


    public MediaSource getMediaSource(String uri){
        return getMediaSource(uri,null,false);
    }

    public MediaSource getMediaSource(String uri, Map<String,String> headers){
        return getMediaSource(uri,headers,false);
    }

    public MediaSource getMediaSource(String uri,boolean isCache){
        return getMediaSource(uri,null,isCache);
    }

    public MediaSource getMediaSource(String uri,Map<String,String> headers,boolean isCache){
        Uri contenturi = Uri.parse(uri);

        if("rtmp".equals(contenturi.getScheme())){
            return new ProgressiveMediaSource.Factory((new RtmpDataSourceFactory(null)))
                    .createMediaSource(contenturi);
        }

        int contentType = inferContentType(uri);

        DataSource.Factory factory;

        if(isCache){
            factory = getCacheDataSourceFactory();
        }else{
            factory = getDataSourceFactory();
        }

        if(mHttpDataSpurceFactory !=  null){
            setHeaders(headers);
        }

        switch (contentType){

            case C.TYPE_DASH:
                    return new DashMediaSource.Factory(factory).createMediaSource(contenturi);
            case C.TYPE_SS:
                    return new SsMediaSource.Factory(factory).createMediaSource(contenturi);
            case C.TYPE_HLS:
                    return new HlsMediaSource.Factory(factory).createMediaSource(contenturi);

            default:
            case C.TYPE_OTHER:
                    return new ProgressiveMediaSource.Factory(factory).createMediaSource(contenturi);
        }
    }

    private void setHeaders(Map<String, String> headers) {

        if(headers != null && headers.size() >0){
            for(Map.Entry<String,String> header:headers.entrySet()){
                String key = header.getKey();
                String value = header.getValue();
                if (TextUtils.equals(key,"User-Agent")){
                    if(!TextUtils.isEmpty(value)){
                        Field userAgentField = null;
                        try {
                            userAgentField = mHttpDataSpurceFactory.getClass().getDeclaredField("userAgent");
                            userAgentField.setAccessible(true);
                            userAgentField.set(mHttpDataSpurceFactory,value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }else{
                    mHttpDataSpurceFactory.getDefaultRequestProperties().set(key,value);
                }
            }
        }

    }


    private DataSource.Factory getCacheDataSourceFactory(){
        if(cache == null){
            cache = newChche();
        }

        return new CacheDataSourceFactory(cache,getDataSourceFactory(),
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
                );
    }

    private DataSource.Factory getDataSourceFactory() {
        return new DefaultDataSourceFactory(mAppContext,getHttpDataSourceFactory());
    }

    private DataSource.Factory getHttpDataSourceFactory() {
        if(mHttpDataSpurceFactory == null){
            mHttpDataSpurceFactory = new DefaultHttpDataSourceFactory(
                mUserAgent,
                    null,
                    DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                    DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                    true
            );
        }
        return mHttpDataSpurceFactory;
    }


    private Cache newChche(){
        return new SimpleCache(
                new File(mAppContext.getExternalCacheDir(),"exo-video-cache"),
                new LeastRecentlyUsedCacheEvictor(512*1024*1024),
                new ExoDatabaseProvider(mAppContext)

        );
    }






}
