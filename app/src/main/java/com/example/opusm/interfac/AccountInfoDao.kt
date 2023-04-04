package com.example.opusm.interfac

import androidx.room.*
import com.example.opusm.dto.AccountInfo

@Dao
interface AccountInfoDao {
    @Query("SELECT * FROM account_info")
    suspend fun getAllAccountInfos(): List<AccountInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountInfo(accountInfo: AccountInfo)

    @Delete
    suspend fun deleteAccountInfo(accountInfo: AccountInfo)
}
