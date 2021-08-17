package be.kuleuven.howlongtobeat.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import be.kuleuven.howlongtobeat.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM Game")
    fun query(): List<Game>

    @Update
    fun update(items: List<Game>)

    @Query("DELETE FROM Game")
    fun deleteAll()

    @Insert
    fun insert(items: List<Game>)
}
