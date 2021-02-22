package com.fentao.tech.sportlive.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.fentao.tech.player_java.player.VideoView;
import com.fentao.tech.player_java.utils.PlayerUtils;
import com.fentao.tech.player_ui.StandardVideoController;
import com.fentao.tech.player_ui.component.CompleteView;
import com.fentao.tech.player_ui.component.ErrorView;
import com.fentao.tech.player_ui.component.GestureView;
import com.fentao.tech.player_ui.component.PrepareView;
import com.fentao.tech.player_ui.component.TitleView;
import com.fentao.tech.player_ui.component.VodControlView;
import com.fentao.tech.sportlive.R;
import com.fentao.tech.sportlive.util.DataUtil;

public class FullScreenActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private StandardVideoController standardVideoController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoView = new VideoView(this);
        adaptCutoutAboveAndroidP();
        setContentView(mVideoView);


        initView();
    }

    private void initView() {
        mVideoView.startFullScreen();
        mVideoView.setUrl(DataUtil.SAMPLE_URL);
        standardVideoController = new StandardVideoController(this);
        standardVideoController.addControlComponent(new CompleteView(this));
        standardVideoController.addControlComponent(new ErrorView(this));
        standardVideoController.addControlComponent(new PrepareView(this));

        TitleView titleView = new TitleView(this);
        // 我这里改变了返回按钮的逻辑，我不推荐这样做，我这样只是为了方便，
        // 如果你想对某个组件进行定制，直接将该组件的代码复制一份，改成你想要的样子
        titleView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle(getString(R.string.str_fullscreen_directly));
        standardVideoController.addControlComponent(titleView);
        VodControlView vodControlView = new VodControlView(this);
        // 我这里隐藏了全屏按钮并且调整了边距，我不推荐这样做，我这样只是为了方便，
        // 如果你想对某个组件进行定制，直接将该组件的代码复制一份，改成你想要的样子
        vodControlView.findViewById(R.id.fullscreen).setVisibility(View.GONE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vodControlView.findViewById(R.id.total_time).getLayoutParams();
        lp.rightMargin = PlayerUtils.dp2px(this, 16);
        standardVideoController.addControlComponent(vodControlView);
        standardVideoController.addControlComponent(new GestureView(this));
        mVideoView.setVideoController(standardVideoController);
        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_16_9);
        mVideoView.start();
    }

    private void adaptCutoutAboveAndroidP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!standardVideoController.isLocked()){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVideoView != null){
            mVideoView.release();
        }
    }
}