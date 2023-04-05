package com.example.opusm.interfac

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.opusm.dto.Account
import com.example.opusm.dto.AccountInfo

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedAccount(account: Account)

    @Query("SELECT * FROM account WHERE id = :id")
    suspend fun getAccountById(id: Int): Account?

    @Query("SELECT * FROM account LIMIT 1")
    suspend fun getSelectedAccount(): Account?

    @Query("SELECT * FROM accounts")
    fun getAllAccountInfo(): LiveData<List<Account>>

    @Update
    suspend fun update(account: Account)

    @Query("DELETE FROM account")
    suspend fun deleteSelectedAccount()

}
