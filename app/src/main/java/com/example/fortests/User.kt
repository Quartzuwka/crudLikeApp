package com.example.fortests

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
class User {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userId")
    var id: Int = 0
    @ColumnInfo(name = "userName")
    var name: String=""


//    constructor(name: String, age: Int) : this(0, name, age)

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name

    }
}