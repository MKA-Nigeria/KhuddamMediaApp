package com.aliumujib.tabbarseed.di.modules.global

import android.content.Context
import com.aliumujib.tabbarseed.ApplicationClass
import com.aliumujib.tabbarseed.di.modules.global.media.MediaPlayerModule
import com.aliumujib.tabbarseed.di.modules.global.network.RepositoryModule
import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import com.aliumujib.tabbarseed.utils.AppSchedulers
import com.aliumujib.tabbarseed.utils.Schedulers
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created by ayokunlepaul on 27/11/2018.
 */
@Module(includes = [RepositoryModule::class, MediaPlayerModule::class])
 class ApplicationModule {

    @ApplicationScope @Provides
    fun provideApplicationContext(application: ApplicationClass): Context{
        return application
    }

    @ApplicationScope
    @Provides
    fun providesAppScheduler(): Schedulers {
        return AppSchedulers()
    }

    /**
     * Other external instances should be provided here.
     */
}