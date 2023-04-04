package com.example.opusm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.opusm.R
import com.example.opusm.dto.AccountInfo

class AccountInfoAdapter(private val context: Context, private val accountInfoList: List<AccountInfo>) : BaseAdapter() {

    override fun getCount(): Int {
        return accountInfoList.size
    }

    override fun getItem(position: Int): Any {
        return accountInfoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_account, null)
        }

        val accountInfo = accountInfoList[position]
        val usernameTextView = view?.findViewById<TextView>(R.id.header_username)
        val holdcoinTextView = view?.findViewById<TextView>(R.id.header_holdcoin)
        val profileImageView = view?.findViewById<ImageView>(R.id.header_myimage)

        usernameTextView?.text = accountInfo.username
        holdcoinTextView?.text = accountInfo.holdcoin
        profileImageView?.setImageResource(accountInfo.profileImage)

        return view!!
    }
}