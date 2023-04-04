package com.example.opusm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.opusm.dto.AccountInfo
import com.example.opusm.interfac.AccountInfoDao

@Database(entities = [AccountInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountInfoDao(): AccountInfoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
