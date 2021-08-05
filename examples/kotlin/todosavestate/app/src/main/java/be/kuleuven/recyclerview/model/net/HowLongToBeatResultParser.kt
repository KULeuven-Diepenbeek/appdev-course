package be.kuleuven.recyclerview.model.net

import be.kuleuven.recyclerview.model.Todo

object HowLongToBeatResultParser {
    fun parse(html: String): List<Todo> {
        // HTML looks like this: <a class="text_white" title="{what we want to grab}" href="...">{what we want to grab}</a>
        return """<a aria-label="(.+)" title=""".toRegex().findAll(html).map {
            val (title) = it.destructured
            Todo(title, false)
        }.toList()
    }
}