package com.executor.userdatabaseapp.DB

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: ByteArray,
    val fName: String,
    val lName: String,
    val dob: String,
    val number: Int,
    val createDate: Date,
)
