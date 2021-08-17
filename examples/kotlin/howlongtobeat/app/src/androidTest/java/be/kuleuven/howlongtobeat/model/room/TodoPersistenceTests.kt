package be.kuleuven.howlongtobeat.model.room

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import be.kuleuven.howlongtobeat.model.Todo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoPersistenceTests {

    private lateinit var db: TodoDatabase
    private lateinit var dao: TodoDao

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, TodoDatabase::class.java)
            .setQueryCallback(LogQueryCallBack(), CurrentThreadExecutor())
            .build()
        db.clearAllTables()
        dao = db.todoDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun todoItemCanBePersisted() {
        val item = Todo("brush my little pony", false)
        dao.insert(arrayListOf(item))

        val refreshedItem = dao.query().single()
        with(refreshedItem) {
            assertEquals(item.title, title)
            assertEquals(item.isDone, isDone)
            assertEquals(1, id)
        }
    }

    @Test
    fun updateUpdatesTodoPropertiesInDb() {
        var todo = Todo("git good at Hollow Knight", false)
        dao.insert(arrayListOf(todo))
        todo = dao.query().single() // refresh to get the ID, otherwise update() will update where ID = 0

        finallyFinishHollowKnight(todo)
        dao.update(arrayListOf(todo))

        val itemsFromDb = dao.query()
        assertEquals(1, itemsFromDb.size)
        with(itemsFromDb.single()) {
            assertEquals(todo.title, title)
            assertEquals(true, isDone)
        }
    }

    private fun finallyFinishHollowKnight(item: Todo) {
        println("Congrats! On to Demon Souls?")
        item.check()
    }
}