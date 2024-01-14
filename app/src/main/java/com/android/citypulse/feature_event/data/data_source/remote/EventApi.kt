package com.android.citypulse.feature_event.data.data_source.remote

import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.model.EventServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventApi {

    @GET("/events")
    suspend fun getEvents(): Response<List<Event>>

    @POST("/events")
    suspend fun createEvent(@Body event: EventServer): Response<Event>

    @DELETE("/events/{id}")
    suspend fun deleteEvent(@Path("id") id: String): Response<Unit>

    @PUT("/events/{id}")
    suspend fun updateEvent(@Path("id") id: String, @Body event: EventServer): Response<Event>

    @PATCH("/events/favourites/add/{id}")
    suspend fun addToFavourites(@Path("id") id: String): Response<Event>

    @PATCH("/events/favourites/delete/{id}")
    suspend fun deleteFromFavourites(@Path("id") id: String): Response<Event>
}
