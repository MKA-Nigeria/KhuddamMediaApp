package com.aliumujib.tabbarseed.data.model

data class YoutubeVideoResponse(
    val etag: String,
    val items: List<YoutubeVideo>,
    val kind: String,
    val pageInfo: PageInfo
)


data class YoutubeVideo(
    val contentDetails: YoutubeVideoContentDetails,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet,
    val statistics: Statistics
)

data class YoutubeVideoContentDetails(
    val caption: String,
    val definition: String,
    val dimension: String,
    val duration: String,
    val licensedContent: Boolean,
    val projection: String
)

data class Snippet(
    val categoryId: String,
    val channelId: String,
    val channelTitle: String,
    val defaultAudioLanguage: String,
    val description: String,
    val liveBroadcastContent: String,
    val localized: Localized,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String
)

data class Statistics(
    val commentCount: String,
    val dislikeCount: String,
    val favoriteCount: String,
    val likeCount: String,
    val viewCount: String
)