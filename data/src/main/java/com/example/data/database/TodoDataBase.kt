package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.entity.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDataBase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}
