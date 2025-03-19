package com.example.fortests

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortests.duckrepo.DuckImagesRepo
import com.example.fortests.network.KtorClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application,
    private val duckImagesRepo: DuckImagesRepo
) : ViewModel() {

    val userList: LiveData<List<User>>
    private val repository: UserRepository
    var userName by mutableStateOf("")
    var userAge by mutableStateOf(0)
    var imagesCount by mutableStateOf(0)

    fun fetchAllImagesCount(): Int {
        viewModelScope.launch {
            imagesCount = duckImagesRepo.fetchAmountOfImages()
             }
        return imagesCount
    }

    init {
        imagesCount = fetchAllImagesCount()
        val userDb = UserRoomDatabase.getInstance(application)
        val userDao = userDb.userDao()
        repository = UserRepository(userDao)
        userList = repository.userList
    }
    fun changeName(value: String){
        userName = value
    }
    fun changeAge(value: String){
        userAge = value.toIntOrNull()?:userAge
    }
    fun addUser() {
        repository.addUser(User(userName, userAge))
    }
    fun deleteUser(id: Int) {
        repository.deleteUser(id)
    }
}