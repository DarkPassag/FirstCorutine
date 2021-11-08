package com.ch.ni.an.invest.model.retrofit

import com.ch.ni.an.invest.model.AnimeChan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("random")
    fun getRandomQuote() :Response<AnimeChan>

    @GET("quotes")
    suspend fun getRandomQuotes() : Response<List<AnimeChan>>

    @GET("quotes/anime?")
    suspend fun getQuotesByAnimeName(
        @Query("title") animeName :String) :Response<List<AnimeChan>>

    @GET("available/anime")
    suspend fun getAvailableAnime() :Response<List<String>>

    @GET("quotes/character")
    suspend fun getQuotesByAnimeCharacter(
        @Query("name") animeCharacter :String) :Response<List<AnimeChan>>

}

object Common {
    private const val BASE_URL = "https://animechan.vercel.app/api/"
    val retrofit :RetrofitService
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)

}