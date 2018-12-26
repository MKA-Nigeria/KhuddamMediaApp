package com.aliumujib.tabbarseed.data.contracts

import com.aliumujib.tabbarseed.data.model.*
import io.reactivex.Observable

interface ISoundCloudRepository {

    fun getSoundCloudAccountPlaylists(id: String): Observable<List<SoundCloudPlayList>>

    fun getSoundCloudAccountAudios(id: String): Observable<List<Track>>

    fun getAllPlaylists(list: List<String>): Observable<List<SoundCloudPlayList>>

    fun getAllAudios(list: List<String>): Observable<List<Track>>

    fun getSinglePlaylist(id: String): Observable<SoundCloudPlayList>
}