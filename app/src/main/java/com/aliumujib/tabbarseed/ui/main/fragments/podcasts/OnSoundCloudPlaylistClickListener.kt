package com.aliumujib.tabbarseed.ui.main.fragments.podcasts

import com.aliumujib.tabbarseed.data.model.SoundCloudPlayList
import com.aliumujib.tabbarseed.ui.adapter.base.BindableItemClickListener
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation

class OnSoundCloudPlaylistClickListener(var fragmentNavigation: IMainFragmentNavigation) : BindableItemClickListener<SoundCloudPlayList> {

    override fun onItemClicked(data: SoundCloudPlayList) {
        fragmentNavigation.openPlayListDetails(data)
    }

}