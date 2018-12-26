package com.aliumujib.tabbarseed.data.repositories

import com.aliumujib.tabbarseed.data.contracts.IYoutubeRepository
import com.aliumujib.tabbarseed.data.model.*
import com.aliumujib.tabbarseed.data.retrofit.YoutubeService
import com.aliumujib.tabbarseed.utils.Schedulers
import io.reactivex.Observable
import javax.inject.Inject

class YoutubeRepository @Inject constructor(var youtubeService: YoutubeService,
                                            var schedulers: Schedulers) : IYoutubeRepository {

    override fun getPlayListDetails(id: String): Observable<PlaylistItemResponse> {
        return youtubeService.getPlaylistItems(constructQueriesForPlayListID(id))
                .subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)
    }


    override fun getYoutubeChannelVideos(list: List<String>): Observable<List<PlayListItem>> {
        return getYoutubeChannelPlaylists(list).flatMap {
            val requestList = mutableListOf<Observable<PlaylistItemResponse>>()
            it.forEach {
                requestList.add(getPlayListDetails(it.id))
            }

            Observable.zip(requestList) {
                val data = it
                val playlist = mutableListOf<PlayListItem>()

                data.forEach {
                    playlist.addAll((it as PlaylistItemResponse).items)
                }

                return@zip playlist
            }
        }
    }

    override fun getYoutubeChannelPlaylists(list: List<String>): Observable<List<YoutubePlayList>> {
        val requestList = mutableListOf<Observable<PlaylistListResponse>>()
        list.forEach {
            requestList.add(youtubeService.getPlaylistsForChannel(constructQueriesForChannelID(it))
                    .subscribeOn(schedulers.subscribeOn)
                    .observeOn(schedulers.observeOn))
        }

        return Observable.zip(requestList) { it ->
            val data = it
            val playlist = mutableListOf<YoutubePlayList>()

            data.forEach {
                playlist.addAll((it as PlaylistListResponse).items)
            }

            return@zip playlist.filter {
                it.contentDetails.itemCount > 0
            }

        }.map {
            it.toList()
        }.subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)
    }


    /**
     *
     * TODO
     * Replace both these methods with an interceptor
     * **/

    fun constructQueriesForChannelID(string: String): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["channelId"] = string
        hashMap["maxResults"] = 25
//        hashMap["part"] = "snippet,contentDetails"
//        hashMap["key"] = "AIzaSyCzTQAdni52z7AR6vLPBVoM75FES9BIUTw"
        return hashMap
    }


    fun constructQueriesForPlayListID(string: String): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["playlistId"] = string
        hashMap["maxResults"] = 25
//        hashMap["part"] = "snippet,contentDetails"
//        hashMap["key"] = "AIzaSyCzTQAdni52z7AR6vLPBVoM75FES9BIUTw"
        return hashMap
    }

}