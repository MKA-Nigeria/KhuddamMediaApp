package com.aliumujib.tabbarseed.ui.main

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.PlayableParcelable
import com.aliumujib.tabbarseed.ui.main.service.AudioPlayerService
import com.aliumujib.tabbarseed.utils.PlaybackStatusView
import com.aliumujib.tabbarseed.utils.extensions.getScreenWidth
import com.aliumujib.tabbarseed.utils.extensions.setWidth
import com.aliumujib.tabbarseed.utils.imageloader.ImageLoader
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber
import javax.inject.Inject

interface IAudioPlaybackVC {
    fun showPlaybackViewForAudio(playableParcelable: PlayableParcelable)
    fun hidePlaybackViewForAudio()
    fun destroy()
    fun setUp()
}

class AudioPlaybackViewController @Inject constructor(var activity: MainActivity,
                                                      var exoPlayer: ExoPlayer,
                                                      var imageLoader: ImageLoader) : IAudioPlaybackVC {

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    var dragView: View? = null
    var imageCollapsed: ImageView? = null
    var collapseToCardBtn: ImageView? = null
    var titleCollapsed: TextView? = null
    var descriptionCollapsed: TextView? = null
    var titleExpanded: TextView? = null
    var descriptionExpanded: TextView? = null
    var playPause: PlaybackStatusView? = null
    var nowPlayingCardView: CardView? = null
    var scrollView:ScrollView?=null

    override fun setUp() {
        dragView = activity.findViewById(R.id.dragView)
        imageCollapsed = activity.findViewById(R.id.imageView)
        collapseToCardBtn = activity.findViewById(R.id.imageView3)
        titleCollapsed = activity.findViewById(R.id.textView6)
        descriptionCollapsed = activity.findViewById(R.id.descriptionText)
        titleExpanded = activity.findViewById(R.id.title)
        descriptionExpanded = activity.findViewById(R.id.description)
        playPause = activity.findViewById(R.id.play_pause)
        nowPlayingCardView = activity.findViewById(R.id.cardView)
        scrollView = activity.findViewById(R.id.scrollView)

        bottomSheetBehavior = BottomSheetBehavior.from(dragView)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior?.setBottomSheetCallback(PanelSlideListener(activity))

        collapseToCardBtn?.setOnClickListener {
            showCardPlayer()
        }

        exoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                Timber.d(TAG, playbackState.toString())

                if (playbackState == Player.STATE_ENDED) {
                    hidePlaybackViewForAudio()
                } else if (playWhenReady && playbackState == Player.STATE_READY) {
                    playPause?.isPlaying = PlaybackStatusView.PLAYED
                    showCardPlayer()
                }
            }
        })

        playPause?.setOnBookmarkStatusChangeListener(object : PlaybackStatusView.OnPlaybackStatusChangeListener {
            override fun onStatusChanged(isPlaying: Int) {
                if (isPlaying == PlaybackStatusView.PAUSED) {
                    AudioPlayerService.pausePlayback(activity)
                } else {
                    AudioPlayerService.continuePlayback(activity)
                }
            }
        })

    }

    private fun setViewsAsClickable(clickable: Boolean) {
        dragView?.isClickable = clickable
        nowPlayingCardView?.isClickable = clickable
        scrollView?.isClickable = clickable
    }


    inner class PanelSlideListener(var activity: MainActivity) : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(p0: View, slideOffset: Float) {
            val cardOpacity = 1 - slideOffset
            val scrollOpacity = slideOffset

            Log.d(this@PanelSlideListener.TAG, "OFSSET: $slideOffset")
            nowPlayingCardView?.alpha = cardOpacity
            scrollView?.alpha = scrollOpacity
        }

        override fun onStateChanged(p0: View, p1: Int) {
            if (p1 == BottomSheetBehavior.STATE_COLLAPSED) {
                scrollView?.setWidth(0)
                setViewsAsClickable(false)
                nowPlayingCardView?.isClickable = false
            } else if (p1 == BottomSheetBehavior.STATE_HIDDEN) {
                AudioPlayerService.stopService(activity)
            } else {
                scrollView?.setWidth(activity.getScreenWidth())
                setViewsAsClickable(true)
            }
        }

        private val TAG = PanelSlideListener::class.java.simpleName
    }


    override fun hidePlaybackViewForAudio() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun destroy() {

    }

    override fun showPlaybackViewForAudio(playableParcelable: PlayableParcelable) {
        imageLoader.loadWithAsCircle(playableParcelable.imageURL, imageCollapsed!!)
        titleCollapsed?.text = playableParcelable.name
        descriptionCollapsed?.text = playableParcelable.description
        titleExpanded?.text = playableParcelable.name
        descriptionExpanded?.text = playableParcelable.description
        showCardPlayer()
    }

    private fun showCardPlayer() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    companion object {
        var TAG = AudioPlaybackViewController::class.java.simpleName
    }
}