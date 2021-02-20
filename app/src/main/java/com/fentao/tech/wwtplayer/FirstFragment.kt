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
//    private val URL =
//        "https://livec.ali.huoxinglaike.com/live/beidanlaonanren.flv?auth_key=1612496758904-19951c36f7b14b918305e8dc52bddad0-0-d3e0371e232301661b7edb8b2ed672ae"
//    private val URL = "http://ivi.bupt.edu.cn/hls/cctv6.m3u8";





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


    }


}