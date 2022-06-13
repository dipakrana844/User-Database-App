package com.executor.userdatabaseapp.DB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val getAllUser: LiveData<List<UserEntity>>

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        getAllUser = repository.getAllUser
    }

    fun insertUser(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    fun updateUser(user: UserEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }
}
