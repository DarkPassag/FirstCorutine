package com.ch.ni.an.invest.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClientImage {
    private var retrofit :Retrofit? = null

    fun getClient(url :String) :Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create()).build()
        }
        return retrofit!!
    }
}