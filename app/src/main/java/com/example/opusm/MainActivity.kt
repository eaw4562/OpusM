package com.example.opusm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.get
import com.example.opusm.databinding.NavHeaderBinding
import com.example.opusm.dto.Account
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import org.koin.androidx.scope.lifecycleScope

class MainActivity : AppCompatActivity() {

    private lateinit var popupWindow: PopupWindow
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountDao = AppDatabase.getDatabase(this).accountDao()
        val accountRepository = AccountRepository(accountDao)
        val userViewModelFactory = UserViewModelFactory(accountRepository)
        userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]

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

        val navView = findViewById<NavigationView>(R.id.navigation_view)
        val binding = NavHeaderBinding.bind(navView.getHeaderView(0))
        val lifecycleScope = this.lifecycle.coroutineScope

        binding.headerAccount1.setOnClickListener {
            userViewModel.loadAccounts().observe(this) { accountList ->
                val selectedAccount = accountList.getOrNull(0)
                selectedAccount?.let {
                    userViewModel.selectAccount(it)
                    binding.headerNowAccount.text = it.username
                    popupWindow.dismiss()
                }
            }
        }

        binding.headerAccount2.setOnClickListener {
            userViewModel.loadAccounts().observe(this) { accountList ->
                val selectedAccount = accountList.getOrNull(1)
                selectedAccount?.let {
                    userViewModel.selectAccount(it)
                    binding.headerNowAccount.text = it.username
                    popupWindow.dismiss()
                }
            }
        }




        val selectedAccount = runBlocking {
            userViewModel.getSelectedAccount()
        }
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
