package com.ch.ni.an.invest.Retrofit

import com.ch.ni.an.invest.model.AnimeChan
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("quotes")
    suspend fun getTenQuotes(): List<AnimeChan>

    companion object{
        val BASE_URL = "https://animechan.vercel.app/api/"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        fun create(): ApiInterface{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}