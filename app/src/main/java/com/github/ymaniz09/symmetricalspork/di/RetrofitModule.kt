package com.github.ymaniz09.symmetricalspork.di

import com.github.ymaniz09.symmetricalspork.Constants
import com.github.ymaniz09.symmetricalspork.api.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    single {
        retrofit(Constants.BASE_URL)
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}

private fun retrofit(baseUrl: String) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()