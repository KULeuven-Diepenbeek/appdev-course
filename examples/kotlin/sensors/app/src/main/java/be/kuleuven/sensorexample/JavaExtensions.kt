package be.kuleuven.sensorexample

fun String.countOccurences(ch: Char): Int =
    this.filter { it == ch }.count()