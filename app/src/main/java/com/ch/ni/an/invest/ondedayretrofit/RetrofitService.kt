package com.ch.ni.an.invest.ondedayretrofit

import com.ch.ni.an.invest.model.AnimeChan
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://animechan.vercel.app/api/"

interface RetrofitService {

    @GET("quotes")
    suspend fun getRandomQuotes(): List<AnimeChan>

    @GET("random")
    suspend fun getRandomQuote(): AnimeChan

    @GET("available/anime")
    suspend fun getAvailableAnime(): List<String>

    @GET("quotes/{animeName}")
    suspend fun getQuotesByAnime(@Path("animeName" )animeName: String): List<AnimeChan>
}

object Common{

    val retrofit = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)

}