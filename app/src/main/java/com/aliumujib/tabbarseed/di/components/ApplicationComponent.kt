package com.aliumujib.tabbarseed.di.components

import com.aliumujib.tabbarseed.ApplicationClass
import com.aliumujib.tabbarseed.di.modules.global.ActivityBuilder
import com.aliumujib.tabbarseed.di.modules.global.ApplicationModule
import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

/**
 * Created by ayokunlepaul on 27/11/2018.
 */
@Component(modules = [AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    ApplicationModule::class])

@ApplicationScope
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplicationInstance(application: ApplicationClass): Builder

        fun buildApplicationComponent(): ApplicationComponent
    }

}