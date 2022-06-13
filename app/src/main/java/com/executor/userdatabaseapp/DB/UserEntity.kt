package com.executor.userdatabaseapp.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val fName: String,
    val lName: String,
    val dob: String,
    val age: Int = 0,
    val number: Long,
    val createDate: Date,
)
