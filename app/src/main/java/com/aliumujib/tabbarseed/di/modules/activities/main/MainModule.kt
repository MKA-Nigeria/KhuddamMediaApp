package com.aliumujib.tabbarseed.di.modules.activities.main

import android.os.Bundle
import com.aliumujib.tabbarseed.di.scopes.PerActivity
import com.aliumujib.tabbarseed.ui.main.MainActivity
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.ui.main.MainFragmentNavigation
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class MainModule {


    @PerActivity
    @Provides
    fun providesMainNavigator(activity: MainActivity): IMainFragmentNavigation {
        return MainFragmentNavigation(activity, Bundle())
    }

}