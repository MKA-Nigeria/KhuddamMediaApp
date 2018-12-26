package com.aliumujib.tabbarseed.data.repositories

import com.aliumujib.tabbarseed.data.contracts.ISoundCloudRepository
import com.aliumujib.tabbarseed.data.model.SoundCloudPlayList
import com.aliumujib.tabbarseed.data.model.Track
import com.aliumujib.tabbarseed.data.retrofit.SoundCloudService
import com.aliumujib.tabbarseed.utils.Schedulers
import io.reactivex.Observable
import javax.inject.Inject

class SoundCloudRepository @Inject constructor(var soundCloudService: SoundCloudService,
                           var schedulers: Schedulers) : ISoundCloudRepository {

    override fun getSinglePlaylist(id: String): Observable<SoundCloudPlayList> {
        return soundCloudService.getPlaylist(id).subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)
    }

    override fun getSoundCloudAccountPlaylists(id: String): Observable<List<SoundCloudPlayList>> {
        return soundCloudService.getPlayLists(id).subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)
    }

    override fun getSoundCloudAccountAudios(id: String): Observable<List<Track>> {
        return soundCloudService.getTracks(id).subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)
    }

    override fun getAllPlaylists(list: List<String>): Observable<List<SoundCloudPlayList>> {
        val requestList = mutableListOf<Observable<List<SoundCloudPlayList>>>()
        list.forEach {
            requestList.add(getSoundCloudAccountPlaylists(it))
        }

        return Observable.zip(requestList) { it ->
            val playLists = mutableListOf<SoundCloudPlayList>()

            it.forEach {
                (it as List<SoundCloudPlayList>).forEach {
                    playLists.add(it)
                }
            }

            return@zip playLists
        }.map {
            it.toList()
        }.subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)

    }

    override fun getAllAudios(list: List<String>): Observable<List<Track>> {
        val requestList = mutableListOf<Observable<List<Track>>>()
        list.forEach {
            requestList.add(getSoundCloudAccountAudios(it))
        }

        return Observable.zip(requestList) { it ->
            val tracks = mutableListOf<Track>()

            it.forEach {
                (it as List<Track>).forEach {
                    tracks.add(it)
                }
            }

            return@zip tracks
        }.map {
            it.toList()
        }.subscribeOn(schedulers.subscribeOn)
                .observeOn(schedulers.observeOn)
    }

}