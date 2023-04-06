package com.example.opusm

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.opusm.adapter.NetworkListAdapter
import com.example.opusm.databinding.ActivityMainBinding
import com.example.opusm.databinding.NavHeaderBinding
import com.example.opusm.datasource.FakeDataSource
import com.example.opusm.model.Network
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var popupWindow: PopupWindow
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var selectedNetworkTextView: TextView
    private lateinit var selectedAccountTextView : TextView
    private lateinit var navHeaderBinding: NavHeaderBinding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHeaderBinding = NavHeaderBinding.bind(binding.navigationView.getHeaderView(0))

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, MainFragment())
        transaction.commit()

        //네트워크 버튼 클릭이벤트(팝업창)
        val networkbtn = findViewById<AppCompatButton>(R.id.header_network)
        networkbtn.setOnClickListener {
            showNetworkDropdown(networkbtn)
        }

        //사용자 버튼 클릭이벤트(드로어)
        val drawLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val headerProfile = findViewById<AppCompatImageButton>(R.id.header_profile)
        headerProfile.setOnClickListener {
            drawLayout.openDrawer(GravityCompat.END)
        }

        //LinearLayout 클릭 이벤트 -> SharePreference -> 로컬저장
        navHeaderBinding.headerAccount1.setOnClickListener {
            saveSelectedAccountUsername(navHeaderBinding.headerUsername1.text.toString())
            navHeaderBinding.headerNowAccount.text = navHeaderBinding.headerUsername1.text
        }

        navHeaderBinding.headerAccount2.setOnClickListener {
            saveSelectedAccountUsername(navHeaderBinding.headerUsername2.text.toString())
            navHeaderBinding.headerNowAccount.text = navHeaderBinding.headerUsername2.text
        }

        selectedAccountTextView = navHeaderBinding.headerNowAccount
        val selectedUsername = getSelectedAccountUsername()
        if (selectedUsername != null) {
            selectedAccountTextView.text = selectedUsername
        }



    selectedNetworkTextView = binding.headerNetwork
        val selectedNetwork = getSelectedNetwork()
        if (selectedNetwork != null) {
            selectedNetworkTextView.text = selectedNetwork.networkName
        }
    }

    private fun showNetworkDropdown(anchorView: View) {
        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = layoutInflater.inflate(R.layout.network_dropdown_layout, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        popupWindow.isFocusable = true

        val location = IntArray(2)
        anchorView.getLocationInWindow(location)
        popupWindow.showAtLocation(
            anchorView,
            Gravity.NO_GRAVITY,
            0,
            location[1] + anchorView.height
        )

        val networkListView = popupView.findViewById<ListView>(R.id.network_list)
        val networks = FakeDataSource.listOfNetwork

        val adapter = NetworkListAdapter(this, networks) { network ->
            saveSelectedNetwork(network)
            selectedNetworkTextView.text = network.networkName
            popupWindow.dismiss()
        }

        networkListView.adapter = adapter
    }

    private fun saveSelectedNetwork(network: Network) {
        val gson = Gson()
        val networkJson = gson.toJson(network)
        sharedPreferences.edit().putString("selected_network", networkJson).apply()
    }

    private fun getSelectedNetwork(): Network? {
        val gson = Gson()
        val networkJson = sharedPreferences.getString("selected_network", null)
        return gson.fromJson(networkJson, Network::class.java)
    }


    private fun saveSelectedAccountUsername(username: String) {
        sharedPreferences.edit().putString("selected_account_username", username).apply()
        navHeaderBinding.headerNowAccount.text = username
    }


    private fun getSelectedAccountUsername(): String? {
        return sharedPreferences.getString("selected_account_username", null)
    }
}
