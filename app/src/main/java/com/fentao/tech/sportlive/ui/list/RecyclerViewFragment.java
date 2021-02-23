package com.fentao.tech.sportlive.ui.list;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.fentao.tech.sportlive.R;
import com.fentao.tech.sportlive.bean.VideoBean;
import com.fentao.tech.sportlive.ui.adapter.VideoRecyclerViewAdapter;
import com.fentao.tech.sportlive.ui.adapter.listener.OnItemChildClickListener;
import com.fentao.tech.sportlive.ui.base.BaseFragment;
import com.fentao.tech.sportlive.util.DataUtil;
import com.fentao.tech.sportlive.util.Tag;
import com.fentao.tech.sportlive.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: SportLive
 * @Package: com.fentao.tech.sportlive.ui.list
 * @ClassName: RecyclerViewFragment
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/22 13:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/22 13:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RecyclerViewFragment extends BaseFragment implements OnItemChildClickListener {

    protected List<VideoBean> mVideos = new ArrayList<>();
    protected VideoRecyclerViewAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLinrearLayoutManager;

    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;

    /**
     * 上次播放的位置，用于页面切换回来之后恢复播放
     */
    protected int mLastPos = mCurPos;

    @Override
    public void onItemChildClick(int position) {
        startPlay(position);
    }

    @Override
    protected void initView() {
        super.initView();

        initVideoView();

        mRecyclerView = findViewById(R.id.rv);
        mLinrearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinrearLayoutManager);

        mAdapter = new VideoRecyclerViewAdapter(mVideos);
        mAdapter.setmOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if(v != null && v == mVideoView && !mVideoView.isFullScreen()){
                    releaseVideoView();
                }
            }
        });

        View view = findViewById(R.id.add);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addData(DataUtil.getVideoList());
            }
        });


    }

    protected void initVideoView() {

        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new VideoView.OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
                if(playerState == VideoView.STATE_IDLE){
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }

            @Override
            public void onPlayStateChanged(int playState) {

            }
        });

        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);

        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);

        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));

        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void initData() {
        super.initData();
        List<VideoBean> videoList = DataUtil.getVideoList();
        mVideos.addAll(videoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    protected void pause(){
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
//        if (MainActivity.mCurrentIndex != 1)
//            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }

    protected void startPlay(int position){

        if(mCurPos == position) return;
        if(mCurPos != -1){
            releaseVideoView();
        }

        VideoBean videoBean = mVideos.get(position);
        mVideoView.setUrl(videoBean.getUrl());
        mTitleView.setTitle(videoBean.getTitle());
        View itemView = mLinrearLayoutManager.findViewByPosition(position);
        if(itemView == null) return;

        VideoRecyclerViewAdapter.VideoHolder videoHolder = (VideoRecyclerViewAdapter.VideoHolder) itemView.getTag();

        mController.addControlComponent(videoHolder.mPrepareView,true);
        Utils.removeViewFormParent(mVideoView);

        videoHolder.mPlayerContainer.addView(mVideoView,0);

        getVideoViewManager().add(mVideoView, Tag.LIST);

        mVideoView.start();
        mCurPos = position;


    }





    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if(getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }


}
