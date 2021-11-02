package com.ch.ni.an.invest.retrofit

import com.ch.ni.an.invest.model.AnimeChan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("quotes")
    suspend fun getBasicQuotes(): List<AnimeChan>

    @GET("quotes/anime?")
    suspend fun getQuotesByAnimeName(@Query("title") animeName: String ): Response<List<AnimeChan>>

    @GET("available/anime")
    suspend fun getAvailableAnime(): Response<List<String>>

}
object Common{
   private const val BASE_URL = "https://animechan.vercel.app/api/"
    val retrofit: RetrofitService
        get() =  RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)

}