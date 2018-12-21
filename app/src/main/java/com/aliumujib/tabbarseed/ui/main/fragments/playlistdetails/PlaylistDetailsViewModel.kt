package com.aliumujib.tabbarseed.ui.main.fragments.playlistdetails

import com.aliumujib.tabbarseed.data.contracts.IYoutubeRepository
import com.aliumujib.tabbarseed.data.model.IPlayable
import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.data.model.PlaylistParcelable
import com.aliumujib.tabbarseed.ui.base.BaseViewModel
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.utils.extensions.mutableLiveDataOf

class PlaylistDetailsViewModel(var fragmentNavigation: IMainFragmentNavigation, var repository: IYoutubeRepository) : BaseViewModel() {

    val playlistDetails = mutableLiveDataOf<PlaylistParcelable>()
    val playlistItems = mutableLiveDataOf<List<IPlayable>>()

    override fun setUp() {
        super.setUp()

    }

    fun getDataFor(playlistParcelable: PlaylistParcelable) {
        playlistDetails.value = playlistParcelable

        addDisposable(repository.getPlayListDetails(playlistParcelable.id)
                //Ideally should do this mapping in the repository
                .map {
                    it.items
                }
                .subscribe({
                    playlistItems.value = it
                }, {
                    handleError(it)
                }))
    }

    companion object {
        val YOUTUBE_LIST = 1
        val SOUNDCLOUD_LIST = 2
    }

}