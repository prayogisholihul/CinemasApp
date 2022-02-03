package com.zogik.cinema.network

import com.google.gson.GsonBuilder
import com.zogik.cinema.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiNetwork {

    private const val baseUrl = "https://api.themoviedb.org/"
    private const val API_KEY = BuildConfig.API_KEY

    fun getClient(): ApiInterface {

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor {
                val request = it.request().url.newBuilder()
                    .addQueryParameter("api_key", API_KEY).build()
                it.proceed(it.request().newBuilder().url(request).build())
            }
            .build()

        val gson = GsonBuilder().serializeNulls().create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }
}
