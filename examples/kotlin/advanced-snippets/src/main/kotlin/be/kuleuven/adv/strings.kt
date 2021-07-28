package be.kuleuven.adv

import be.kuleuven.adv.rappers.Rapper

// this is an expression body that appends a funny string
// it's also an EXTENSION method that extends the default string library. This is the receiver object, String is the receiver type
fun String.snoopDoggize(): String = this + ", dawg!"

// We can also extend Java classes
fun Rapper.rap(): String = name + ", yoyo"

fun main(args: Array<String>) {
    val greeting = "sup"

    // we access a Java class here
    val snoop = Rapper("Snoop Dogg")

    // can be executed on any string
    println(greeting.snoopDoggize())
    println(snoop.name.snoopDoggize() + " rappin': " + snoop.rap())
}

// this just gets converted into a static method with an argument in Java:
/*
    @NotNull
    public static final String snoopDoggize(@NotNull final String $this$snoopDoggize) {
        Intrinsics.checkNotNullParameter((Object)$this$snoopDoggize, "<this>");
        return Intrinsics.stringPlus($this$snoopDoggize, (Object)", dawg!");
    }
 */