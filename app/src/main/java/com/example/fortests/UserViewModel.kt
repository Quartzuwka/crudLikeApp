package com.example.fortests

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortests.db.User
import com.example.fortests.db.UserRepository
import com.example.fortests.db.UserRoomDatabase
import com.example.fortests.duckrepo.DuckImagesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application, private val duckImagesRepo: DuckImagesRepo
) : ViewModel() {

    val userList: LiveData<List<User>>
    private val repository: UserRepository
    var imagesIds by mutableStateOf(0)
    var imageString by mutableStateOf("https://random-d.uk/api/185.jpg")
        private set
    var image_сount by mutableStateOf(0)

    init {
        image_сount = fetchAllImagesCount()
        val userDb = UserRoomDatabase.getInstance(application)
        val userDao = userDb.userDao()
        repository = UserRepository(userDao)
        userList = repository.userList
        viewModelScope.launch(Dispatchers.IO) {
            imagesIds = repository.getMaxId()
        }
    }


    fun fetchAllImagesCount(): Int {
        viewModelScope.launch {
            image_сount = duckImagesRepo.fetchAmountOfImages()
        }
        return image_сount
    }

    fun addMultipleUsers(users: List<User>) {
        viewModelScope.launch {
            repository.insertAll(users)
        }
    }

    fun loadImages() {
        viewModelScope.launch {
            val images = duckImagesRepo.fetchImages()
            if (!images.isNullOrEmpty()) {
                // Создаем список пользователей
                val users = images.shuffled().take(10).mapIndexed { index, imageName ->
                    imagesIds++
                    User(
                        id = imagesIds, // Генерируем id вручную
                        name = imageName
                    )

                }

                addMultipleUsers(users)
            }
        }
    }

    fun deleteUser(id: Int) {
        imagesIds--
        repository.deleteUser(id)
    }

    fun displayImage(user: User) {
        imageString = "https://random-d.uk/api/${user.name}.jpg"
    }

    fun deleteAll() {
        imagesIds = 0
        repository.deleteAll()
    }
}