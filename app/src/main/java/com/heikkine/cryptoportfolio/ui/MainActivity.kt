package com.heikkine.cryptoportfolio.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.heikkine.cryptoportfolio.BuildConfig
import com.heikkine.cryptoportfolio.R
import com.heikkine.cryptoportfolio.data.CryptoCurrency
import com.heikkine.cryptoportfolio.data.Rate
import com.heikkine.cryptoportfolio.repository.CoinIoRepository
import com.heikkine.cryptoportfolio.views.CryptoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    private val coinIoRepository: CoinIoRepository = CoinIoRepository()

    var cryptoCurrencies = ArrayList<CryptoCurrency>()

    var currency : String = "EUR"
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

        loader.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            updateRates()
            launch(Dispatchers.Main) {
                cryptoListView.adapter = CryptoAdapter(this@MainActivity, cryptoCurrencies)

                var sum = 0.0
                cryptoCurrencies.forEach{ cryptoCurrency ->
                    sum += cryptoCurrency.rate.rate * cryptoCurrency.amount
                }
                val sumText = "Portfolio sum: ${String.format("%.2f", sum)} $currency"
                supportActionBar?.title = sumText

                loader.visibility = View.GONE


            }
        }
    }

    private suspend fun updateRates() {
        for (cryptoId in amountMap.keys) {
            val rate = try {
                val rateTmp = coinIoRepository.getExchangeRate(cryptoId, currency).rate
                rateTmp ?: 0.0
            } catch (e: HttpException) {
                0.0
            }

            cryptoCurrencies.find { it.name == cryptoId }?.let { cryptoCurrency ->
                Log.d("ESA", "Updating ${cryptoCurrency.name} rate to $rate")
                cryptoCurrency.rate.rate = rate
            } ?: run {
                Log.d("ESA", "Adding new $cryptoId rate to $rate")
                val amount = amountMap[cryptoId] ?: 0.0
                cryptoCurrencies.add(CryptoCurrency(cryptoId, amount, Rate(rate, currency)))
            }
        }
    }
}
