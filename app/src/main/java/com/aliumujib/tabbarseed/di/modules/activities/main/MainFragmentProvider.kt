package com.aliumujib.tabbarseed.di.modules.activities.main

import com.aliumujib.tabbarseed.di.modules.activities.main.discover.DiscoverModule
import com.aliumujib.tabbarseed.di.modules.activities.main.me.MeModule
import com.aliumujib.tabbarseed.di.modules.activities.main.playlistdetails.PlayListDetailsModule
import com.aliumujib.tabbarseed.di.modules.activities.main.podcasts.PodcastsModule
import com.aliumujib.tabbarseed.di.modules.activities.main.videos.VideosModule
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.main.fragments.discover.DiscoverFragment
import com.aliumujib.tabbarseed.ui.main.fragments.me.MeFragment
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.PodcastsFragment
import com.aliumujib.tabbarseed.ui.main.fragments.playlistdetails.PlaylistDetailsFragment
import com.aliumujib.tabbarseed.ui.main.fragments.videos.VideosFragment
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.fragments.YoutubeVideoPlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainFragmentProvider {

    @PerFragment
    @ContributesAndroidInjector(modules = [VideosModule::class])
    internal abstract fun bindsVideosFragment(): VideosFragment


    @PerFragment
    @ContributesAndroidInjector(modules = [PodcastsModule::class])
    internal abstract fun bindsPodcastsFragment(): PodcastsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [MeModule::class])
    internal abstract fun bindsMeFragment(): MeFragment

    @PerFragment
    @ContributesAndroidInjector()
    internal abstract fun bindsYoutubePlayerFragment(): YoutubeVideoPlayerFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [DiscoverModule::class])
    internal abstract fun bindsDiscoverFragment(): DiscoverFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PlayListDetailsModule::class])
    internal abstract fun bindsPlaylistDetailsFragment(): PlaylistDetailsFragment

}