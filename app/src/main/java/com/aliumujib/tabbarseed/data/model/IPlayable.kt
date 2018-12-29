package com.aliumujib.tabbarseed.data.model

import android.os.Parcel
import android.os.Parcelable

interface IPlayable {
    val title: String
    val date: String
    val duration: String
    val hasDurationData: Boolean
}


data class PlayableParcelable(val name: String,
                              val description: String,
                              val imageURL: String,
                              val id: String,
                              val type: Int) : Parcelable {


    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(description)
        writeString(imageURL)
        writeString(id)
        writeInt(type)
    }

    companion object {

        val TYPE_YOUTUBE_VIDEO = 4
        val TYPE_SOUNDCLOUD_TRACK = 5

        fun fromYoutubeVideo(youtubeVideo: PlayListItem): PlayableParcelable {
            return PlayableParcelable(youtubeVideo.snippet.title, youtubeVideo.snippet.description,
                    youtubeVideo.snippet.thumbnails.defaultThumb.url, youtubeVideo.contentDetails.videoId,
                    TYPE_YOUTUBE_VIDEO)
        }

        fun fromSoundCloudTrack(soundCloudTrack: Track): PlayableParcelable {
            return PlayableParcelable(soundCloudTrack.title, soundCloudTrack.description,
                    soundCloudTrack.artwork_url?:"N/A", soundCloudTrack.id.toString(), TYPE_SOUNDCLOUD_TRACK)
        }

        @JvmField
        val CREATOR: Parcelable.Creator<PlayableParcelable> = object : Parcelable.Creator<PlayableParcelable> {
            override fun createFromParcel(source: Parcel): PlayableParcelable = PlayableParcelable(source)
            override fun newArray(size: Int): Array<PlayableParcelable?> = arrayOfNulls(size)
        }
    }
}