package be.kuleuven.howlongtobeat.model

import android.content.Context
import be.kuleuven.howlongtobeat.model.room.GameRepositoryRoomImpl

interface GameRepository {

    companion object {
        /**
         * This makes it easier to switch between implementations if needed and does not expose the RoomImpl
         * Dependency Injection is the better alternative.
         */
        fun defaultImpl(appContext: Context): GameRepository = GameRepositoryRoomImpl(appContext)
    }

    fun load(): List<Game>

    fun update(game: Game)

    fun find(id: Int): Game

    fun save(game: Game)

    fun overwrite(items: List<Game>)
}
