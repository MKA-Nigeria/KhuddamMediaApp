package com.aliumujib.tabbarseed.di.modules.activities.main.videos


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverFragment
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverViewModel
import com.aliumujib.tabbarseed.ui.main.fragments.me.MeViewModel
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.PodcastsViewModel
import com.aliumujib.tabbarseed.ui.main.fragments.videos.VideosFragment
import com.aliumujib.tabbarseed.ui.main.fragments.videos.VideosViewModel
import com.aliumujib.tabbarseed.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class VideosModule {

    @PerFragment
    @Provides
    fun providesVMFactory(mainFragmentNavigation: IMainFragmentNavigation): ViewModelFactory<VideosViewModel> {
        return ViewModelFactory(lazyOf(VideosViewModel(mainFragmentNavigation)))
    }

    @PerFragment
    @Provides
    fun providesVM(viewModelFactory: ViewModelFactory<VideosViewModel>, fragment: VideosFragment): VideosViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(VideosViewModel::class.java)
    }


//    @PerFragment
//    @Provides
//    fun providesStatAdapter(context: Context, fragment: PVCAdminStatsFragment): StatAdapter {
//        return StatAdapter(context, fragment)
//    }

}