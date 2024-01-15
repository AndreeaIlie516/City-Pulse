package com.android.citypulse.feature_event.data.network

import android.util.Log
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.model.EventServer
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.net.SocketException

data class WebSocketUpdate(
    val action: String,
    val event: EventServer
)

class WebSocketEventDataSource(
    private val networkChecker: NetworkChecker,
    url: String
) {

    private val client = OkHttpClient()
    private val request = Request.Builder().url(url).build()
    private var webSocket: WebSocket? = null

    private val eventChannel = Channel<WebSocketUpdate>(Channel.BUFFERED)

    private var reconnectJob: Job? = null
    private var isConnected = false

    init {
        connectWebSocket()
    }

    private fun connectWebSocket() {
        if (networkChecker.isNetworkAvailable()) {
            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                    Log.i("WebSocketEventDataSource", "WebSocket Connection Opened")
                    isConnected = true
                    reconnectJob?.cancel()
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    Log.i("WebSocketEventDataSource", "Received Message: $text")
                    val updateNotification = parseUpdateNotification(text)
                    val update =
                        WebSocketUpdate(updateNotification.action, updateNotification.event)
                    eventChannel.trySend(update).isSuccess
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    // TODO
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    Log.i("WebSocketEventDataSource", "WebSocket Closing: $code / $reason")
                    isConnected = false
                    eventChannel.close()
                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: okhttp3.Response?
                ) {
                    isConnected = false
                    if (t is SocketException) {
                        Log.e("WebSocket", "Connection lost. Waiting for reconnection...")
                        scheduleReconnect()
                    } else {
                        Log.e("WebSocket", "WebSocket Failure: ${t.message}")
                    }
                    eventChannel.close(t)
                }
            })
        } else {
            Log.i("WebSocketEventDataSource", "Network unavailable, not connecting to WebSocket")
        }
    }

    private fun reconnect() {
        reconnectJob?.cancel()
        reconnectJob = GlobalScope.launch {
            while (!isConnected) {
                delay(5000)
                if (!isConnected) {
                    Log.i("WebSocketEventDataSource", "Reconnecting...")
                    connectWebSocket()
                }
            }
        }
    }

    private fun scheduleReconnect() {
        var retryDelay = 1000L // 1 second
        reconnectJob?.cancel()
        reconnectJob = GlobalScope.launch {
            while (!isConnected && networkChecker.isNetworkAvailable()) {
                delay(retryDelay)
                retryDelay = (retryDelay * 2).coerceAtMost(60000) // Max 60 seconds
                if (!isConnected) {
                    Log.i("WebSocketEventDataSource", "Reconnecting...")
                    connectWebSocket()
                }
            }
        }
    }

    fun observeWebSocketUpdates(): Flow<WebSocketUpdate> {
        return eventChannel.receiveAsFlow()
    }

    fun sendWebSocketUpdate(action: String, event: Event) {
        val eventServer = EventServer(
            time = event.time,
            band = event.band,
            location = event.location,
            image_url = event.image_url,
            is_private = true,
            is_favourite = false
        )
        val update = WebSocketUpdate(action, eventServer)
        val json = Gson().toJson(update)
        webSocket?.send(json)
    }

    private fun parseUpdateNotification(jsonString: String): WebSocketUpdate {
        return Gson().fromJson(jsonString, WebSocketUpdate::class.java)
    }

    fun close() {
        webSocket?.close(1000, null)
        eventChannel.close()
    }
}