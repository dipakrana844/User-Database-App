package com.executor.userdatabaseapp.DB

import androidx.lifecycle.LiveData

class UserRepository(private val userDAO: UserDAO) {

    val getAllUser: LiveData<List<UserEntity>> = userDAO.getAllUser()

    suspend fun insertUser(user: UserEntity) {
        userDAO.insertUser(user)
    }

    suspend fun updateUser(user: UserEntity) {
        userDAO.updateUser(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDAO.deleteUser(user)
    }


}