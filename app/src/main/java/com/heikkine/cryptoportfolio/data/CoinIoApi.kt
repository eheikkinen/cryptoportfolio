package com.heikkine.cryptoportfolio.data

import com.heikkine.cryptoportfolio.models.ExchangeRateResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinIoApi {

    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )
    @GET("/v1/exchangerate/{cryptoid}/{currency}")
    suspend fun getExchangeRate(@Path("cryptoid") cryptoId: String,
                                @Path("currency") currency: String,
                                @Query("apikey") apiKey : String) : ExchangeRateResponse

}