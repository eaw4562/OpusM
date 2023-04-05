package com.example.opusm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.opusm.dto.Account
import com.example.opusm.dto.AccountInfo
import com.example.opusm.interfac.AccountDao
import com.example.opusm.interfac.AccountInfoDao

@Database(entities = [AccountInfo::class, Account::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountInfoDao(): AccountInfoDao
    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
