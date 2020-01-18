package com.heikkine.cryptoportfolio.repository

import com.heikkine.cryptoportfolio.BuildConfig
import com.heikkine.cryptoportfolio.data.CoinIoApi
import com.heikkine.cryptoportfolio.data.CoinIoRetrofitService

class CoinIoRepository {
    var client = CoinIoRetrofitService.createService(CoinIoApi::class.java)

    suspend fun getExchangeRate(cryptoId: String, currency : String = "EUR") =
        client.getExchangeRate(cryptoId.toUpperCase(), currency.toUpperCase(), BuildConfig.COIN_IO_API_KEY)

}