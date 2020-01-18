package com.heikkine.cryptoportfolio.data

data class CryptoCurrency(val name: String, val amount: Double, val rate: Rate) {
//    override operator fun equals(other: Any?) : Boolean {
//        if(other == null || other !is CryptoCurrency) {
//            return false
//        }
//        return name == other.name
//    }
}