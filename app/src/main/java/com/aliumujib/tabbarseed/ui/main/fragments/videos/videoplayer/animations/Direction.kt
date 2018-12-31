package com.aliumujib.tabbarseed.ui.main.fragments.videos.videoplayer.animations

/**
 * Created by Burhanuddin Rashid on 2/27/2018.
 */

/**
 * Direction sealed class used as enum for defining directions
 */
sealed class Direction {
    class LEFT : Direction()
    class RIGHT : Direction()
    class UP : Direction()
    class DOWN : Direction()
    class NONE : Direction()
}