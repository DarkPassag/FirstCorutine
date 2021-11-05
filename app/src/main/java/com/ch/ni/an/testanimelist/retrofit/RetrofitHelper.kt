package com.ch.ni.an.testanimelist.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitHelper {

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getCharByName(@Body body :String) :Response<String>

}

object Common {
    private const val BASE_URL = "https://graphql.anilist.co/"
    val retrofitHelper = RetrofitApi.getClient(BASE_URL).create(RetrofitHelper::class.java)

}