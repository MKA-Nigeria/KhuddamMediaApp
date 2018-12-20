package com.aliumujib.tabbarseed.ui.main.fragments.videos

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.PlayList
import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.adapter.base.SingleLayoutAdapter
import com.aliumujib.tabbarseed.ui.adapter.utils.SpacingItemDecoration
import com.aliumujib.tabbarseed.ui.base.BaseMVVMFragment
import com.aliumujib.tabbarseed.utils.extensions.dpToPx
import com.aliumujib.tabbarseed.utils.extensions.removeAllDecorations
import com.google.android.material.tabs.TabLayout
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_videos_list.*
import javax.inject.Inject


class VideosFragment : BaseMVVMFragment<VideosViewModel>(){

    @Inject
    lateinit var playlistAdapter: SingleLayoutAdapter<PlayList>


    @Inject
    lateinit var videosAdapter: SingleLayoutAdapter<PlayListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

    }


    @Inject
    override fun injectViewModel(viewModel: VideosViewModel) {
        super.injectViewModel(viewModel)
    }

    override fun injectDependencies() {
        super.injectDependencies()
        AndroidSupportInjection.inject(this)
    }


    override val layoutResID: Int
        get() = R.layout.fragment_videos_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = listOf("Videos", "Playlists")

        for (i in 0 until titles.size) {
            tabs.addTab(tabs.newTab().setText(titles[i]))
        }

        view_flipper.displayedChild = 0

        items.adapter = videosAdapter
        items.removeAllDecorations()
        items.addItemDecoration(SpacingItemDecoration(context!!.dpToPx(16), context!!.dpToPx(16),false,true ))

        play_lists.adapter = playlistAdapter
        play_lists.removeAllDecorations()
        play_lists.addItemDecoration(SpacingItemDecoration(context!!.dpToPx(16), context!!.dpToPx(16),false,true ))
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
                VideosFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
