package com.example.opusm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ListView
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.opusm.dto.AccountInfo
import com.google.android.material.navigation.NavigationView
import com.example.opusm.adapter.AccountInfoAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var popupWindow: PopupWindow
    private val accountList = mutableListOf<AccountInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 프래그먼트 매니저를 통해 프래그먼트 트랜잭션 시작
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, MainFragment())
        transaction.commit()

        val testButton = findViewById<AppCompatImageButton>(R.id.header_network)
        testButton.setOnClickListener {
            showNetworkDropdown(testButton)
        }

        //네비게이션 드로어
        val drawLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val headerProfile = findViewById<AppCompatImageButton>(R.id.header_profile)

        headerProfile.setOnClickListener {
            drawLayout.openDrawer(GravityCompat.END)
        }

        // 네비게이션 리스트뷰 초기화
        val headerView = findViewById<NavigationView>(R.id.navigation_view).getHeaderView(0)
        val listView = headerView.findViewById<ListView>(R.id.account_list_view)
        val accountList = mutableListOf<AccountInfo>(
            AccountInfo("계정1", "100 ETH", R.drawable.logo),
            AccountInfo("계정2", "50 ETH", R.drawable.download),
            AccountInfo("계정3", "20 ETH", R.drawable.wallet)
        )
        val adapter = AccountInfoAdapter(this, accountList)
        listView.adapter = adapter
    }



    private fun showNetworkDropdown(anchorView: View) {
        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = layoutInflater.inflate(R.layout.network_dropdown_layout, null)

        popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        popupWindow.isFocusable = true

        val location = IntArray(2)
        anchorView.getLocationInWindow(location)
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, 0, location[1] + anchorView.height)
    }

}
