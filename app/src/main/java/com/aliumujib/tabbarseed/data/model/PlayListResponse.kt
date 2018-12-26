package com.aliumujib.tabbarseed.data.model

import com.google.gson.annotations.SerializedName

data class PlaylistListResponse(
        val etag: String,
        val items: List<YoutubePlayList>,
        val kind: String,
        val nextPageToken: String,
        val pageInfo: PageInfo
)

data class YoutubePlayList(
        val contentDetails: PlaylistContentDetails,
        val etag: String,
        val id: String,
        val kind: String,
        val snippet: PlayListSnippet
)

data class PlayListSnippet(
        val channelId: String,
        val channelTitle: String,
        val description: String,
        val localized: Localized,
        val publishedAt: String,
        val thumbnails: Thumbnails,
        val title: String
)

data class Thumbnails(
        @SerializedName("default")
        val defaultThumb: Thumbnail,
        val high: Thumbnail,
        val maxres: Thumbnail,
        val medium: Thumbnail,
        val standard: Thumbnail
)

data class Thumbnail(
        val height: Int,
        val url: String,
        val width: Int
)

data class Localized(
        val description: String,
        val title: String
)

data class PlaylistContentDetails(
        val itemCount: Int
)

data class PageInfo(
        val resultsPerPage: Int,
        val totalResults: Int
)