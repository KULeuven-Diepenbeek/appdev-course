package be.kuleuven.howlongtobeat.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import be.kuleuven.howlongtobeat.model.Game

@Database(entities = arrayOf(Game::class), version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun todoDao() : GameDao
}