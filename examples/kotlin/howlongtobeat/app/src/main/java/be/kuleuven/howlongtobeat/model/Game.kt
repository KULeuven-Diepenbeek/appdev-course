package be.kuleuven.howlongtobeat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0) : java.io.Serializable {

    fun check() {
        isDone = true
    }

    companion object {
        val NONE_YET = Game("No entries yet, add one!")
    }

}