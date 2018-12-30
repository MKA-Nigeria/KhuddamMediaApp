package com.aliumujib.tabbarseed.data.model

import android.os.Parcel
import android.os.Parcelable


data class PlaylistParcelable(val name: String,
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

        val TYPE_PLAYLIST_YOUTUBE = 3
        val TYPE_PLAYLIST_SOUNDCLOUD = 4

        fun fromPlayList(youtubePlayList: YoutubePlayList): PlaylistParcelable {
            return PlaylistParcelable(youtubePlayList.snippet.title, youtubePlayList.snippet.description,
                    youtubePlayList.snippet.thumbnails.defaultThumb.url, youtubePlayList.id, TYPE_PLAYLIST_YOUTUBE)
        }

        fun fromPlayList(playList: SoundCloudPlayList): PlaylistParcelable {
            return PlaylistParcelable(playList.title, playList.description ?: "N/A",
                    playList.artwork_url ?: "N/A", playList.id.toString(), TYPE_PLAYLIST_SOUNDCLOUD)
        }

        @JvmField
        val CREATOR: Parcelable.Creator<PlaylistParcelable> = object : Parcelable.Creator<PlaylistParcelable> {
            override fun createFromParcel(source: Parcel): PlaylistParcelable = PlaylistParcelable(source)
            override fun newArray(size: Int): Array<PlaylistParcelable?> = arrayOfNulls(size)
        }
    }
}