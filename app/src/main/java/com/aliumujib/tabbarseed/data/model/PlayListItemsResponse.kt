package com.aliumujib.tabbarseed.data.model

data class PlaylistItemResponse(
    val etag: String,
    val items: List<PlayListItem>,
    val kind: String,
    val pageInfo: PageInfo
)

data class PlayListItem(
    val contentDetails: PlayListItemContentDetails,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: PlayListItemSnippet
)

data class PlayListItemContentDetails(
    val videoId: String,
    val videoPublishedAt: String
)

data class PlayListItemSnippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val resourceId: ResourceId,
    val thumbnails: Thumbnails,
    val title: String
)

data class ResourceId(
    val kind: String,
    val videoId: String
)
