package com.ch.ni.an.invest.ondedayretrofit

import com.ch.ni.an.invest.model.AnimeChan
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface RetrofitService2{

    @GET("quotes")
    suspend fun getSomeQuites(): List<AnimeChan>

    @GET("anime?title={animeName}")
    suspend fun getQuotesByAnime(@Path("animeName") animeName: String): List<AnimeChan>


    companion object {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val URL = "https://animechan.vercel.app/api/"

        fun create(): RetrofitService2 {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(RetrofitService2::class.java)
            return retrofit
        }

    }

}


interface AnimeApiRetrofit{

    @GET("url")
    suspend fun getSomething(@Path("url") url: String): Any

    @GET("url/{id}")
    suspend fun getSomethingById(@Path("id") id: String): Any


    companion object {
        private val URL = "https://en.wikipedia.org/w/api.php?"
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        fun create(): AnimeApiRetrofit{
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            return retrofit.create(AnimeApiRetrofit::class.java)
        }
    }
}