package com.aliumujib.tabbarseed.di.modules.activities.main

import android.os.Bundle
import com.aliumujib.tabbarseed.di.scopes.PerActivity
import com.aliumujib.tabbarseed.ui.main.*
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.AudioPlaybackViewController
import com.aliumujib.tabbarseed.ui.main.fragments.podcasts.IAudioPlaybackVC
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.IVideoPlayerVC
import com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.VideoPlayerViewController
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 14/05/2018.
 */

@Module
class MainModule {


    @PerActivity
    @Provides
    fun providesMainNavigator(activity: MainActivity, videoPlayerVC: IVideoPlayerVC, audioPlaybackVC: IAudioPlaybackVC): IMainFragmentNavigation {
        return MainFragmentNavigation(activity, audioPlaybackVC, videoPlayerVC, Bundle())
    }

    @PerActivity
    @Provides
    fun providesAudioPlaybackVC(audioPlaybackViewController: AudioPlaybackViewController): IAudioPlaybackVC {
        return audioPlaybackViewController
    }

    @PerActivity
    @Provides
    fun providesVideoPlaybackVC(videoPlayerVC: VideoPlayerViewController): IVideoPlayerVC {
        return videoPlayerVC
    }

}