package com.aliumujib.tabbarseed.di.modules.activities.main.videos


import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.contracts.IYoutubeRepository
import com.aliumujib.tabbarseed.data.model.PlayList
import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.di.scopes.PerFragment
import com.aliumujib.tabbarseed.ui.adapter.base.SingleLayoutAdapter
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation
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
    fun providesPlayListAdapter(): SingleLayoutAdapter<PlayList> {
        return SingleLayoutAdapter(R.layout.item_video_playlist)
    }


    @PerFragment
    @Provides
    fun providesPlayListItemAdapter(): SingleLayoutAdapter<PlayListItem> {
        return SingleLayoutAdapter(R.layout.item_video)
    }

}