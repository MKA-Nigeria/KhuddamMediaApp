package com.aliumujib.tabbarseed.di.modules.global.network

import com.aliumujib.tabbarseed.BuildConfig
import com.aliumujib.tabbarseed.data.retrofit.SoundCloudService
import com.aliumujib.tabbarseed.data.retrofit.YoutubeService
import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import com.aliumujib.tabbarseed.data.retrofit.SoundCloudQueryInterceptor
import com.aliumujib.tabbarseed.data.retrofit.YoutubeQueryInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
                          httpLoggingInterceptor: HttpLoggingInterceptor,
                          converterFactory: GsonConverterFactory): YoutubeService {

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(youtubeQueryInterceptor)
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
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
                             httpLoggingInterceptor: HttpLoggingInterceptor,
                             soundCloudQueryInterceptor: SoundCloudQueryInterceptor,
                             converterFactory: GsonConverterFactory): SoundCloudService {

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        httpClientBuilder.addInterceptor(soundCloudQueryInterceptor)
        httpClientBuilder.followRedirects(false)
        httpClientBuilder.followSslRedirects(false)
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
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