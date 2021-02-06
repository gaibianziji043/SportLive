package com.fentao.tech.wwtplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.fentao.tech.player_java.player.AbstractPlayer
import com.fentao.tech.player_java.player.VideoView
import com.fentao.tech.player_java.utils.L
import com.fentao.tech.player_ui.StandardVideoController
import com.fentao.tech.player_ui.component.*
import com.fentao.tech.wwtplayer.widget.component.DebugInfoView
import com.fentao.tech.wwtplayer.widget.component.PlayerMonitor

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val THUMB =
        "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg"
    private val URL =
        "https://livec.ali.huoxinglaike.com/live/beidanlaonanren.flv?auth_key=1612496758904-19951c36f7b14b918305e8dc52bddad0-0-d3e0371e232301661b7edb8b2ed672ae"

    var mVideoView:VideoView<AbstractPlayer>? = null;



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }

       mVideoView = view.findViewById(R.id.player)

        val controller = this.activity?.applicationContext?.let { StandardVideoController(it) }
        //根据屏幕方向自动进入/退出全屏
        //根据屏幕方向自动进入/退出全屏
        controller?.setEnableOrientation(true)

        val prepareView = this.activity?.applicationContext?.let { PrepareView(it) } //准备播放界面

        val thumb: ImageView? = prepareView?.findViewById(R.id.thumb) //封面图


//        Glide.with(this).load( THUMB).into(thumb)
        controller?.addControlComponent(prepareView)

        controller?.addControlComponent(this.activity?.applicationContext?.let { CompleteView(it) }) //自动完成播放界面


        controller?.addControlComponent(this.activity?.applicationContext?.let { ErrorView((it)) }) //错误界面


        val titleView = this.activity?.applicationContext?.let { TitleView(it) } //标题栏

        controller?.addControlComponent(titleView)

        //根据是否为直播设置不同的底部控制条

        //根据是否为直播设置不同的底部控制条
        val isLive: Boolean = true//intent.getBooleanExtra(IntentKeys.IS_LIVE, false)
        if (isLive) {
            controller?.addControlComponent(this.activity?.applicationContext?.let {
                LiveControlView(
                    it
                )
            }) //直播控制条
        } else {
            val vodControlView =
                this.activity?.applicationContext?.let { VodControlView(it) } //点播控制条
            //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
            controller?.addControlComponent(vodControlView)
        }

        val gestureControlView =
            this.activity?.applicationContext?.let { GestureView(it) } //滑动控制视图

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
        controller?.addControlComponent(DebugInfoView(this.activity?.applicationContext))
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
}