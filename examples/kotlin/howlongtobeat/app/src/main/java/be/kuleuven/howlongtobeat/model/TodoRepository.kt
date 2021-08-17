package be.kuleuven.howlongtobeat.model

interface TodoRepository {

    fun load(): List<Todo>

    fun save(items: List<Todo>)
}
