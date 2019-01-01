package com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments

import com.aliumujib.tabbarseed.data.model.RepositoryEntity
import com.aliumujib.tabbarseed.data.model.YoutubeVideo
import com.aliumujib.tabbarseed.data.repositories.YoutubeRepository
import com.aliumujib.tabbarseed.ui.base.BaseViewModel
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.utils.extensions.mutableLiveDataOf
import com.aliumujib.tabbarseed.utils.extensions.singleLiveDataOf

class YoutubeVideoDetailViewModel(var fragmentNavigation: IMainFragmentNavigation,
                                  var repository: YoutubeRepository) : BaseViewModel() {

    val video = mutableLiveDataOf<YoutubeVideo>()

    override fun setUp() {
        super.setUp()

    }

    fun setVideoId(videoId: String) {
        addDisposable(repository.getVideoDetails(videoId)
                .subscribe({
                    video.value = it
                }, {
                    handleError(exception = it)
                }))
    }

}