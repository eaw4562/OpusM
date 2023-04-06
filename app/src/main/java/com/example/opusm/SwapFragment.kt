package com.example.opusm

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.opusm.adapter.CoinSpinnerAdapter
import com.example.opusm.databinding.FragmentSwapBinding
import com.example.opusm.model.Coin
import com.example.opusm.model.CoinSpinnerItem
import com.example.opusm.model.TickerData
import com.example.opusm.utils.UpbitServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SwapFragment : Fragment() {

    private lateinit var binding: FragmentSwapBinding
    private lateinit var coinSpinnerAdapter: CoinSpinnerAdapter
    private lateinit var selectedCoin: CoinSpinnerItem
    private var tickerData: TickerData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSwapBinding.inflate(inflater, container, false)

        // Spinner Adapter 설정
        val coinSpinnerItemList = mutableListOf<CoinSpinnerItem>()
        coinSpinnerAdapter = CoinSpinnerAdapter(requireContext(), coinSpinnerItemList)
        binding.assetHoldcoinListView.adapter = coinSpinnerAdapter

        // Upbit API를 이용해 특정 코인 목록 가져오기
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val coinList = listOf(
                    "KRW-BTC", "KRW-ETH", "BTC-ETH", "BTC-XRP", "BTC-ETC", "BTC-OMG", "BTC-CVC"
                )
                val coinSpinnerItemList = mutableListOf<CoinSpinnerItem>()
                for (coinMarket in coinList) {
                    val coin = Coin(coinMarket, 0.0)
                    val tickerDataList = UpbitServiceBuilder.apiService.getTicker(coinMarket)
                    if (tickerDataList.isNotEmpty()) {
                        val tickerData = tickerDataList[0]
                        coinSpinnerItemList.add(CoinSpinnerItem(coin, tickerData))
                    }
                }
                withContext(Dispatchers.Main) {
                    coinSpinnerAdapter.setData(coinSpinnerItemList)
                }
                // Upbit API에서 코인 목록을 가져오는 부분에서 로그 출력
                Log.d("UpbitApi", "Coin list: $coinSpinnerItemList")
            } catch (e: Exception) {
                Log.e("SwapFragment", e.toString())
            }
        }


        // Spinner 선택 이벤트 처리
        binding.assetHoldcoinListView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCoin = coinSpinnerAdapter.getItem(position)!!
                tickerData = selectedCoin.tickerData
                calculate()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // EditText 입력 이벤트 처리
        binding.assetHoldcoin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculate()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }

    private fun calculate() {
        if (tickerData != null) {
            val holdCoinCount = binding.assetHoldcoin.text.toString().toDoubleOrNull() ?: 0.0
            val result = holdCoinCount * tickerData!!.trade_price
            binding.currentPriceTextview.text = result.toString()
        }
    }
}
