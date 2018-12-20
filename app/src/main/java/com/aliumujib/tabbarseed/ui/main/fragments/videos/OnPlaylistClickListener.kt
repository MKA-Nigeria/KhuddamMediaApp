package com.aliumujib.tabbarseed.ui.main.fragments.videos

import com.aliumujib.tabbarseed.data.model.PlayList
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation

class OnPlaylistClickListener(var fragmentNavigation: IMainFragmentNavigation) : BindableItemClickListener<PlayList> {

    override fun onItemClicked(data: PlayList) {
        fragmentNavigation.openPlayListDetails(data)
    }

}