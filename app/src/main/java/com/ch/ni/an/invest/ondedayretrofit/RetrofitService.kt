package com.ch.ni.an.invest.ondedayretrofit

import com.ch.ni.an.invest.model.AnimeChan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://animechan.vercel.app/api/"

interface RetrofitService {

    @GET("quotes")
    suspend fun getRandomQuotes(): Response<List<AnimeChan>>

    @GET("random")
    suspend fun getRandomQuote(): AnimeChan

    @GET("available/anime")
    suspend fun getAvailableAnime(): Response<List<String>>

    @GET("quotes/anime?")
    suspend fun getQuotesByAnime(@Query("title" )animeName: String): List<AnimeChan>

    @GET("quotes/character?")
    suspend fun getQuotesByCharacter(@Query("name") characterName: String): Response<List<AnimeChan>>
}

object Common{

    val retrofit = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)

}