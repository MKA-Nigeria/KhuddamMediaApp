package com.aliumujib.tabbarseed.di.modules.global.network

import com.aliumujib.tabbarseed.BuildConfig
import com.aliumujib.tabbarseed.data.retrofit.SoundCloudService
import com.aliumujib.tabbarseed.data.retrofit.YoutubeService
import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import com.aliumujib.tabbarseed.utils.SoundCloudQueryInterceptor
import com.aliumujib.tabbarseed.utils.YoutubeQueryInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ayokunlepaul on 27/11/2018.
 */
@Module(includes = [FactoryModule::class, InterceptorsModule::class])
class RetrofitModule {

    //I am too lazy to construct singleton generators for each component.. sooo

    @ApplicationScope
    @Provides
    fun provideYoutubeApi(callAdapterFactory: RxJava2CallAdapterFactory,
                          youtubeQueryInterceptor: YoutubeQueryInterceptor,
                          converterFactory: GsonConverterFactory): YoutubeService {

        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.addInterceptor(youtubeQueryInterceptor)
        httpClientBuilder.followRedirects(false)
        httpClientBuilder.followSslRedirects(false)

        val httpClient = httpClientBuilder.build()


        val builder = Retrofit.Builder()
        builder.baseUrl(BuildConfig.YOUTUBE_API_URL)
        builder.addCallAdapterFactory(callAdapterFactory)
        builder.addConverterFactory(converterFactory)
        builder.client(httpClient)
        val retrofit2 = builder.build()
        return retrofit2.create(YoutubeService::class.java)
    }


    @ApplicationScope
    @Provides
    fun provideSoundCloudApi(callAdapterFactory: RxJava2CallAdapterFactory,
                             soundCloudQueryInterceptor: SoundCloudQueryInterceptor,
                             converterFactory: GsonConverterFactory): SoundCloudService {

        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.addInterceptor(soundCloudQueryInterceptor)
        httpClientBuilder.followRedirects(false)
        httpClientBuilder.followSslRedirects(false)

        val httpClient = httpClientBuilder.build()


        val builder = Retrofit.Builder()
        builder.baseUrl(BuildConfig.SOUNDCLOUD_API_URL)
        builder.addCallAdapterFactory(callAdapterFactory)
        builder.addConverterFactory(converterFactory)
        builder.client(httpClient)
        val retrofit2 = builder.build()
        return retrofit2.create(SoundCloudService::class.java)
    }

}