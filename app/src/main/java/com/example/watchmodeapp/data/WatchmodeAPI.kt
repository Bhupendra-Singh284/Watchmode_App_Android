package com.example.watchmodeapp.data

import com.example.watchmodeapp.data.models.TitleDetails
import com.example.watchmodeapp.data.models.TitlesList
import com.example.watchmodeapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchmodeApi {
    @GET("list-titles/")
    suspend fun getTitles(
        @Query("apiKey") apiKey:String = Constants.API_KEY,
        @Query("page") page:Int=1,
        @Query("limit") limit:Int=100,
        @Query("types") type:String,
    ):TitlesList

    @GET("title/{id}/details/")
    suspend fun getTitleDetails(
        @Path("id") titleId:Int,
        @Query("apiKey") apiKey:String = Constants.API_KEY,
    ):TitleDetails
}