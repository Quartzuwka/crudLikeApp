package com.example.fortests.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<User>>

    @Insert
    fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Игнорировать конфликты
    suspend fun insertAll(users: List<User>)

    @Query("DELETE FROM users WHERE userId = :id")
    fun deleteUser(id:Int)

    @Query("SELECT MAX(userId) FROM users")
    suspend fun getMaxId(): Int?

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}