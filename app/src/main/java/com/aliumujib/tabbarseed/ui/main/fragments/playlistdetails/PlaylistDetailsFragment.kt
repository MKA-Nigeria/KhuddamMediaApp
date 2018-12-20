package com.aliumujib.tabbarseed.ui.main.fragments.playlistdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseMVVMFragment
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.utils.extensions.setStatusBarTranslucent
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class PlaylistDetailsFragment : BaseMVVMFragment<PlaylistDetailsViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

    }


    @Inject
    override fun injectViewModel(viewModel: PlaylistDetailsViewModel) {
        super.injectViewModel(viewModel)
    }

    override fun injectDependencies() {
        super.injectDependencies()
        AndroidSupportInjection.inject(this)
    }

    @Inject
    lateinit var mainFragmentNavigation: IMainFragmentNavigation


    override fun onResume() {
        super.onResume()
        mainFragmentNavigation.hideMainToolbar()
        activity?.setStatusBarTranslucent(true)
    }

    override fun onPause() {
        super.onPause()
        mainFragmentNavigation.showMainToolbar()
        activity?.setStatusBarTranslucent(false)
    }

    override val layoutResID: Int
        get() = R.layout.fragment_playlist_details


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    companion object {

        @JvmStatic
        fun newInstance() =
                PlaylistDetailsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }

        fun openVideoPlayListDetails() =
                PlaylistDetailsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }

    }
}
