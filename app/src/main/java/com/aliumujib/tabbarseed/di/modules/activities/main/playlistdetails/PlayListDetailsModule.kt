package com.aliumujib.tabbarseed.di.modules.activities.main.playlistdetails


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.di.scopes.PerFragment
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
    fun providesVMFactory(mainFragmentNavigation: IMainFragmentNavigation): ViewModelFactory<PlaylistDetailsViewModel> {
        return ViewModelFactory(lazyOf(PlaylistDetailsViewModel(mainFragmentNavigation)))
    }

    @PerFragment
    @Provides
    fun providesVM(viewModelFactory: ViewModelFactory<PlaylistDetailsViewModel>, fragment: PlaylistDetailsFragment): PlaylistDetailsViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(PlaylistDetailsViewModel::class.java)
    }


//    @PerFragment
//    @Provides
//    fun providesStatAdapter(context: Context, fragment: PVCAdminStatsFragment): StatAdapter {
//        return StatAdapter(context, fragment)
//    }

}