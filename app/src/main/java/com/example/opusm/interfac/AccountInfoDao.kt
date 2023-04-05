package com.example.opusm.interfac

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.opusm.dto.Account
import com.example.opusm.dto.AccountInfo

@Dao
interface AccountInfoDao {
   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountInfo: AccountInfo)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedAccount(account: Account) {
        Log.d("AccountDao", "Inserted account: $account")
    }

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountInfoById(id: Int): AccountInfo?

    @Query("SELECT * FROM accounts")
    fun getAllAccountInfo(): LiveData<List<AccountInfo>>


}
