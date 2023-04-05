package com.example.opusm.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey
    var id: Int = 0,

    var username: String,

    var holdcoin: Double,

    @ColumnInfo(name = "profile_image")
    var profileImage: String
)
