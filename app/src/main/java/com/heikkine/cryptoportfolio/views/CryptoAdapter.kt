package com.heikkine.cryptoportfolio.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.heikkine.cryptoportfolio.R
import com.heikkine.cryptoportfolio.data.CryptoCurrency

class CryptoAdapter(var context : Context, var cryptoCurrencies: ArrayList<CryptoCurrency>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return cryptoCurrencies.size
    }

    override fun getItem(i: Int): Any {
        return cryptoCurrencies[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.crypto_list_row, parent, false)

        val cryptoCurrency = this.getItem(i) as CryptoCurrency

        val titleText = "${cryptoCurrency.name} (${String.format("%.4f", cryptoCurrency.rate.rate)} ${cryptoCurrency.rate.currency})"
        rowView.findViewById<TextView>(R.id.titleTextView).text = titleText

        val ownedText = "Owned: ${String.format("%.4f", cryptoCurrency.amount)} ${cryptoCurrency.name}"
        rowView.findViewById<TextView>(R.id.ownedTextView).text = ownedText

        val sum = cryptoCurrency.rate.rate * cryptoCurrency.amount
        val sumText = "Total: ${String.format("%.2f", sum)} ${cryptoCurrency.rate.currency}"
        rowView.findViewById<TextView>(R.id.sumTextView).text = sumText

        rowView.setOnClickListener { Toast.makeText(context, cryptoCurrency.name, Toast.LENGTH_SHORT).show() }

        rowView.tag = i
        return rowView
    }
}