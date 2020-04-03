package com.github.ymaniz09.symmetricalspork.api

import com.github.ymaniz09.symmetricalspork.Constants
import com.github.ymaniz09.symmetricalspork.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val retrofitBuilder : Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())

    }

    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}