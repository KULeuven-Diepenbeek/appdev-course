package be.kuleuven.howlongtobeat.model.room

import android.content.Context
import androidx.room.Room
import be.kuleuven.howlongtobeat.model.Todo
import be.kuleuven.howlongtobeat.model.TodoRepository

class TodoRoomRepository(appContext: Context) : TodoRepository {

    private val db: TodoDatabase
    private val dao: TodoDao

    init {
        db = Room.databaseBuilder(appContext, TodoDatabase::class.java, "todo-db")
            .allowMainThreadQueries()
            .build()
        dao = db.todoDao()
    }

    override fun load(): List<Todo> = dao.query()

    override fun save(items: List<Todo>) {
        // You'll learn more about transactions in the database course in the 3rd academic year.
        db.runInTransaction {
            dao.deleteAll()
            dao.insert(items)
        }
    }

}