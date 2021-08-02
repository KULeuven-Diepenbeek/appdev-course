package be.kuleuven.filemanip

import be.kuleuven.filemanip.model.Student
import java.sql.Connection
import java.sql.DriverManager

lateinit var connection: Connection

fun createDb() {
    connection = DriverManager.getConnection("jdbc:sqlite:mydb.db").apply {
        setAutoCommit(false)
    }
    val sql = Student::class.java.getResource("/create_student.sql").readText()
    connection.createStatement().use {
        println("Creating table with SQL: $sql")
        it.executeUpdate(sql)
    }
}

fun insertStudent(student: Student) {
    connection.createStatement().use {
        it.executeUpdate("INSERT INTO student(name, age) values(\"${student.name}\", ${student.age});")
    }
}

fun printAllStudents() {
    connection.createStatement().use {
        val rs = it.executeQuery("SELECT * FROM student")
        while(rs.next()) {
            println("Student: ${rs.getString("name")}, age: ${rs.getInt("age")}")
        }
    }
}

fun main(args: Array<String>) {
    createDb()
    insertStudent(Student("Jozefien", 19))

    printAllStudents()
}