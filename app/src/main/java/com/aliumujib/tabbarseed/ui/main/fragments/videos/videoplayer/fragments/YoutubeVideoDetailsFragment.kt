package com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseFragment


class YoutubeVideoDetailsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_video_details, container, false)
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
