package com.aliumujib.tabbarseed.data.retrofit

import com.aliumujib.tabbarseed.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class SoundCloudQueryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("limit", 200.toString())
                .addQueryParameter("client_id",BuildConfig.SOUNDCLOUD_API_KEY)
                .build()
        return chain.proceed(original.newBuilder().url(url).build())
    }

}