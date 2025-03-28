package com.example.fortests.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(User::class)], version = 3)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: UserRoomDatabase? = null
        fun getInstance(context: Context): UserRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, UserRoomDatabase::class.java, "usersdb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}