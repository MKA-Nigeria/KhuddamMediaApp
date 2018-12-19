package com.aliumujib.tabbarseed.di.modules.global

import com.aliumujib.tabbarseed.di.modules.activities.main.MainFragmentProvider
import com.aliumujib.tabbarseed.di.modules.activities.main.MainModule
import com.aliumujib.tabbarseed.di.scopes.PerActivity
import com.aliumujib.tabbarseed.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ayokunlepaul on 27/11/2018.
 */

@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity


    /**
     * Other activities and their sub-components should be added here
     */
}