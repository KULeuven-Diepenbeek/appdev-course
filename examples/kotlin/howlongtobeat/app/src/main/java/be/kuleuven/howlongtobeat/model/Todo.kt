package be.kuleuven.howlongtobeat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

// In practice, you should NOT mix seralizable and entity
// This is just an example to show you both Room and ObjectOutputStream's implementations.
@Serializable
@Entity
data class Todo(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean,
    @PrimaryKey(autoGenerate = true) var id: Int = 0) : java.io.Serializable {

    fun check() {
        isDone = true
    }

    companion object {
        fun defaults(): List<Todo> = arrayListOf(
            Todo("Get graded", false),
            Todo("Pay attention", true),
            Todo("Get good at Android dev", false),
            Todo("Refactor Java projects", false)
        )
    }

}