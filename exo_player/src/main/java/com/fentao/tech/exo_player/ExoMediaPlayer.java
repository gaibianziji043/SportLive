package com.fentao.tech.exo_player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.fentao.tech.player_java.player.AbstractPlayer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.Map;

/**
 * @ProjectName: WWTPlayer
 * @Package: com.fentao.tech.exo_player
 * @ClassName: ExoMediaPlayer
 * @Description: Exo播放器封装
 * @Author: allen
 * @CreateDate: 2021/2/5 19:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/5 19:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ExoMediaPlayer extends AbstractPlayer implements VideoListener, Player.EventListener {

    protected Context mAppContext;
    protected ExoMediaSourceHelper mediaSourceHelper;

    protected SimpleExoPlayer mInternalPalyer;

    private LoadControl mLoadControl;
    private RenderersFactory mRenderersFactory;
    private TrackSelector mTrackSelector;

    protected MediaSource mediaSource;
    private boolean mIsPreparing;
    private boolean mIsBuffering;
    private PlaybackParameters mSpeedPlaybackParameters;
    private int mLastReportedPlaybackState = Player.STATE_IDLE;
    private boolean mLastReportedPlayWhenReady = false;


    public ExoMediaPlayer(Context mAppContext) {
        this.mAppContext = mAppContext;
        this.mediaSourceHelper = ExoMediaSourceHelper.getInstance(mAppContext);
    }



    @Override
    public void initPlayer() {
        mInternalPalyer = new SimpleExoPlayer.Builder(
                mAppContext,
                mRenderersFactory == null?mRenderersFactory = new DefaultRenderersFactory(mAppContext):mRenderersFactory,
                mTrackSelector == null?mTrackSelector = new DefaultTrackSelector(mAppContext):mTrackSelector,
                mLoadControl == null?mLoadControl = new DefaultLoadControl():mLoadControl,
                DefaultBandwidthMeter.getSingletonInstance(mAppContext),
                Util.getLooper(),
                new AnalyticsCollector(Clock.DEFAULT),
                true,
                Clock.DEFAULT).build();

        setOptions();

        mInternalPalyer.addListener(this);
        mInternalPalyer.addVideoListener(this);

    }


    public void setmLoadControl(LoadControl mLoadControl) {
        this.mLoadControl = mLoadControl;
    }

    public void setmRenderersFactory(RenderersFactory mRenderersFactory) {
        this.mRenderersFactory = mRenderersFactory;
    }

    public void setmTrackSelector(TrackSelector mTrackSelector) {
        this.mTrackSelector = mTrackSelector;
    }



    @Override
    public void setDataSource(String path, Map<String, String> headers) {
        mediaSource = mediaSourceHelper.getMediaSource(path, headers);
    }

    @Override
    public void setDataSource(AssetFileDescriptor fd) {

    }

    @Override
    public void start() {
        if(mInternalPalyer == null){
            return;
        }

        mInternalPalyer.setPlayWhenReady(true);
    }



    @Override
    public void pause() {
        if(mInternalPalyer == null){
            return;
        }

        mInternalPalyer.setPlayWhenReady(false);
    }



    @Override
    public void stop() {
        if(mInternalPalyer == null){
            return;
        }

        mInternalPalyer.stop();
    }



    @Override
    public void prepareAsync() {
        if(mInternalPalyer == null)
            return;

        if(mediaSource == null) return;

        if(mSpeedPlaybackParameters != null){
            mInternalPalyer.setPlaybackParameters(mSpeedPlaybackParameters);
        }

        mIsPreparing = true;
        mediaSource.addEventListener(new Handler(),mediaSourceEventListener);
        mInternalPalyer.prepare(mediaSource);

    }

    private MediaSourceEventListener mediaSourceEventListener = new MediaSourceEventListener() {

        @Override
        public void onReadingStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            if(mPlayerEventListener != null && mIsPreparing){
                mPlayerEventListener.onPrepared();
            }
        }
    };

    @Override
    public void reset() {

        if(mInternalPalyer != null){
            mInternalPalyer.stop(true);
            mInternalPalyer.setVideoSurface(null);
            mIsPreparing = false;
            mIsBuffering = false;
            mLastReportedPlaybackState = Player.STATE_IDLE;
            mLastReportedPlayWhenReady = false;
        }

    }

    @Override
    public boolean isPlaying() {

        if(mInternalPalyer == null){
            return false;
        }
        int state = mInternalPalyer.getPlaybackState();
        switch (state){

            case Player.STATE_BUFFERING:
            case Player.STATE_READY:
                return mInternalPalyer.getPlayWhenReady();

            case Player.STATE_IDLE:
            case Player.STATE_ENDED:
            default:
                return false;
        }


    }

    @Override
    public void seekTo(long time) {
        if(mInternalPalyer == null)
        {
            return;
        }

        mInternalPalyer.seekTo(time);
    }




    @Override
    public void release() {
        if(mInternalPalyer != null){
            mInternalPalyer.removeListener(this);
            mInternalPalyer.removeVideoListener(this);
            final SimpleExoPlayer palyer = mInternalPalyer;
            mInternalPalyer = null;
            new Thread(){
                @Override
                public void run() {
                    palyer.release();
                }
            }.start();
        }

        mIsPreparing = false;
        mIsBuffering = false;
        mLastReportedPlaybackState = Player.STATE_IDLE;
        mLastReportedPlayWhenReady = false;
        mSpeedPlaybackParameters = null;
    }



    @Override
    public long getCurrentPosition() {
        if(mInternalPalyer == null){
            return 0;
        }
        return mInternalPalyer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        if(mInternalPalyer == null){
            return 0;
        }
        return mInternalPalyer.getDuration();
    }

    @Override
    public int getBufferedPercentage() {
        return mInternalPalyer == null?0:mInternalPalyer.getBufferedPercentage();
    }

    @Override
    public void setSurface(Surface surface) {
        if(mInternalPalyer != null){
            mInternalPalyer.setVideoSurface(surface);
        }
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        if(holder == null){
            setSurface(null);
        }else{
            setSurface(holder.getSurface());
        }
    }

    @Override
    public void setVolume(float v1, float v2) {
        if(mInternalPalyer != null){
            mInternalPalyer.setVolume((v1+v2)/2);
        }
    }

    @Override
    public void setLooping(boolean isLooping) {
        if(mInternalPalyer != null){
            mInternalPalyer.setRepeatMode(isLooping?Player.REPEAT_MODE_ALL:Player.REPEAT_MODE_OFF);
        }
    }


    @Override
    public void setOptions() {
        mInternalPalyer.setPlayWhenReady(true);
    }

    @Override
    public void setSpeed(float speed) {

        PlaybackParameters playbackParameters = new PlaybackParameters(speed);
        mSpeedPlaybackParameters = playbackParameters;
        if(mInternalPalyer != null){
            mInternalPalyer.setPlaybackParameters(playbackParameters);
        }
    }


    @Override
    public float getSpeed() {
        if(mSpeedPlaybackParameters != null){
            return mSpeedPlaybackParameters.speed;
        }
        return 1f;
    }

    @Override
    public long getTcpSpeed() {
        return 0;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(mInternalPalyer == null) return;

        if(mIsPreparing) return;

        if(mLastReportedPlayWhenReady != playWhenReady || mLastReportedPlaybackState != playbackState){
            switch (playbackState){
                case Player.STATE_BUFFERING:
                    mPlayerEventListener.onInfo(MEDIA_INFO_BUFFERING_START,getBufferedPercentage());
                    break;
                case Player.STATE_READY:
                    if(mIsBuffering){
                        mPlayerEventListener.onInfo(MEDIA_INFO_BUFFERING_END,getBufferedPercentage());
                        mIsBuffering = false;
                    }
                    break;
                case Player.STATE_ENDED:
                    mPlayerEventListener.onCompletetion();
                    break;
            }

            mLastReportedPlaybackState = playbackState;
            mLastReportedPlayWhenReady = playWhenReady;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        if(mPlayerEventListener != null){
            mPlayerEventListener.onError();
        }
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        if(mPlayerEventListener != null){
            mPlayerEventListener.onVideoSizeChanged(width, height);

            if(unappliedRotationDegrees > 0){
                mPlayerEventListener.onInfo(MEDIA_INFO_VIDEO_ROTATION_CHANGED,unappliedRotationDegrees);
            }

        }
    }

    @Override
    public void onRenderedFirstFrame() {
        if(mPlayerEventListener != null && mIsPreparing){
            mPlayerEventListener.onInfo(MEDIA_INFO_BUFFERING_START,0);
            mIsPreparing = false;
        }
    }
}
