package com.aliumujib.tabbarseed.data.retrofit

import com.aliumujib.tabbarseed.data.model.PlaylistItemResponse
import com.aliumujib.tabbarseed.data.model.PlaylistListResponse
import com.aliumujib.tabbarseed.data.model.YoutubeVideoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by abdulmujibaliu on 10/15/17.
 */

interface YoutubeService {

    @GET("playlistItems")
    fun getPlaylistItems(@QueryMap data: HashMap<String, Any>) : Observable<PlaylistItemResponse>

    @GET("playlists")
    fun getPlaylistsForChannel(@QueryMap data: HashMap<String, Any>) : Observable<PlaylistListResponse>

    @GET("videos")
    fun getVideoItems(@QueryMap data: HashMap<String, Any>) : Observable<YoutubeVideoResponse>

}