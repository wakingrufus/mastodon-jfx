package com.github.wakingrufus.mastodon.client

import com.google.gson.Gson
import com.sys1yagi.mastodon4j.MastodonClient
import mu.KLogging
import okhttp3.OkHttpClient

class ClientBuilder() {
    companion object : KLogging()

    fun createAccountClient(instance: String, accessToken: String): MastodonClient {
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(OkHttpLogger())
        var builder = MastodonClient.Builder(instance, httpClientBuilder, Gson())
        builder = builder.accessToken(accessToken)
        return builder.build()
    }

    fun createServerClient(instance: String): MastodonClient {
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(OkHttpLogger())
        val builder = MastodonClient.Builder(instance, httpClientBuilder, Gson())
        return builder.build()
    }
}