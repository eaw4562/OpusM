package com.example.opusm

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.opusm.SharedPreferencesUtil.saveSelectedAccountUsername
import com.example.opusm.databinding.ActivityMainBinding
import com.example.opusm.databinding.NavHeaderBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var popupWindow: PopupWindow
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var selectedAccountTextView: TextView
    private lateinit var navHeaderBinding: NavHeaderBinding

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

        val testButton = findViewById<AppCompatImageButton>(R.id.header_network)
        testButton.setOnClickListener {
            showNetworkDropdown(testButton)
        }

        val drawLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val headerProfile = findViewById<AppCompatImageButton>(R.id.header_profile)
        headerProfile.setOnClickListener {
            drawLayout.openDrawer(GravityCompat.END)
        }

        navHeaderBinding.headerUsername1.setOnClickListener {
            saveSelectedAccountUsername(navHeaderBinding.headerUsername1.text.toString())
            selectedAccountTextView.text = navHeaderBinding.headerUsername1.text
        }

        navHeaderBinding.headerUsername2.setOnClickListener {
            saveSelectedAccountUsername(navHeaderBinding.headerUsername2.text.toString())
            selectedAccountTextView.text = navHeaderBinding.headerUsername2.text
        }

        selectedAccountTextView = navHeaderBinding.headerNowAccount
        val selectedUsername = getSelectedAccountUsername()
        if (selectedUsername != null) {
            selectedAccountTextView.text = selectedUsername
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
    }

    private fun saveSelectedAccountUsername(username: String) {
        sharedPreferences.edit().putString("selected_account_username", username).apply()
        navHeaderBinding.headerNowAccount.text = username
    }


    private fun getSelectedAccountUsername(): String? {
        return sharedPreferences.getString("selected_account_username", null)
    }
}
