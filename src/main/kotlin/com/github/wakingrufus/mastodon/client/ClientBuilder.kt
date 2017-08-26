package com.github.wakingrufus.mastodon.client

import com.google.gson.Gson
import com.sys1yagi.mastodon4j.MastodonClient
import okhttp3.OkHttpClient

fun createAccountClient(instance: String, accessToken: String): MastodonClient {
    val httpClientBuilder = OkHttpClient.Builder().addInterceptor(OkHttpLogger())
    var builder = MastodonClient.Builder(instance, httpClientBuilder, Gson())
    builder = builder.accessToken(accessToken).useStreamingApi()
    return builder.build()
}

fun createServerClient(instance: String): MastodonClient {
    val httpClientBuilder = OkHttpClient.Builder().addInterceptor(OkHttpLogger())
    val builder = MastodonClient.Builder(instance, httpClientBuilder, Gson())
    return builder.build()
}
