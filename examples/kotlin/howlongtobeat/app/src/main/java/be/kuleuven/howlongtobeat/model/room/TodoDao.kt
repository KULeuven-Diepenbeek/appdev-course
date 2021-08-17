package be.kuleuven.howlongtobeat.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import be.kuleuven.howlongtobeat.model.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo")
    fun query(): List<Todo>

    @Update
    fun update(items: List<Todo>)

    @Query("DELETE FROM Todo")
    fun deleteAll()

    @Insert
    fun insert(items: List<Todo>)
}
