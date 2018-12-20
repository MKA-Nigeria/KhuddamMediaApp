package com.aliumujib.tabbarseed.data.contracts

import com.aliumujib.tabbarseed.data.model.PlayList
import com.aliumujib.tabbarseed.data.model.PlayListItem
import com.aliumujib.tabbarseed.data.model.PlaylistListResponse
import io.reactivex.Observable

interface IYoutubeRepository {

    fun getYoutubeChannelPlaylists(list: List<String>): Observable<List<PlayList>>

    fun getYoutubeChannelVideos(list: List<String>): Observable<List<PlayListItem>>

}