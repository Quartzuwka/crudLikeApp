package com.example.fortests


@androidx.room.Entity(tableName = "users")
class User {
    @androidx.room.PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    @androidx.room.ColumnInfo(name = "userId")
    var id: Int = 0
    @androidx.room.ColumnInfo(name = "userName")
    var name: String=""
    var age: Int = 0

    constructor() {}

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}