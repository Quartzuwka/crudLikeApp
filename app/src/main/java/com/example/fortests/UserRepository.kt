package com.example.fortests

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val userList: LiveData<List<User>> = userDao.getUsers()

    fun addUser(User: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.addUser(User)
        }
    }

    fun deleteUser(id:Int) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.deleteUser(id)
        }
    }
}