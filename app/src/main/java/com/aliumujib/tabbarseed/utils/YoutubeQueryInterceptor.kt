package com.aliumujib.tabbarseed.utils

import com.aliumujib.tabbarseed.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class YoutubeQueryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("part", "snippet,contentDetails")
                .addQueryParameter("maxResults", 50.toString())
                .addQueryParameter("key",BuildConfig.SOUNDCLOUD_API_KEY)
                .build()
        return chain.proceed(original.newBuilder().url(url).build())
    }

}