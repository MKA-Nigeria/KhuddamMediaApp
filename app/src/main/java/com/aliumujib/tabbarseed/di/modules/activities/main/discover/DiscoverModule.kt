package com.aliumujib.tabbarseed.di.modules.activities.main.discover


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverFragment
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverViewModel
import com.aliumujib.tabbarseed.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class DiscoverModule {

    @PerFragment
    @Provides
    fun providesVMFactory(mainFragmentNavigation: IMainFragmentNavigation): ViewModelFactory<DiscoverViewModel> {
        return ViewModelFactory(lazyOf(DiscoverViewModel(mainFragmentNavigation)))
    }

    @PerFragment
    @Provides
    fun providesVM(viewModelFactory: ViewModelFactory<DiscoverViewModel>, fragment: DiscoverFragment): DiscoverViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(DiscoverViewModel::class.java)
    }


//    @PerFragment
//    @Provides
//    fun providesStatAdapter(context: Context, fragment: PVCAdminStatsFragment): StatAdapter {
//        return StatAdapter(context, fragment)
//    }

}