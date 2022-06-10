package com.executor.userdatabaseapp.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fName: String,
    val lName: String,
    val dob: String,
    val number: Int,
    val createDate: Date,
)
