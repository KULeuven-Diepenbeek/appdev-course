package be.kuleuven.recyclerview.model

interface TodoRepository {

    fun load(): List<Todo>

    fun save(items: List<Todo>)
}
