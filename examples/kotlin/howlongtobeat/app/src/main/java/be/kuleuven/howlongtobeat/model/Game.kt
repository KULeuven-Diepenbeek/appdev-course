package be.kuleuven.howlongtobeat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult

@Entity
data class Game(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "code") val cartCode: String,
    @ColumnInfo(name = "hltb") val howLongToBeat: Double,
    @ColumnInfo(name = "finished") var finished: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0) : java.io.Serializable {

    constructor(result: HowLongToBeatResult) : this(result.title, result.cartCode, result.howlong)

    val boxartFileName
        get() = "box-${cartCode}.jpg"
    val snapshotFileName
        get() = "snap-${cartCode}.jpg"

    fun finish() {
        finished = true
    }

    companion object {
        val NONE_YET = Game("No entries yet, add one!", "", 0.0)
        val GAME_ID = "GameId"
    }

}