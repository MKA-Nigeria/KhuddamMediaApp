package com.aliumujib.tabbarseed.data.contracts

import com.aliumujib.tabbarseed.data.model.*
import io.reactivex.Observable

interface IYoutubeRepository {

    fun getYoutubeChannelPlaylists(list: List<String>): Observable<List<YoutubePlayList>>

    fun getYoutubeChannelVideos(list: List<String>): Observable<List<PlayListItem>>

    fun getPlayListDetails(id: String): Observable<PlaylistItemResponse>

    fun getVideoDetails(id: String): Observable<YoutubeVideo>

}