package com.heikkine.cryptoportfolio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heikkine.cryptoportfolio.BuildConfig
import com.heikkine.cryptoportfolio.R
import com.heikkine.cryptoportfolio.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    private val repository: Repository = Repository()

    val amountMap = mapOf(
        "BTC"  to BuildConfig.AMOUNT_BTC,
        "ETH"  to BuildConfig.AMOUNT_ETH,
        "LINK" to BuildConfig.AMOUNT_LINK,
        "BAT"  to BuildConfig.AMOUNT_BAT,
        "RVN"  to BuildConfig.AMOUNT_RVN,
        "XRP"  to BuildConfig.AMOUNT_XRP
    )

    var rateMap = mutableMapOf<String, Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.IO) {
            updateRates()
            launch(Dispatchers.Main) {
                updateUi()
            }
        }
    }

    private suspend fun updateRates() {
        for (cryptoCurrency in amountMap) {
            val rate = try {
                repository.getExchangeRate(cryptoCurrency.key).rate
            } catch (e: HttpException) {
                0.0
            }
            rateMap[cryptoCurrency.key] = rate ?: 0.0
        }
    }

    private fun updateUi() {
        var text = ""
        var sum = 0.0
        for (cryptoAmount in amountMap) {
            val cryptoId = cryptoAmount.key
            text += "${cryptoAmount.key} "
            text += "(${String.format("%.2f", cryptoAmount.value)}) "
            text += "(${String.format("%.2f", rateMap[cryptoId])}) "
            rateMap[cryptoId]?.let { rate ->
                val cryptoSum = cryptoAmount.value * rate
                text += "= ${String.format("%.2f", cryptoSum)}\n\n"
                sum += cryptoSum
            }
        }

        text += "================================\n"
        text += "Total: ${sum}"

        textView.text = text
    }
}
