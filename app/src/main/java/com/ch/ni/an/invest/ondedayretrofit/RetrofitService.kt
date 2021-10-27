package com.ch.ni.an.invest.ondedayretrofit

import com.ch.ni.an.invest.model.AnimeChan
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("quotes")
    suspend fun getBasicQuotes(): List<AnimeChan>

    @GET("quotes/anime?title=naruto")
    suspend fun getQuotesByAnimeName(): List<AnimeChan>

}
object Common{
    val retrofit: RetrofitService
        get() =  RetrofitClient.getClient().create(RetrofitService::class.java)


}