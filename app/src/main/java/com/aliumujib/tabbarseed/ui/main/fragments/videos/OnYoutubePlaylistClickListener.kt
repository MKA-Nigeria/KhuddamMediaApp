package com.aliumujib.tabbarseed.ui.main.fragments.videos

import com.aliumujib.tabbarseed.data.model.YoutubePlayList
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation

class OnYoutubePlaylistClickListener(var fragmentNavigation: IMainFragmentNavigation) : BindableItemClickListener<YoutubePlayList> {

    override fun onItemClicked(data: YoutubePlayList) {
        fragmentNavigation.openPlayListDetails(data)
    }

}