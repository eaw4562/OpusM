package com.example.opusm.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferencesUtil {
    private const val PREFERENCE_FILE_KEY = "com.example.myapp.prefs"
    private const val SELECTED_ACCOUNT_USERNAME_KEY = "selected_account_username"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun saveSelectedAccountUsername(context: Context, username: String?) {
        getSharedPreferences(context).edit {
            putString(SELECTED_ACCOUNT_USERNAME_KEY, username)
        }
    }

    fun getSelectedAccountUsername(context: Context): String? {
        return getSharedPreferences(context).getString(SELECTED_ACCOUNT_USERNAME_KEY, null)
    }

fun saveSelectedNetWork(context: Context, network: String?) {
        getSharedPreferences(context).edit {
            putString(SELECTED_ACCOUNT_USERNAME_KEY, network)
        }
    }

    fun getSelectedNetWork(context: Context): String? {
        return getSharedPreferences(context).getString(SELECTED_ACCOUNT_USERNAME_KEY, null)
    }
}
