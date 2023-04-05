package com.example.opusm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opusm.dto.Account
import kotlinx.coroutines.launch


class UserViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private var selectedAccountId: Long = 0L

    fun selectAccount(account: Account){
        selectedAccountId = account.id.toLong()
        val accountInfo = Account(
            username = account.username,
            holdcoin = account.holdcoin,
            profileImage = account.profileImage
        )
        viewModelScope.launch{
            accountRepository.saveSelectedAccount(accountInfo)
        }
    }


    suspend fun getSelectedAccount(): Account? {
        val selectedAccountInfo = accountRepository.getSelectedAccount()
        return selectedAccountInfo?.let {
            Account(
                id = 0,
                username = it.username,
                holdcoin = it.holdcoin,
                profileImage = it.profileImage
            )
        }
    }

    fun loadAccounts(): LiveData<List<Account>> {
        return accountRepository.getAllAccountInfo()
    }
}
