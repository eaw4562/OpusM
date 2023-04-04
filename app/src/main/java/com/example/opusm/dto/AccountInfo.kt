package com.example.opusm.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_info")
data class AccountInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val holdcoin: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val profileImage: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is AccountInfo) {
            return false
        }
        return id == other.id &&
                username == other.username &&
                holdcoin == other.holdcoin &&
                profileImage.contentEquals(other.profileImage)
    }

    override fun hashCode(): Int {
        return id.hashCode() + username.hashCode() + holdcoin.hashCode() + profileImage.contentHashCode()
    }
}
