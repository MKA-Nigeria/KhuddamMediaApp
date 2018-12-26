package com.aliumujib.tabbarseed.ui.main.fragments.videos

import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation

class OnVideoClickListener(var fragmentNavigation: IMainFragmentNavigation) : BindableItemClickListener<PlayListItem> {

    override fun onItemClicked(data: PlayListItem) {
        fragmentNavigation.playVideo(data)
    }

}