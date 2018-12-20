package com.aliumujib.tabbarseed.di.modules.activities.main.videos


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.contracts.IYoutubeRepository
import com.aliumujib.tabbarseed.data.model.PlayList
import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.adapter.base.SingleLayoutAdapter
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
import com.aliumujib.tabbarseed.ui.main.fragments.videos.OnPlaylistClickListener
import com.aliumujib.tabbarseed.ui.main.fragments.videos.OnVideoClickListener
import com.aliumujib.tabbarseed.ui.main.fragments.videos.VideosFragment
import com.aliumujib.tabbarseed.ui.main.fragments.videos.VideosViewModel
import com.aliumujib.tabbarseed.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class VideosModule {

    @PerFragment
    @Provides
    fun providesVMFactory(mainFragmentNavigation: IMainFragmentNavigation, youtubeRepository: IYoutubeRepository): ViewModelFactory<VideosViewModel> {
        return ViewModelFactory(lazyOf(VideosViewModel(mainFragmentNavigation, youtubeRepository)))
    }

    @PerFragment
    @Provides
    fun providesVM(viewModelFactory: ViewModelFactory<VideosViewModel>, fragment: VideosFragment): VideosViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(VideosViewModel::class.java)
    }


    @PerFragment
    @Provides
    fun providesPlayListAdapter(onPlaylistClickListener: OnPlaylistClickListener): SingleLayoutAdapter<PlayList> {
        return SingleLayoutAdapter(R.layout.item_video_playlist, onPlaylistClickListener)
    }

    @PerFragment
    @Provides
    fun providesOnPlaylistClickListener(mainFragmentNavigation: IMainFragmentNavigation): OnPlaylistClickListener {
        return OnPlaylistClickListener(mainFragmentNavigation)
    }

    @PerFragment
    @Provides
    fun providesOnVideoClickListener(mainFragmentNavigation: IMainFragmentNavigation): OnVideoClickListener {
        return OnVideoClickListener(mainFragmentNavigation)
    }


    @PerFragment
    @Provides
    fun providesPlayListItemAdapter(onVideoClickListener: OnVideoClickListener): SingleLayoutAdapter<PlayListItem> {
        return SingleLayoutAdapter(R.layout.item_video, onVideoClickListener)
    }

}