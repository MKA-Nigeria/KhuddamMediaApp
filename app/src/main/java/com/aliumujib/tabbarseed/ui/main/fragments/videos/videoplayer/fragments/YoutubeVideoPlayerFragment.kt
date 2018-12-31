package com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseFragment


class YoutubeVideoPlayerFragment  : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoID = arguments!!.getString(VIDEO_ID)



    }

    companion object {
        private const val VIDEO_ID = "VIDEO_ID"
        val TAG = YoutubeVideoPlayerFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(videoId: String) =
                YoutubeVideoPlayerFragment().apply {
                    arguments = Bundle().apply {
                        putString(VIDEO_ID, videoId)
                    }
                }
    }
}
