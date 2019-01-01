package com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments


import android.os.Bundle
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseMVVMFragment


class YoutubeVideoDetailsFragment : BaseMVVMFragment<YoutubeVideoDetailViewModel>() {

    override val layoutResID: Int
        get() = R.layout.fragment_youtube_video_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    companion object {

        val TAG = YoutubeVideoDetailsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() =
                YoutubeVideoDetailsFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
