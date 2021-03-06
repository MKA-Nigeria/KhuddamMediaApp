package com.aliumujib.tabbarseed.di.modules.activities.main.me


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverFragment
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverViewModel
import com.aliumujib.tabbarseed.ui.main.fragments.me.MeFragment
import com.aliumujib.tabbarseed.ui.main.fragments.me.MeViewModel
import com.aliumujib.tabbarseed.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class MeModule {

    @PerFragment
    @Provides
    fun providesVMFactory(mainFragmentNavigation: IMainFragmentNavigation): ViewModelFactory<MeViewModel> {
        return ViewModelFactory(lazyOf(MeViewModel(mainFragmentNavigation)))
    }

    @PerFragment
    @Provides
    fun providesVM(viewModelFactory: ViewModelFactory<MeViewModel>, fragment: MeFragment): MeViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(MeViewModel::class.java)
    }


//    @PerFragment
//    @Provides
//    fun providesStatAdapter(context: Context, fragment: PVCAdminStatsFragment): StatAdapter {
//        return StatAdapter(context, fragment)
//    }

}