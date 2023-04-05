package com.example.opusm

import androidx.lifecycle.LiveData
import com.example.opusm.dto.Account
import com.example.opusm.dto.AccountInfo
import com.example.opusm.interfac.AccountDao

class AccountRepository(private val accountDao: AccountDao) {

    suspend fun saveSelectedAccount(account: Account) {
        accountDao.insertSelectedAccount(account)
    }

    suspend fun deleteSelectedAccount() {
        accountDao.deleteSelectedAccount()
    }

    suspend fun getSelectedAccount(): Account? {
        return accountDao.getSelectedAccount()
    }

    fun getAllAccountInfo(): LiveData<List<Account>> {
        return accountDao.getAllAccountInfo()
    }
}
