package com.fentao.tech.sportlive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.fentao.tech.player_java.player.AbstractPlayer
import com.fentao.tech.player_java.player.VideoView
import com.fentao.tech.player_java.utils.L
import com.fentao.tech.player_ui.StandardVideoController
import com.fentao.tech.player_ui.component.*
import com.fentao.tech.sportlive.widget.component.DebugInfoView
import com.fentao.tech.sportlive.widget.component.PlayerMonitor

class PlayerActivity : AppCompatActivity() {
    private val URL = "https://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4"

    var mVideoView: VideoView<AbstractPlayer>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        mVideoView = findViewById(R.id.player)

        val controller =   StandardVideoController(this)
        //根据屏幕方向自动进入/退出全屏
        //根据屏幕方向自动进入/退出全屏
        controller?.setEnableOrientation(true)

        val prepareView =   PrepareView(this)  //准备播放界面

        val thumb: ImageView? = prepareView?.findViewById(R.id.thumb) //封面图


//        Glide.with(this).load( THUMB).into(thumb)
        controller?.addControlComponent(prepareView)

        controller?.addControlComponent( CompleteView(this) ) //自动完成播放界面


        controller?.addControlComponent(  ErrorView(this)) //错误界面


        val titleView =   TitleView(this)  //标题栏

        controller?.addControlComponent(titleView)

        //根据是否为直播设置不同的底部控制条

        //根据是否为直播设置不同的底部控制条
        val isLive: Boolean = true//intent.getBooleanExtra(IntentKeys.IS_LIVE, false)
        if (isLive) {
            controller?.addControlComponent(
                LiveControlView(
                    this
                )
            ) //直播控制条
        } else {
            val vodControlView =
                  VodControlView(this)  //点播控制条
            //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
            controller?.addControlComponent(vodControlView)
        }

        val gestureControlView =
              GestureView(this)  //滑动控制视图

        controller?.addControlComponent(gestureControlView)
        //根据是否为直播决定是否需要滑动调节进度
        //根据是否为直播决定是否需要滑动调节进度
        controller?.setCanChangePosition(!isLive)

        //设置标题

        //设置标题
//        val title: String = intent.getStringExtra(IntentKeys.TITLE)
//        titleView.setTitle(title)

        //注意：以上组件如果你想单独定制，我推荐你把源码复制一份出来，然后改成你想要的样子。
        //改完之后再通过addControlComponent添加上去
        //你也可以通过addControlComponent添加一些你自己的组件，具体实现方式参考现有组件的实现。
        //这个组件不一定是View，请发挥你的想象力😃

        //如果你不需要单独配置各个组件，可以直接调用此方法快速添加以上组件
//            controller.addDefaultControlComponent(title, isLive);

        //竖屏也开启手势操作，默认关闭
//            controller.setEnableInNormal(true);
        //滑动调节亮度，音量，进度，默认开启
//            controller.setGestureEnabled(false);
        //适配刘海屏，默认开启
//            controller.setAdaptCutout(false);

        //在控制器上显示调试信息

        //注意：以上组件如果你想单独定制，我推荐你把源码复制一份出来，然后改成你想要的样子。
        //改完之后再通过addControlComponent添加上去
        //你也可以通过addControlComponent添加一些你自己的组件，具体实现方式参考现有组件的实现。
        //这个组件不一定是View，请发挥你的想象力😃

        //如果你不需要单独配置各个组件，可以直接调用此方法快速添加以上组件
//            controller.addDefaultControlComponent(title, isLive);

        //竖屏也开启手势操作，默认关闭
//            controller.setEnableInNormal(true);
        //滑动调节亮度，音量，进度，默认开启
//            controller.setGestureEnabled(false);
        //适配刘海屏，默认开启
//            controller.setAdaptCutout(false);

        //在控制器上显示调试信息
        controller?.addControlComponent(DebugInfoView(this))
        //在LogCat显示调试信息
        //在LogCat显示调试信息
        controller?.addControlComponent(PlayerMonitor())

        //如果你不想要UI，不要设置控制器即可

        //如果你不想要UI，不要设置控制器即可
        mVideoView?.setVideoController(controller)

        mVideoView?.setUrl(URL)

        //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());
        //播放状态监听

        //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());
        //播放状态监听
        mVideoView?.addOnStateChangeListener(mOnStateChangeListener)

        //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
        //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
        //使用ExoPlayer解码
//            mVideoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //使用MediaPlayer解码
//            mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());


        //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
        //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
        //使用ExoPlayer解码
//            mVideoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //使用MediaPlayer解码
//            mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());
        mVideoView?.start()


    }



    private val mOnStateChangeListener: VideoView.OnStateChangeListener =
        object : VideoView.SimpleOnStateChangeListener() {
            override fun onPlayerStateChanged(playerState: Int) {
                when (playerState) {
                    VideoView.PLAYER_NORMAL -> {
                    }
                    VideoView.PLAYER_FULL_SCREEN -> {
                    }
                }
            }

            override fun onPlayStateChanged(playState: Int) {
                when (playState) {
                    VideoView.STATE_IDLE -> {
                    }
                    VideoView.STATE_PREPARING -> {
                    }
                    VideoView.STATE_PREPARED -> {
                    }
                    VideoView.STATE_PLAYING -> {
                        //需在此时获取视频宽高
                        val videoSize: IntArray? = mVideoView?.getVideoSize()
                        L.d("视频宽：" + videoSize!![0])
                        L.d("视频高：" + videoSize[1])
                    }
                    VideoView.STATE_PAUSED -> {
                    }
                    VideoView.STATE_BUFFERING -> {
                    }
                    VideoView.STATE_BUFFERED -> {
                    }
                    VideoView.STATE_PLAYBACK_COMPLETED -> {
                    }
                    VideoView.STATE_ERROR -> {
                    }
                }
            }
        }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}