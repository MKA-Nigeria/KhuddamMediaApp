package com.aliumujib.tabbarseed.data.contracts

import com.aliumujib.tabbarseed.data.model.YoutubePlayList
import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.data.model.PlaylistItemResponse
import io.reactivex.Observable

interface IYoutubeRepository {

    fun getYoutubeChannelPlaylists(list: List<String>): Observable<List<YoutubePlayList>>

    fun getYoutubeChannelVideos(list: List<String>): Observable<List<PlayListItem>>

   fun getPlayListDetails(id: String): Observable<PlaylistItemResponse>

}