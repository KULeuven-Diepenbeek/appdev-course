package be.kuleuven.filemanip

import be.kuleuven.filemanip.model.Student
import java.io.*


// Example taken from https://kuleuven-diepenbeek.github.io/db-course/nosql/keyvaluestores/
fun main(args: Array<String>) {
    val db = mapOf("Joske" to Student("Joske", 11)).apply {
        println(size)
    }


    val file = File("database.db")
    // handy function to auto-close streams: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/use.html
    ObjectOutputStream(FileOutputStream(file)).use {
        it.writeObject(db)  // crashes without serialization interface
    }

    println("database.db written")

    var fromFile: Map<String, Student>
    ObjectInputStream(FileInputStream("database.db")).use {
        fromFile = it.readObject() as Map<String, Student>
    }

    println("Retrieved age from Joske in DB file: " + fromFile.getValue("Joske").age)
}