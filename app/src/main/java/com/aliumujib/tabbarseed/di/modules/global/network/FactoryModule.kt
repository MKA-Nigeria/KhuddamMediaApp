package com.aliumujib.tabbarseed.di.modules.global.network

import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ayokunlepaul on 27/11/2018.
 */
@Module
class FactoryModule {

    @ApplicationScope @Provides
    internal fun provideRxJava2AdapterFactory() = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @ApplicationScope @Provides
    internal fun provideGsonConverterFactory() = GsonConverterFactory.create()
}