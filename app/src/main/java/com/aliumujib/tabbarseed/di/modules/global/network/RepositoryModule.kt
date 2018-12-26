package com.aliumujib.tabbarseed.di.modules.global.network


import com.aliumujib.tabbarseed.data.contracts.ISoundCloudRepository
import com.aliumujib.tabbarseed.data.contracts.IYoutubeRepository
import com.aliumujib.tabbarseed.data.repositories.SoundCloudRepository
import com.aliumujib.tabbarseed.data.repositories.YoutubeRepository
import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 26/05/2018.
 */

@Module(includes = [RetrofitModule::class])
class RepositoryModule {


    @ApplicationScope
    @Provides
    fun provideYoutubeRepository(youtubeRepository: YoutubeRepository): IYoutubeRepository {
        return youtubeRepository
    }


    @ApplicationScope
    @Provides
    fun provideSoundCloudRepository(soundCloudRepository: SoundCloudRepository): ISoundCloudRepository {
        return soundCloudRepository
    }


}