package com.aliumujib.tabbarseed.ui.main.fragments.playlistdetails

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat.getMinimumHeight
import androidx.fragment.app.Fragment
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.IPlayable
import com.aliumujib.tabbarseed.data.model.PlaylistParcelable
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.adapter.base.SingleLayoutAdapter
import com.aliumujib.tabbarseed.ui.adapter.utils.SpacingItemDecoration
import com.aliumujib.tabbarseed.ui.base.BaseMVVMFragment
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.utils.extensions.*
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_playlist_details.*
import javax.inject.Inject


class PlaylistDetailsFragment : BaseMVVMFragment<PlaylistDetailsViewModel>(), BindableItemClickListener<IPlayable> {

    override fun onItemClicked(data: IPlayable) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

    }

    @Inject
    lateinit var adapter: SingleLayoutAdapter<IPlayable>

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

        items.removeAllDecorations()
        items.adapter = adapter
        items.addItemDecoration(SpacingItemDecoration(0, context!!.dpToPx(16), false, true))
        initActionProps()

        val playlistParcelable = arguments?.getParcelable<PlaylistParcelable>(PLAY_LIST_PARCELABLE)!!
        getViewModel().getDataFor(playlistParcelable)

    }


    private fun initActionProps() {
        // appCompatActivity().supportActionBar?.setEmptyTitle()

        app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (collapsing_toolbar.height + verticalOffset < 2 * getMinimumHeight(collapsing_toolbar)) {
                toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
            } else {
                toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
            }
        })
    }


    companion object {

        const val PLAY_LIST_PARCELABLE = "PLAY_LIST_PARCELABLE"


//        @JvmStatic
//        fun newInstance() =
//                PlaylistDetailsFragment().apply {
//                    arguments = Bundle().apply {
//                    }
//                }

        fun openVideoPlayListDetails(playlistParcelable: PlaylistParcelable) =
                PlaylistDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(PLAY_LIST_PARCELABLE, playlistParcelable)
                    }
                }

    }
}
