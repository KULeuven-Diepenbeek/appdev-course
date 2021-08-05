package be.kuleuven.recyclerview.model.room

import androidx.room.RoomDatabase
import java.util.concurrent.Executor

// If we use something like Executors.newSingleThreadExecutor(), it's still happening in another thread
// By then, our unit test is done and the output won't be shown.
class CurrentThreadExecutor : Executor {
    override fun execute(runnable: Runnable?) {
        runnable?.run()
    }
}

class LogQueryCallBack : RoomDatabase.QueryCallback {
    override fun onQuery(sqlQuery: String, bindArgs: MutableList<Any>) {
        when (bindArgs.any()) {
            true -> println("SQL: $sqlQuery, args: $bindArgs")
            false -> println("SQL: $sqlQuery")
        }
    }
}
