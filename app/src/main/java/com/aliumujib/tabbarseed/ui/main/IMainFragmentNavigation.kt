package com.aliumujib.tabbarseed.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.*
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverFragment
import com.aliumujib.tabbarseed.ui.main.fragments.me.MeFragment
import com.aliumujib.tabbarseed.ui.main.fragments.playlistdetails.PlaylistDetailsFragment
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.PodcastsFragment
import com.aliumujib.tabbarseed.ui.main.fragments.videos.VideosFragment
import com.aliumujib.tabbarseed.ui.main.service.AudioPlayerService
import com.aliumujib.tabbarseed.utils.FragNavController
import com.aliumujib.tabbarseed.utils.FragmentHistory
import kotlinx.android.synthetic.main.activity_main_constraints.*
import javax.inject.Inject


interface IMainFragmentNavigation {
    fun pushFragment(fragment: Fragment)
    fun push(position: Int)
    fun clearStack()
    fun switchTab(position: Int)
    fun popFragment()
    fun handleBackPress()
    fun isOnRootFragment(): Boolean
    fun setUp()
    fun onSaveInstanceState(outState: Bundle)
    fun hideMainToolbar()
    fun showMainToolbar()
    fun playVideo(data: PlayListItem)
    fun openPlayListDetails(data: YoutubePlayList)
    fun openPlayListDetails(data: SoundCloudPlayList)
    fun playTrack(data: Track)
}

/**
 * Controls navigation within the main fragments of the app
 * All methods do exactly what they say and any extra methods should first be added to IMainFragmentNavigation
 * **/

class MainFragmentNavigation(private var activity: MainActivity,
                             private var playbackVC: IPlaybackVC,
                             private var bundle: Bundle) : IMainFragmentNavigation,
        FragNavController.TransactionListener,
        FragNavController.RootFragmentListener {

    override fun playTrack(data: Track) {
        playbackVC.showPlaybackViewForAudio(PlayableParcelable.fromSoundCloudTrack(data))
        AudioPlayerService.startService(activity, PlayableParcelable.fromSoundCloudTrack(data))
    }

    override fun playVideo(data: PlayListItem) {

    }

    override fun openPlayListDetails(data: YoutubePlayList) {
        pushFragment(PlaylistDetailsFragment.openVideoPlayListDetails(PlaylistParcelable.fromPlayList(data)))
    }

    override fun openPlayListDetails(data: SoundCloudPlayList) {
        pushFragment(PlaylistDetailsFragment.openVideoPlayListDetails(PlaylistParcelable.fromPlayList(data)))
    }

    override fun hideMainToolbar() {
        toolbar?.visibility = View.GONE
    }

    override fun showMainToolbar() {
        toolbar?.visibility = View.VISIBLE
    }

    var toolbar: View? = null


    override fun onSaveInstanceState(outState: Bundle) {
        navController?.onSaveInstanceState(outState)
    }

    override fun handleBackPress() {

        if (!this.isOnRootFragment()) {
            this.popFragment()
        } else {

            if (fragmentHistory.isEmpty) {
                activity.onBackPressed()
            } else {

                if (fragmentHistory!!.stackSize > 1) {

                    val position = fragmentHistory!!.popPrevious()

                    switchTab(position)

                } else {

                    switchTab(0)

                    fragmentHistory!!.emptyStack()
                }
            }

        }
    }


    override fun isOnRootFragment(): Boolean = navController?.isRootFragment!!

    override fun switchTab(position: Int) {
        navController?.switchTab(position)
    }

    override fun popFragment() {
        navController?.popFragment()
    }

    override fun clearStack() {
        navController?.clearStack()
    }

    override fun push(position: Int) {
        fragmentHistory.push(position)
    }

    private var navController: FragNavController? = null
    private lateinit var fragmentHistory: FragmentHistory
    private var supportActionBar: ActionBar? = null

    override fun setUp() {

        fragmentHistory = FragmentHistory()

        toolbar = activity.include2

        navController = FragNavController.newBuilder(bundle,
                activity.supportFragmentManager,
                R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, 4)
                .build()

        supportActionBar = (activity as AppCompatActivity).supportActionBar
    }

    override fun pushFragment(fragment: Fragment) {
        navController?.pushFragment(fragment)
    }


    override fun onTabTransaction(fragment: Fragment, index: Int) {
        // If we have a backstack, show the back button
        if (activity.supportActionBar != null) {
            updateToolbar()
        }
    }

    private fun updateToolbar() {
        if (navController != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(!navController!!.isRootFragment)
            supportActionBar!!.setDisplayShowHomeEnabled(!navController!!.isRootFragment)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

            //TransitionManager.beginDelayedTransition(title_layout, ChangeBounds())
            if (!navController!!.isRootFragment) {

            } else {

            }
        }
    }


    override fun onFragmentTransaction(fragment: Fragment, transactionType: FragNavController.TransactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (supportActionBar != null && navController != null) {

            updateToolbar()

        }
    }

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            FragNavController.TAB1 -> return VideosFragment.newInstance()
            FragNavController.TAB2 -> return PodcastsFragment.newInstance()
            FragNavController.TAB3 -> return DiscoverFragment.newInstance()
            FragNavController.TAB4 -> return MeFragment.newInstance()
        }
        throw IllegalStateException("Need to send an index that we know")
    }


    fun updateToolbarTitle(title: String) {
        supportActionBar!!.title = title
        //toolbar_title_view.text = title
    }
}