package com.aliumujib.tabbarseed.data.model

import com.google.gson.annotations.SerializedName


data class SearchResponse(
        @SerializedName("total_count") val totalCount: Int?,
        @SerializedName("incomplete_results") val incompleteResults: Boolean?,
        @SerializedName("items") val items: List<RepositoryEntity>?
)