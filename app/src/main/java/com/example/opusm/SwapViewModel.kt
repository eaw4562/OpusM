package com.example.opusm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opusm.model.Coin

class SwapViewModel : ViewModel() {

    private val _coinList = MutableLiveData<List<Coin>>()
    val coinList: LiveData<List<Coin>>
        get() = _coinList

    private val _selectedCoin = MutableLiveData<Coin>()
    val selectedCoin: LiveData<Coin>
        get() = _selectedCoin

    private val _currentPrice = MutableLiveData<Double>()
    val currentPrice: LiveData<Double>
        get() = _currentPrice

    fun setCoinList(list: List<Coin>) {
        _coinList.value = list
    }

    fun setSelectedCoin(coin: Coin) {
        _selectedCoin.value = coin
    }

    fun setCurrentPrice(price: Double) {
        _currentPrice.value = price
    }

}
