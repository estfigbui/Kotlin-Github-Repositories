package com.example.android_bankuish_challenge

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Test

import org.junit.Assert.*

/**
 * [MockWebServer] will allow us to simulate a web server, which is going to intercept our request and respond with local data.
 * We do this so we don´t depend on network performance to test our API.
 *
 */
open class BaseTest {
    val mockWebServer = MockWebServer()

    fun enqueue(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val buffer = inputStream.source().buffer()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(buffer.readString(Charsets.UTF_8))
        )
    }
}