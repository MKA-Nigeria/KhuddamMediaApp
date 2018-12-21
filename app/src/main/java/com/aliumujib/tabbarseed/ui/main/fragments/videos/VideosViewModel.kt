package com.aliumujib.tabbarseed.ui.main.fragments.videos

import com.aliumujib.tabbarseed.data.contracts.IYoutubeRepository
import com.aliumujib.tabbarseed.data.model.PlayList
import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.ui.base.BaseViewModel
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.utils.extensions.mutableLiveDataOf

class VideosViewModel(var fragmentNavigation: IMainFragmentNavigation,
                      var videoRepository: IYoutubeRepository) : BaseViewModel() {

     val listOfChannelPlaylists = mutableLiveDataOf<List<PlayList>>()
    val listOfChannelVideos = mutableLiveDataOf<List<PlayListItem>>()

    override fun setUp() {
        super.setUp()

        addDisposable(videoRepository.getYoutubeChannelPlaylists(listOf("UCpEHs4jtfj1sTo1g-ubDyMg")).subscribe({
            listOfChannelPlaylists.value = it
        },{
            it.printStackTrace()
            handleError(it)
        }))


        addDisposable(videoRepository.getYoutubeChannelVideos(listOf("UCpEHs4jtfj1sTo1g-ubDyMg")).subscribe({
            listOfChannelVideos.value = it
        },{
            it.printStackTrace()
            handleError(it)
        }))
    }

}