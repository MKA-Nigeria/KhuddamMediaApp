package com.aliumujib.tabbarseed.ui.main.fragments.podcasts

import com.aliumujib.tabbarseed.data.model.Track
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation

class OnTrackClickListener(var fragmentNavigation: IMainFragmentNavigation) : BindableItemClickListener<Track> {

    override fun onItemClicked(data: Track) {
        fragmentNavigation.playTrack(data)
    }

}