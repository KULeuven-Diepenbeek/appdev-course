package be.kuleuven.recyclerview.model

class Items {
    fun sort(items: ArrayList<Todo>) {
        items.sortBy { it.title }
    }
}