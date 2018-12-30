package com.aliumujib.tabbarseed.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.PlayableParcelable
import com.aliumujib.tabbarseed.ui.main.service.AudioPlayerService
import com.aliumujib.tabbarseed.utils.PlaybackStatusView
import com.aliumujib.tabbarseed.utils.imageloader.ImageLoader
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber
import javax.inject.Inject

interface IPlaybackVC {
    fun showPlaybackViewForAudio(playableParcelable: PlayableParcelable)
    fun hidePlaybackViewForAudio()
    fun destroy()
    fun setUp()
    fun showPlaybackViewForVideo(playableParcelable: PlayableParcelable)
    fun hidePlaybackViewForVideo()
}

class PlaybackViewController @Inject constructor(var activity: MainActivity,
                                                 var exoPlayer: ExoPlayer,
                                                 var imageLoader: ImageLoader) : IPlaybackVC {

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    var dragView: View? = null
    var imageCollapsed: ImageView? = null
    var collapseToCardBtn: ImageView? = null
    var titleCollapsed: TextView? = null
    var descriptionCollapsed: TextView? = null
    var titleExpanded: TextView? = null
    var descriptionExpanded: TextView? = null
    var playPause: PlaybackStatusView? = null

    override fun setUp() {
        dragView = activity.findViewById(R.id.dragView)
        imageCollapsed = activity.findViewById(R.id.imageView)
        collapseToCardBtn = activity.findViewById(R.id.imageView3)
        titleCollapsed = activity.findViewById(R.id.textView6)
        descriptionCollapsed = activity.findViewById(R.id.descriptionText)
        titleExpanded = activity.findViewById(R.id.title)
        descriptionExpanded = activity.findViewById(R.id.description)
        playPause = activity.findViewById(R.id.play_pause)
        bottomSheetBehavior = BottomSheetBehavior.from(dragView)


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

        playPause?.setOnBookmarkStatusChangeListener(object :PlaybackStatusView.OnPlaybackStatusChangeListener{
            override fun onStatusChanged(isPlaying: Int) {
                if(isPlaying == PlaybackStatusView.PAUSED){
                    AudioPlayerService.pausePlayback(activity)
                }else{
                    AudioPlayerService.continuePlayback(activity)
                }
            }
        })

    }

    override fun hidePlaybackViewForVideo() {

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
    }

    private fun showCardPlayer() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun showPlaybackViewForVideo(playableParcelable: PlayableParcelable) {

    }

    companion object {
        var TAG = PlaybackViewController::class.java.simpleName
    }
}