package com.aliumujib.tabbarseed.ui.main.fragments.podcasts

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.PlayableParcelable
import com.aliumujib.tabbarseed.ui.main.MainActivity
import com.aliumujib.tabbarseed.ui.main.service.AudioPlayerService
import com.aliumujib.tabbarseed.utils.PlaybackStatusView
import com.aliumujib.tabbarseed.utils.extensions.getScreenWidth
import com.aliumujib.tabbarseed.utils.extensions.setWidth
import com.aliumujib.tabbarseed.utils.imageloader.ImageLoader
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.TimeBar
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

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var dragView: View? = null
    private var imageCollapsed: ImageView? = null
    private var collapseToCardBtn: ImageView? = null
    private var titleCollapsed: TextView? = null
    private var descriptionCollapsed: TextView? = null
    private var titleExpanded: TextView? = null
    private var descriptionExpanded: TextView? = null
    private var playPause: PlaybackStatusView? = null
    private var nowPlayingCardView: CardView? = null
    private var scrollView: ScrollView? = null

    private var seekPlayerProgress: DefaultTimeBar? = null
    private var handler: Handler? = null
    private var btnPlayPauseExpanded: PlaybackStatusView? = null
    private var txtCurrentTime: TextView? = null
    private var txtEndTime: TextView? = null

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
        btnPlayPauseExpanded = activity.findViewById(R.id.imageView6)

        seekPlayerProgress = activity.findViewById(R.id.exo_progress)
        txtCurrentTime = activity.findViewById(R.id.exo_position)
        txtEndTime = activity.findViewById(R.id.exo_duration)


        bottomSheetBehavior = BottomSheetBehavior.from(dragView)
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior?.setBottomSheetCallback(PanelSlideListener(activity))


        collapseToCardBtn?.setOnClickListener {
            showCardPlayer()
        }

        if (exoPlayer.playWhenReady && exoPlayer.playbackState == Player.STATE_READY) {
            playPause?.isPlaying = PlaybackStatusView.PLAYED
            btnPlayPauseExpanded?.isPlaying = PlaybackStatusView.PLAYED
            showCardPlayer()
        }

        exoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                Timber.d(TAG, playbackState.toString())

                if (playbackState == Player.STATE_ENDED) {
                    hidePlaybackViewForAudio()
                } else if (playWhenReady && playbackState == Player.STATE_READY) {
                    playPause?.isPlaying = PlaybackStatusView.PLAYED
                    btnPlayPauseExpanded?.isPlaying = PlaybackStatusView.PLAYED
                    showCardPlayer()
                    initializeSeekBarAndTimers()
                }
            }

        })

        playPause?.setOnPlaybackStatusChangeListener(playbackBtnStatusListener)
        btnPlayPauseExpanded?.setOnPlaybackStatusChangeListener(playbackBtnStatusListener)

    }

    var playbackBtnStatusListener = object : PlaybackStatusView.OnPlaybackStatusChangeListener {
        override fun onStatusChanged(isPlaying: Int) {
            if (isPlaying == PlaybackStatusView.PAUSED) {
                AudioPlayerService.pausePlayback(activity)
            } else {
                AudioPlayerService.continuePlayback(activity)
            }
        }
    }

    private fun initializeSeekBarAndTimers() {

        seekPlayerProgress?.addListener(object : TimeBar.OnScrubListener {
            override fun onScrubMove(timeBar: TimeBar?, position: Long) {

            }

            override fun onScrubStart(timeBar: TimeBar?, position: Long) {

            }

            override fun onScrubStop(timeBar: TimeBar?, position: Long, canceled: Boolean) {
                if (!canceled) {
                    exoPlayer.seekTo((position))
                }
            }

        })

        seekPlayerProgress?.setDuration(exoPlayer.duration)
        txtEndTime?.text = formatMilliSecondsToTime(exoPlayer.duration)

        handler = Handler()

        handler?.postDelayed(runnable, 0)
    }


    private fun updateProgress() {

        //get current progress
        Log.d(TAG, "Current position: ${exoPlayer.contentPosition} FORMATTED: ${formatMilliSecondsToTime(exoPlayer.contentPosition)}")
        seekPlayerProgress?.setPosition(exoPlayer.currentPosition)
        txtCurrentTime?.text = formatMilliSecondsToTime(exoPlayer.contentPosition)

        handler?.postDelayed(runnable, 1000)

    }

    private var runnable = Runnable {
        updateProgress()
    }


    private fun formatMilliSecondsToTime(milliseconds: Long): String {

        val seconds = (milliseconds / 1000).toInt() % 60
        val minutes = (milliseconds / (1000 * 60) % 60).toInt()
        val hours = (milliseconds / (1000 * 60 * 60) % 24).toInt()
        return (twoDigitString(hours.toLong()) + " : " + twoDigitString(minutes.toLong()) + " : "
                + twoDigitString(seconds.toLong()))
    }

    private fun twoDigitString(number: Long): String {

        if (number == 0L) {
            return "00"
        }

        return if (number / 10 == 0L) {
            "0$number"
        } else number.toString()

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