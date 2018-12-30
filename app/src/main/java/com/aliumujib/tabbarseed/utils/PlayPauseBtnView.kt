package com.aliumujib.tabbarseed.utils


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build

import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.utils.extensions.dpToPx
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageButton
import org.jetbrains.anko.backgroundDrawable


class PlaybackStatusView : AppCompatImageButton {

    private var onPlayBackStatusChangeListener: OnPlaybackStatusChangeListener? = null
    private var playedDrawable: Drawable? = null
    private var pausedDrawable: Drawable? = null
    private var padding = 10
    @BookmarkState
    private var currentPlaybackStatus = PAUSED

    var isPlaying: Int
        @BookmarkState
        get() = currentPlaybackStatus
        set(@BookmarkState bookmarked) {
            this.currentPlaybackStatus = bookmarked
            setIcons()
        }



    init {
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeView()
    }

    private fun initializeView() {
        playedDrawable = ContextCompat.getDrawable(context, R.drawable.ic_pause_grey600_24dp)

        pausedDrawable = ContextCompat.getDrawable(context, R.drawable.ic_play_grey600_24dp)


        setIcons()
        //padding = context.dpToPx(padding)
        //setPadding(padding, padding, padding, padding)

        setOnClickListener(BookmarkClickListener())

    }

    private fun setIcons() {
        if (currentPlaybackStatus == PLAYED) {
            setImageDrawable(playedDrawable)
        } else
            setImageDrawable(pausedDrawable)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (Build.VERSION.SDK_INT > 10) {
            alpha = if (enabled) {
                1f
            } else {
                0.5f
            }
        }
    }

    fun setOnBookmarkStatusChangeListener(onPlaybackStatusChangeListener: OnPlaybackStatusChangeListener) {
        this.onPlayBackStatusChangeListener = onPlaybackStatusChangeListener
    }

    @IntDef(PAUSED, PLAYED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class BookmarkState

    interface OnPlaybackStatusChangeListener {
        fun onStatusChanged(@BookmarkState isPlaying: Int)
    }

    private inner class BookmarkClickListener : View.OnClickListener {

        override fun onClick(view: View) {
            if (currentPlaybackStatus == PLAYED) {
                currentPlaybackStatus = PAUSED
            } else
                currentPlaybackStatus = PLAYED

            setIcons()

            if (onPlayBackStatusChangeListener != null)
                onPlayBackStatusChangeListener!!.onStatusChanged(currentPlaybackStatus)
        }
    }

    companion object {

        const val PAUSED = 0
        const val PLAYED = 1
    }
}
