package com.aliumujib.tabbarseed.ui.main.fragments.podcasts

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.SoundCloudPlayList
import com.aliumujib.tabbarseed.data.model.Track
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.adapter.base.SingleLayoutAdapter
import com.aliumujib.tabbarseed.ui.adapter.utils.SpacingItemDecoration
import com.aliumujib.tabbarseed.ui.base.BaseMVVMFragment
import com.aliumujib.tabbarseed.utils.extensions.dpToPx
import com.aliumujib.tabbarseed.utils.extensions.removeAllDecorations
import com.google.android.material.tabs.TabLayout
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_podcasts.*
import javax.inject.Inject


class PodcastsFragment : BaseMVVMFragment<PodcastsViewModel>(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

    }

    @Inject
    lateinit var playListAdapter: SingleLayoutAdapter<SoundCloudPlayList>

    @Inject
    lateinit var trackListAdapter: SingleLayoutAdapter<Track>


    @Inject
    override fun injectViewModel(viewModel: PodcastsViewModel) {
        super.injectViewModel(viewModel)
    }

    override fun injectDependencies() {
        super.injectDependencies()
        AndroidSupportInjection.inject(this)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override val layoutResID: Int
        get() = R.layout.fragment_podcasts


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = listOf("All Podcasts", "Playlists")

        for (i in 0 until titles.size) {
            tabs.addTab(tabs.newTab().setText(titles[i]))
        }

        view_flipper.displayedChild = 0


        items.adapter = trackListAdapter
        items.removeAllDecorations()
        items.addItemDecoration(SpacingItemDecoration(context!!.dpToPx(16), context!!.dpToPx(16), false, true))


        play_lists.adapter = playListAdapter
        play_lists.removeAllDecorations()
        play_lists.addItemDecoration(SpacingItemDecoration(context!!.dpToPx(16), context!!.dpToPx(16), false, true))
        play_lists.layoutManager = GridLayoutManager(context, 2)


        tabs.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                view_flipper.displayedChild = p0!!.position
            }
        })

    }


    companion object {

        @JvmStatic
        fun newInstance() =
                PodcastsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
