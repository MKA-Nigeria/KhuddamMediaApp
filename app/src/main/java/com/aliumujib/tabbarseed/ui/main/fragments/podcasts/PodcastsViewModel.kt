package com.aliumujib.tabbarseed.ui.main.fragments.podcasts

import com.aliumujib.tabbarseed.data.contracts.ISoundCloudRepository
import com.aliumujib.tabbarseed.data.model.SoundCloudPlayList
import com.aliumujib.tabbarseed.data.model.Track
import com.aliumujib.tabbarseed.data.repositories.SoundCloudRepository
import com.aliumujib.tabbarseed.ui.base.BaseViewModel
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.utils.extensions.mutableLiveDataOf

class PodcastsViewModel(var fragmentNavigation: IMainFragmentNavigation, var soundCloudRepository: ISoundCloudRepository) : BaseViewModel() {


    val playLists = mutableLiveDataOf<List<SoundCloudPlayList>>()
    val tracks = mutableLiveDataOf<List<Track>>()


    val MKAN_NG = "204709701"
    val VOICE_OF_ISLAM = "182328357"

    override fun setUp() {
        super.setUp()

        addDisposable(soundCloudRepository
                .getAllPlaylists(mutableListOf(MKAN_NG, VOICE_OF_ISLAM))
                .subscribe({
                    playLists.value = it
                }, {
                    handleError(it)
                }))

        addDisposable(soundCloudRepository
                .getAllAudios(mutableListOf(MKAN_NG, VOICE_OF_ISLAM))
                .subscribe({
                    tracks.value = it
                }, {
                    handleError(it)
                }))
    }

}