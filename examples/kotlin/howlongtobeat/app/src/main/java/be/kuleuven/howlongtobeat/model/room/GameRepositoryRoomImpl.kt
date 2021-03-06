package be.kuleuven.howlongtobeat.model.room

import android.content.Context
import androidx.room.Room
import be.kuleuven.howlongtobeat.model.Game
import be.kuleuven.howlongtobeat.model.GameRepository

class GameRepositoryRoomImpl(appContext: Context) :
    GameRepository {

    private val db: GameDatabase
    private val dao: GameDao

    init {
        db = Room.databaseBuilder(appContext, GameDatabase::class.java, "todo-db")
            .allowMainThreadQueries()
            .build()
        dao = db.todoDao()
    }

    override fun load(): List<Game> = dao.query()
    override fun update(game: Game) = dao.update(listOf(game))

    override fun find(id: Int): Game = load().single { it.id == id }

    override fun save(game: Game) {
        db.runInTransaction {
            dao.insert(listOf(game))
        }
    }

    override fun overwrite(items: List<Game>) {
        // You'll learn more about transactions in the database course in the 3rd academic year.
        db.runInTransaction {
            dao.deleteAll()
            dao.insert(items)
        }
    }

}