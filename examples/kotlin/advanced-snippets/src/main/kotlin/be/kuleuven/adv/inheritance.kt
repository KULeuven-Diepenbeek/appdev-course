package be.kuleuven.adv

open class Animal() {

}
interface Plays {
    fun play()
}

// without "open" in Animal's definition, this wouldn't work
// classes are FINAL by default!
// "implements" and "extends" are both replaced by a semicolon
open class Monkey(val name: String) : Animal(), Plays {

    lateinit var hobbies: String
        private set

    // all paths to all constructors MUST initialize this
    // otherwise: Exception in thread "main" kotlin.UninitializedPropertyAccessException: lateinit property hobbies has not been initialized
    // since the primary constructor doesn't do this, we'll need an "init" block for it instead.
    init {
        hobbies = "Boring myself to death with a rock or perhaps a small bush of grass"
    }

    constructor(name: String, hobbies: String) : this(name) {
        this.hobbies = hobbies
    }

    constructor(twinbrother: Monkey): this(twinbrother.name, twinbrother.hobbies)

    override fun play() {
        println("ooh ooh aah aah monkey see monkey do?")
    }

    // Note that since it's a standard Java equals, the argument can be null
    // Note that after calling "is?, the argument automagically is class-casted! Wowza! Check out the decompiled source to see the magic:
    /*
    @Override
    public boolean equals(@Nullable final Object other) {
        return other != null && other instanceof Monkey && Intrinsics.areEqual((Object)((Monkey)other).name, (Object)this.name) && Intrinsics.areEqual((Object)((Monkey)other).getHobbies(), (Object)this.getHobbies());
    }
     */
    override fun equals(other: Any?): Boolean {
        if(other == null || other !is Monkey)
            return false
        return other.name == name && other.hobbies == hobbies
    }
}

class VeryPrivateMonkey private constructor(): Monkey("I'd rather not say")

fun main(args: Array<String>) {
    val george = Monkey("George")
    val jeffrey = Monkey(george)

    // can't. there's a "private" constructor
    // Seems easier in Java, isn't it?
    // val anonymous = VeryPrivateMonkey()

    george.play()
    jeffrey.play()

    println("Are George and Jeffrey alike? " + (george == jeffrey))
}