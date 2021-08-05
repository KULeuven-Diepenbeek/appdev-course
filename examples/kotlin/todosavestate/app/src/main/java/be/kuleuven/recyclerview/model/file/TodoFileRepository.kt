package be.kuleuven.recyclerview.model.file

import android.content.Context
import be.kuleuven.recyclerview.model.Todo
import be.kuleuven.recyclerview.model.TodoRepository
import java.io.EOFException
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class TodoFileRepository(val context: Context) : TodoRepository {

    override fun load(): List<Todo> {
        try {
            val openFileInput = context.openFileInput("todoitems.txt") ?: return Todo.defaults()
            ObjectInputStream(openFileInput).use {
                return it.readObject() as ArrayList<Todo>
            }
        } catch(fileNotFound: FileNotFoundException) {
            // no file yet, revert to defaults.
        } catch(prematureEndOfFile: EOFException) {
            // also ignore this: file incomplete/corrupt, revert to defaults.
        }
        return Todo.defaults()
    }

    override fun save(items: List<Todo>) {
        val openFileOutput = context.openFileOutput("todoitems.txt", Context.MODE_PRIVATE) ?: return
        ObjectOutputStream(openFileOutput).use {
            it.writeObject(items)
        }
    }

}