package com.example.tiktokclone

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import kotlin.math.log

private const val ARG_PARAM1 = "param1"

class VideoFragment : Fragment() {
    private var url: String? = null
    private var simplePlayer: SimpleExoPlayer? = null
    private var cacheDataSourceFactory: CacheDataSource.Factory? = null
    private val videoCache = App.videoCache
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var playerView : PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_PARAM1)
        }
        Log.d(TAG, "onCreate: " + url)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_video, container, false)
        playerView = root.findViewById(R.id.player_view)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(requireContext())
    }

    private fun initData(context : Context){
        simplePlayer = SimpleExoPlayer.Builder(context).build()
        playerView.player = simplePlayer
        prepare(url ?: "")
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        play()
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    private fun prepare(url : String){
        val mediaSource = ProgressiveMediaSource.Factory(viewModel.cacheDataSourceFactory).createMediaSource(
            MediaItem.fromUri(Uri.parse(url)))
        simplePlayer?.setMediaSource(mediaSource)
        simplePlayer?.prepare()
    }

    private fun play(){
        simplePlayer?.play()
    }

    private fun pause(){
        simplePlayer?.pause()
    }

    private fun release(){
        simplePlayer?.stop()
        simplePlayer?.release()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}