package com.executor.userdatabaseapp.DB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertUser(user: UserEntity)

    @Update
     fun updateUser(user: UserEntity)

    @Delete
     fun deleteUser(user: UserEntity)

    @Query("select * from user_table")
    fun getAllUser(): LiveData<List<UserEntity>>
}
