package com.example.taipeitravel.api

import com.example.taipeitravel.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var gson: Gson = GsonBuilder()
    .setLenient()
    .create()


/**
 * The Retrofit object with the Moshi converter.
 */
private val loggingInterceptor = HttpLoggingInterceptor().setLevel(
    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
)

class ApiClient {
    /**
     * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
     */

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(NetworkConnectInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .baseUrl(
            HttpUrl.Builder().scheme("https")
                .host("www.travel.taipei").build()
        )
        .build()

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
