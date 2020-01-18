package com.heikkine.cryptoportfolio.models

import com.google.gson.annotations.SerializedName

data class CoinIoExchangeRateResponse(
    @SerializedName("time")
    var time: String? = null,
    @SerializedName("asset_id_base")
    var assetIdBase: String? = null,
    @SerializedName("asset_id_quote")
    var assetIdQuote: String? = null,
    @SerializedName("rate")
    var rate: Double? = null,
    @SerializedName("intermediaries_in_the_path")
    var intermediariesInThePath: List<String>? = null
)