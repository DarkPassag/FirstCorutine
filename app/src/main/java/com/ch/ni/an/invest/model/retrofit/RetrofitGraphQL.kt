package com.ch.ni.an.invest.model.retrofit

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitGraphQL {

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getCharByName(@Body body :String) : String

}

object CommonGraphQL {
    private const val BASE_URL = "https://graphql.anilist.co/"

    val dataAnimeList = RetrofitClientImage.getClient(BASE_URL).create(RetrofitGraphQL::class.java)
}