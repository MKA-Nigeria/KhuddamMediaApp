package com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer

import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.PlayableParcelable
import com.aliumujib.tabbarseed.ui.main.MainActivity
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.animations.GestureEvents
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.animations.VideoTouchHandler
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments.YoutubeVideoDetailsFragment
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments.YoutubeVideoPlayerFragment
import com.aliumujib.tabbarseed.utils.extensions.getParams
import com.aliumujib.tabbarseed.utils.extensions.loadFragment
import com.aliumujib.tabbarseed.utils.extensions.removeFragmentByID
import com.aliumujib.tabbarseed.utils.extensions.updateParams
import kotlinx.android.synthetic.main.activity_main_constraints.*
import timber.log.Timber
import javax.inject.Inject

interface IVideoPlayerVC {
    fun showPlaybackViewForVideo(playableParcelable: PlayableParcelable)
    fun hidePlaybackViewForVideo()
    fun destroy()
    fun setUp()
    fun onBackPressed()
}


class VideoPlayerViewController @Inject constructor(var activity: MainActivity) : IVideoPlayerVC, GestureEvents {

    override fun onBackPressed() {
        if (animationTouchListener?.isExpanded == true) {
            animationTouchListener?.isExpanded = false
        } else {
            activity.onBackPressed()
        }

    }

    override fun onClick(view: View) {
        if (animationTouchListener?.isExpanded == true) {
            animationTouchListener?.isExpanded = true
        } else {
            //dashboardViewModel.onClicked()
        }
    }

    override fun onDismiss(view: View) {
        dismiss()
    }

    override fun onScale(percentage: Float) {
        //dashboardViewModel.showControllers(false)
        scaleVideo(percentage)
    }

    override fun onSwipe(percentage: Float) {
        swipeVideo(percentage)
    }

    override fun onExpand(isExpanded: Boolean) {
        setViewExpanded(isExpanded)
    }

    /**
     * Scale the video as per given percentage of user scrolls
     * in up/down direction from current position
     */
    private fun scaleVideo(percentScrollUp: Float) {

        //Prevent guidelines to go out of screen bound
        val percentVerticalMoved = Math.max(0F, Math.min(VideoTouchHandler.MIN_VERTICAL_LIMIT, percentScrollUp))
        val movedPercent = percentVerticalMoved / VideoTouchHandler.MIN_VERTICAL_LIMIT
        val percentHorizontalMoved = VideoTouchHandler.MIN_HORIZONTAL_LIMIT * movedPercent
        val percentBottomMoved = 1F - movedPercent * (1F - VideoTouchHandler.MIN_BOTTOM_LIMIT)
        Timber.e("Bottom : $percentBottomMoved")
        val percentMarginMoved = 1F - movedPercent * (1F - VideoTouchHandler.MIN_MARGIN_END_LIMIT)

        paramsGlHorizontal?.guidePercent = percentVerticalMoved
        paramsGlVertical?.guidePercent = percentHorizontalMoved
        paramsGlBottom?.guidePercent = percentBottomMoved
        paramsGlMarginEnd?.guidePercent = percentMarginMoved

        activity.guidelineHorizontal.layoutParams = paramsGlHorizontal
        activity.guidelineVertical.layoutParams = paramsGlVertical
        activity.guidelineBottom.layoutParams = paramsGlBottom
        activity.guidelineMarginEnd.layoutParams = paramsGlMarginEnd

        activity.frmDetailsContainer.alpha = 1.0F - movedPercent
    }

    /**
     * Swipe animation on given percentage user has scroll on left/right
     * direction from the current position
     */
    private fun swipeVideo(percentScrollSwipe: Float) {

        //Prevent guidelines to go out of screen bound
        val percentHorizontalMoved = Math.max(-0.25F, Math.min(VideoTouchHandler.MIN_HORIZONTAL_LIMIT, percentScrollSwipe))
        val percentMarginMoved = percentHorizontalMoved + (VideoTouchHandler.MIN_MARGIN_END_LIMIT - VideoTouchHandler.MIN_HORIZONTAL_LIMIT)

        paramsGlVertical?.guidePercent = percentHorizontalMoved
        paramsGlMarginEnd?.guidePercent = percentMarginMoved

        activity.guidelineVertical.layoutParams = paramsGlVertical
        activity.guidelineMarginEnd.layoutParams = paramsGlMarginEnd
    }

    /**
     * Hide all video and video details fragment
     */
    private fun hide() = activity.rootContainer.updateParams {

        setGuidelinePercent(activity.guidelineHorizontal.id, 100F)
        setGuidelinePercent(activity.guidelineVertical.id, 100F)
        setAlpha(activity.frmDetailsContainer.id, 0F)

        TransitionManager.beginDelayedTransition(activity.rootContainer, ChangeBounds().apply {
            interpolator = AnticipateOvershootInterpolator(1.0f)
            duration = 250
        })
    }

    /**
     * Expand or collapse the video fragment animation
     */
    private fun setViewExpanded(isExpanded: Boolean) = activity.rootContainer.updateParams(constraintSet) {

        setGuidelinePercent(activity.guidelineHorizontal.id, if (isExpanded) 0F else VideoTouchHandler.MIN_VERTICAL_LIMIT)
        setGuidelinePercent(activity.guidelineVertical.id, if (isExpanded) 0F else VideoTouchHandler.MIN_HORIZONTAL_LIMIT)
        setGuidelinePercent(activity.guidelineBottom.id, if (isExpanded) 1F else VideoTouchHandler.MIN_BOTTOM_LIMIT)
        setGuidelinePercent(activity.guidelineMarginEnd.id, if (isExpanded) 1F else VideoTouchHandler.MIN_MARGIN_END_LIMIT)
        setAlpha(activity.frmDetailsContainer.id, if (isExpanded) 1.0F else 0F)

        TransitionManager.beginDelayedTransition(activity.rootContainer, ChangeBounds().apply {
            interpolator = android.view.animation.AnticipateOvershootInterpolator(1.0f)
            duration = 250
        })
    }

    /**
     * Show dismiss animation when user have moved
     * more than 50% horizontally
     */
    private fun dismiss() = activity.rootContainer.updateParams(constraintSet) {

        setGuidelinePercent(activity.guidelineVertical.id, VideoTouchHandler.MIN_HORIZONTAL_LIMIT - VideoTouchHandler.MIN_MARGIN_END_LIMIT)
        setGuidelinePercent(activity.guidelineMarginEnd.id, 0F)

        TransitionManager.beginDelayedTransition(activity.rootContainer, ChangeBounds().apply {
            interpolator = AnticipateOvershootInterpolator(1.0f)
            duration = 250
            addListener(object : Transition.TransitionListener {
                override fun onTransitionResume(transition: Transition) {
                }

                override fun onTransitionPause(transition: Transition) {
                }

                override fun onTransitionCancel(transition: Transition) {
                }

                override fun onTransitionStart(transition: Transition) {
                }

                override fun onTransitionEnd(transition: Transition) {
                    //Remove Video when swipe animation is ended
                    activity.removeFragmentByID(R.id.frmVideoContainer)
                }
            })
        })
    }


    /*
 *  Setting up guideline parameters to change the
 *  guideline percent value as per user touch event
 */
    private var paramsGlHorizontal: ConstraintLayout.LayoutParams? = null
    private var paramsGlVertical: ConstraintLayout.LayoutParams? = null
    private var paramsGlBottom: ConstraintLayout.LayoutParams? = null
    private var paramsGlMarginEnd: ConstraintLayout.LayoutParams? = null

    private val constraintSet = ConstraintSet()

    private var animationTouchListener: VideoTouchHandler? = null

    override fun showPlaybackViewForVideo(playableParcelable: PlayableParcelable) {
        animationTouchListener?.show()
        animationTouchListener?.isExpanded = true

        activity.loadFragment {
            replace(R.id.frmVideoContainer, YoutubeVideoPlayerFragment.newInstance(playableParcelable.id), YoutubeVideoPlayerFragment.TAG)
        }

        activity.loadFragment {
            replace(R.id.frmDetailsContainer, YoutubeVideoDetailsFragment.newInstance(), YoutubeVideoDetailsFragment.TAG)
        }
    }

    override fun hidePlaybackViewForVideo() {
        hide()
    }

    override fun destroy() {

    }

    override fun setUp() {
        paramsGlHorizontal = activity.guidelineHorizontal.getParams()
        paramsGlVertical = activity.guidelineVertical.getParams()
        paramsGlBottom = activity.guidelineBottom.getParams()
        paramsGlMarginEnd = activity.guidelineMarginEnd.getParams()
        animationTouchListener = VideoTouchHandler(activity, this)

        activity.frmVideoContainer.setOnTouchListener(animationTouchListener)
        hidePlaybackViewForVideo()
    }

}