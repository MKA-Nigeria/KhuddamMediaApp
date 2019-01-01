package com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseFragment
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.IVideoPlayerVC
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.VideoPlayerViewController
import com.aliumujib.tabbarseed.utils.extensions.getPlayerWebView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_youtube_video_player.*
import javax.inject.Inject
import java.lang.reflect.AccessibleObject.setAccessible


class YoutubeVideoPlayerFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @Inject
    lateinit var videoPlayerViewController: IVideoPlayerVC

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoID = arguments!!.getString(VIDEO_ID)

        lifecycle.addObserver(youtube_player_fragment)

        youtube_player_fragment.initialize({ youTubePlayer ->
            youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                  // (videoPlayerViewController as VideoPlayerViewController).addTouchHandlerToView(youTubePlayer as WebView)
                    youTubePlayer.loadVideo(videoID, 0F)
                }
            })
        }, true)

       (videoPlayerViewController as VideoPlayerViewController).addTouchHandlerToView(touch_consumer_view)

    }


    override fun onStop() {
        super.onStop()
        youtube_player_fragment.release()
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
