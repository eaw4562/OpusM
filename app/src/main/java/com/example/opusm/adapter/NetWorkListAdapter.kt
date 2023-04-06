package com.example.opusm.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.opusm.R
import com.example.opusm.model.Network

class NetworkListAdapter(private val context: Context,
                         private val networks: List<Network>,
                         private val itemClickListener: (Network) -> Unit
) : ArrayAdapter<Network>(context, R.layout.list_item_network, networks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_network, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val network = networks[position]
        holder.bind(network)

        view?.setOnClickListener { itemClickListener(network) }

        return view!!
    }

    private class ViewHolder(view: View) {
        private val networkColorView: View = view.findViewById(R.id.network_color)
        private val networkNameView: TextView = view.findViewById(R.id.network_name)

        fun bind(network: Network) {
            networkColorView.setBackgroundColor(Color.parseColor(network.imageLink))
            networkNameView.text = network.networkName
        }
    }
}
