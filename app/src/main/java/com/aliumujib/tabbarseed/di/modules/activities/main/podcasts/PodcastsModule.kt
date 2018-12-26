package com.aliumujib.tabbarseed.di.modules.activities.main.podcasts


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.contracts.ISoundCloudRepository
import com.aliumujib.tabbarseed.data.model.IPlayable
import com.aliumujib.tabbarseed.data.model.SoundCloudPlayList
import com.aliumujib.tabbarseed.data.model.Track
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.adapter.base.SingleLayoutAdapter
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.OnSoundCloudPlaylistClickListener
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.OnTrackClickListener
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.PodcastsFragment
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.PodcastsViewModel
import com.aliumujib.tabbarseed.ui.main.fragments.videos.OnVideoClickListener
import com.aliumujib.tabbarseed.ui.main.fragments.videos.OnYoutubePlaylistClickListener
import com.aliumujib.tabbarseed.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class PodcastsModule {

    @PerFragment
    @Provides
    fun providesVMFactory(mainFragmentNavigation: IMainFragmentNavigation, soundCloudRepository: ISoundCloudRepository): ViewModelFactory<PodcastsViewModel> {
        return ViewModelFactory(lazyOf(PodcastsViewModel(mainFragmentNavigation, soundCloudRepository)))
    }

    @PerFragment
    @Provides
    fun providesVM(viewModelFactory: ViewModelFactory<PodcastsViewModel>, fragment: PodcastsFragment): PodcastsViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(PodcastsViewModel::class.java)
    }

    @PerFragment
    @Provides
    fun providesOnPlaylistClickListener(mainFragmentNavigation: IMainFragmentNavigation): OnSoundCloudPlaylistClickListener {
        return OnSoundCloudPlaylistClickListener(mainFragmentNavigation)
    }

    @PerFragment
    @Provides
    fun providesOnTrackClickListener(mainFragmentNavigation: IMainFragmentNavigation): OnTrackClickListener {
        return OnTrackClickListener(mainFragmentNavigation)
    }

    @PerFragment
    @Provides
    fun providesPlayListAdapter(onSoundCloudPlaylistClickListener: OnSoundCloudPlaylistClickListener): SingleLayoutAdapter<SoundCloudPlayList> {
        return SingleLayoutAdapter(R.layout.item_podcast_playlist, onSoundCloudPlaylistClickListener)
    }

    @PerFragment
    @Provides
    fun providesTracksAdapter(onTrackClickListener: OnTrackClickListener): SingleLayoutAdapter<Track> {
        return SingleLayoutAdapter(R.layout.item_playable, onTrackClickListener)
    }

}