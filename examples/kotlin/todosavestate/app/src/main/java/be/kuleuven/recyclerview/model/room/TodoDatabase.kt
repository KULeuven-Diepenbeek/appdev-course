package be.kuleuven.recyclerview.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import be.kuleuven.recyclerview.model.Todo

@Database(entities = arrayOf(Todo::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao
}