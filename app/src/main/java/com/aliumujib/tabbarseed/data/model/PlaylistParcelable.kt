package com.aliumujib.tabbarseed.data.model

import android.os.Parcel
import android.os.Parcelable


data class PlaylistParcelable(val name: String,
                              val description: String,
                              val imageURL: String,
                              val id: String) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(description)
        writeString(imageURL)
        writeString(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PlaylistParcelable> = object : Parcelable.Creator<PlaylistParcelable> {
            override fun createFromParcel(source: Parcel): PlaylistParcelable = PlaylistParcelable(source)
            override fun newArray(size: Int): Array<PlaylistParcelable?> = arrayOfNulls(size)
        }

        fun fromPlayList(playList: PlayList): PlaylistParcelable {
            return PlaylistParcelable(playList.snippet.title, playList.snippet.description,
                    playList.snippet.thumbnails.defaultThumb.url, playList.id)
        }
    }
}