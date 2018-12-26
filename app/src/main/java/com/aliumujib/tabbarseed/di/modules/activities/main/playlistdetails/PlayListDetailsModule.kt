package com.aliumujib.tabbarseed.di.modules.activities.main.playlistdetails


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.contracts.ISoundCloudRepository
import com.aliumujib.tabbarseed.data.contracts.IYoutubeRepository
import com.aliumujib.tabbarseed.data.model.IPlayable
import com.aliumujib.tabbarseed.data.repositories.SoundCloudRepository
import com.aliumujib.tabbarseed.data.repositories.YoutubeRepository
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.adapter.base.SingleLayoutAdapter
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.ui.main.fragments.me.MeViewModel
import com.aliumujib.tabbarseed.ui.main.fragments.playlistdetails.PlaylistDetailsFragment
import com.aliumujib.tabbarseed.ui.main.fragments.playlistdetails.PlaylistDetailsViewModel
import com.aliumujib.tabbarseed.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class PlayListDetailsModule {

    @PerFragment
    @Provides
    fun providesVMFactory(mainFragmentNavigation: IMainFragmentNavigation, youtubeRepository: IYoutubeRepository, soundCloudRepository: ISoundCloudRepository): ViewModelFactory<PlaylistDetailsViewModel> {
        return ViewModelFactory(lazyOf(PlaylistDetailsViewModel(mainFragmentNavigation, youtubeRepository,soundCloudRepository)))
    }

    @PerFragment
    @Provides
    fun providesVM(viewModelFactory: ViewModelFactory<PlaylistDetailsViewModel>, fragment: PlaylistDetailsFragment): PlaylistDetailsViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(PlaylistDetailsViewModel::class.java)
    }


    @PerFragment
    @Provides
    fun providesSingleLayoutAdapter(fragment: PlaylistDetailsFragment): SingleLayoutAdapter<IPlayable> {
        return SingleLayoutAdapter(R.layout.item_playable, fragment)
    }

}