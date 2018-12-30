package com.aliumujib.tabbarseed.di.modules.activities.main

import android.os.Bundle
import com.aliumujib.tabbarseed.di.scopes.PerActivity
import com.aliumujib.tabbarseed.ui.main.*
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class MainModule {


    @PerActivity
    @Provides
    fun providesMainNavigator(activity: MainActivity, playbackVC: IPlaybackVC): IMainFragmentNavigation {
        return MainFragmentNavigation(activity, playbackVC, Bundle())
    }

    @PerActivity
    @Provides
    fun providesPlaybackVC(playbackViewController: PlaybackViewController): IPlaybackVC {
        return playbackViewController
    }

}