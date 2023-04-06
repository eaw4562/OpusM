package com.example.opusm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.opusm.R
import com.example.opusm.model.Coin
import android.content.Context
import android.util.Log
import android.widget.BaseAdapter
import com.example.opusm.model.CoinSpinnerItem
class CoinSpinnerAdapter(private val context: Context, private var coinList: MutableList<CoinSpinnerItem>) :
    ArrayAdapter<CoinSpinnerItem>(context, 0, coinList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.coin_spinner_list, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val coinName = coinList[position].coin.market
        holder.coinNameTextView.text = coinName
        return view!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.coin_spinner_list, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val coinName = coinList[position].coin.market
        holder.coinNameTextView.text = coinName
        return view!!
    }

    private class ViewHolder(view: View) {
        val coinNameTextView: TextView = view.findViewById(R.id.item_coin_name)
    }

    fun setData(data: List<CoinSpinnerItem>) {
        coinList.clear()
        for (item in data) {
            coinList.add(item)
            Log.d("Item","ADD")
        }
        notifyDataSetChanged()
    }
}
