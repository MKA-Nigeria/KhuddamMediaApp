package com.aliumujib.tabbarseed.di.modules.global.media

import android.content.Context
import com.aliumujib.tabbarseed.di.scopes.ApplicationScope
import com.aliumujib.tabbarseed.ui.main.ComponentListener
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import dagger.Module
import dagger.Provides

/**
 * Created by aliumujib on 12/05/2018.
 */

@Module
class MediaPlayerModule {

    @ApplicationScope
    @Provides
    fun providesTrackSelection(bandwidthMeter: BandwidthMeter): TrackSelection.Factory {
        return AdaptiveTrackSelection.Factory(bandwidthMeter)
    }

    @ApplicationScope
    @Provides
    fun providesDefaultBandwidthMeter(): BandwidthMeter {
        return DefaultBandwidthMeter()
    }

    @ApplicationScope
    @Provides
    fun providesComponentListener(): ComponentListener {
        return ComponentListener()
    }

    @ApplicationScope
    @Provides
    fun providesExoPlayer(context: Context, trackSelectionFactory: TrackSelection.Factory, componentListener: ComponentListener): ExoPlayer {
        val exoPlayer = ExoPlayerFactory.newSimpleInstance(context,
                DefaultTrackSelector(trackSelectionFactory), DefaultLoadControl())
        exoPlayer.addListener(componentListener)
        exoPlayer.addAnalyticsListener(componentListener)
        return exoPlayer
    }

}