package com.heikkine.cryptoportfolio.data

import com.heikkine.cryptoportfolio.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinIoRetrofitService {

    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    var client : OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

    private var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.COIN_IO_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}