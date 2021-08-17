package be.kuleuven.howlongtobeat.model

interface GameRepository {

    fun load(): List<Game>

    fun save(items: List<Game>)
}
