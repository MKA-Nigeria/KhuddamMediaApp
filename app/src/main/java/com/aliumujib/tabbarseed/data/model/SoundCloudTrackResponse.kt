package com.aliumujib.tabbarseed.data.model

import com.google.gson.annotations.SerializedName


data class SoundCloudPlayList(
    val artwork_url: String?,
    val created_at: String,
    val description: String?,
    val downloadable: Boolean,
    val duration: Long,
    val ean: Any,
    val embeddable_by: String,
    val genre: Any,
    val id: Int,
    val kind: String,
    val label_id: Any,
    val label_name: Any,
    val last_modified: String,
    val license: String,
    val permalink: String,
    val permalink_url: String,
    val playlist_type: Any,
    val purchase_title: Any,
    val purchase_url: Any,
    val release: Any,
    val release_day: Any,
    val release_month: Any,
    val release_year: Any,
    val sharing: String,
    val streamable: Boolean,
    val tag_list: String,
    val title: String,
    val track_count: Int,
    val tracks: List<Track>,
    val type: Any,
    val uri: String,
    val user: User,
    val user_id: Int
){
    val imageURL :String
    get() = artwork_url?:"n/a"
}

data class Track(
    val artwork_url: String?,
    val attachments_uri: String,
    val bpm: Any,
    val comment_count: Int,
    val commentable: Boolean,
    val created_at: String,
    val description: String,
    val download_count: Int,
    val downloadable: Boolean,
    @SerializedName("duration")
    val durationAttr: Long,
    val embeddable_by: String,
    val favoritings_count: Int,
    val genre: String,
    val id: Int,
    val isrc: Any,
    val key_signature: Any,
    val kind: String,
    val label_id: Any,
    val label_name: Any,
    val last_modified: String,
    val license: String,
    val original_content_size: Int,
    val original_format: String,
    val permalink: String,
    val permalink_url: String,
    val playback_count: Int,
    val purchase_title: Any,
    val purchase_url: Any,
    val release: Any,
    val release_day: Any,
    val release_month: Any,
    val release_year: Any,
    val sharing: String,
    val state: String,
    val stream_url: String,
    val streamable: Boolean,
    val tag_list: String,
    @SerializedName("title")
    val titleAttr: String,
    val track_type: Any,
    val uri: String,
    val user: User,
    val user_id: Int,
    val video_url: Any,
    val waveform_url: String
):IPlayable{
    override val title: String
        get() = titleAttr
    override val duration: String
        get() = durationAttr.toString()
    override val date: String
        get() = created_at
    override val hasDurationData: Boolean
        get() = true

}

data class User(
    val avatar_url: String,
    val id: Int,
    val kind: String,
    val last_modified: String,
    val permalink: String,
    val permalink_url: String,
    val uri: String,
    val username: String
)