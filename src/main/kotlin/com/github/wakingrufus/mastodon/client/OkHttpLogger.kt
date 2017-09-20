package com.github.wakingrufus.mastodon.client

import mu.KLogging
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OkHttpLogger : Interceptor {
    companion object : KLogging()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        logger.debug(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()))

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        logger.trace(String.format("Received " + response.code() + " response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()))

        return response
    }
}