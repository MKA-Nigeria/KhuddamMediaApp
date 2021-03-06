package com.aliumujib.tabbarseed.di.modules.global.network

import com.aliumujib.tabbarseed.BuildConfig
import com.aliumujib.tabbarseed.data.retrofit.RedirectInterceptor
import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import com.aliumujib.tabbarseed.utils.SoundCloudQueryInterceptor
import com.aliumujib.tabbarseed.utils.TabBarSeedLog
import com.aliumujib.tabbarseed.utils.YoutubeQueryInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by ayokunlepaul on 27/11/2018.
 */
@Module
class InterceptorsModule {

    @ApplicationScope
    @Provides
    internal fun provideRedirectInterceptor(): RedirectInterceptor = RedirectInterceptor()

    @ApplicationScope
    @Provides
    internal fun provideYoutubeQueryInterceptor(): YoutubeQueryInterceptor = YoutubeQueryInterceptor()

    @ApplicationScope
    @Provides
    internal fun provideSoundCloudQueryInterceptor(): SoundCloudQueryInterceptor = SoundCloudQueryInterceptor()

    @ApplicationScope
    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            TabBarSeedLog.d(it)
        })
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        return loggingInterceptor
    }
}