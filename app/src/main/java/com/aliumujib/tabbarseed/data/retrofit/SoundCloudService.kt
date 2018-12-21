package com.aliumujib.tabbarseed.data.retrofit

import com.aliumujib.tabbarseed.data.model.SoundCloudPlayList
import com.aliumujib.tabbarseed.data.model.Track
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Abdul-Mujeeb Aliu  on 10-10-2017
 *
 * Interface to get data from SoundCloud that will be used by Retrofit2
 */

interface SoundCloudService {

    @GET("users/{userId}/tracks")
    fun getTracks(@Path("userId") userId: String): Observable<List<Track>>


    @GET("users/{userId}/playlists")
    fun getPlayLists(@Path("userId") userId: String): Observable<List<SoundCloudPlayList>>

}
