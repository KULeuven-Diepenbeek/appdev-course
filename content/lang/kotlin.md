---
title: 1. The Kotlin programming lang.
---

## Why in Kotlin, Yet Another Language?

Good question! The answer is multi-faceted:

### 1. Because Google Says So

Google says you should [Develop Android apps with Kotlin](https://developer.android.com/kotlin/). It's their recommended way of doing so. They state:

>  Write better Android apps faster with Kotlin. Kotlin is a modern statically typed programming language used by over 60% of professional Android developers that helps boost productivity, developer satisfaction, and code safety. 

Among a slew of listed advantages, they have also created an [Android Basics in Kotlin](https://developer.android.com/courses/android-basics-kotlin/course) course, where [the basics of the Kotlin language](https://developer.android.com/courses/android-basics-kotlin/unit-1) is the first hurdle to take. 

Thanks to Google's push for Kotlin, open source Android projects on GitHub, GitLab, and friends have boomed, massively switching from Java to Kotlin. It is still possible to write Java-like code and mix in a bit of Kotlin here and there: that is one of the powers of a language on top of the JVM. It would make no sense for us to teach you Android development without touching upon Kotlin, as reading source code of bigger projects would become hard since they're littered with Kotlin-specific syntax. 

Try navigating your way through a few of the following popular open source repositories:

- https://github.com/ccomeaux/boardgamegeek4android --- a BoardGameGeek client for Android
- https://gitlab.com/AuroraOSS/AuroraStore --- A Google Playstore Client
- https://github.com/owncloud/android --- The ownCloud Android App
- https://github.com/mrcsxsiq/Kotlin-Pokedex --- Gotta Catch 'em All! Pokémon!

Simply search for "kotlin" and "android" on GitHub and you'll find yourself wading knee-deep into modern and highly-maintained app source code. 

Moreover, troubleshooting using Google/Stack Overflow usually nets you Kotlin code, as it's become that common. Yet another point in favor for learning Android app development "the recommended way". We promise it won't hurt a bit. Maybe only sting. Just a little bit. 

### 2. Because We Say So

The software department of ACRO, the KU Leuven research group at Diepenbeek Campus, focuses on functional languages, of which Kotlin certainly fits the bill. Kotlin's functional mechanics are nowhere near as complex as Prolog or Scala, and is familiar enough for students who are used to writing programs in Java. Thus, with relative little effort, a new (both for you and for the programming community) language can be learned. 

## A Crash Course in Kotlin

Roughly based upon Google's [Introduction to Kotlin crash course](https://developer.android.com/kotlin/learn).

### 1. Variables

As mentioned before, Kotlin has many functional aspects to it. For variable declaration, you can _pre-set_ a value, declaring it will never change (it's a constant), or just call it a "variable".

```kt
fun main(args: Array<String>) {
    System.out.println("Hi from Kotlin! the Main fun(ction) should be placed outside of a class. Strange, isn't it? No? Hmm.")

    var count1: Int = 10
    count1++
    var count2 := 10
    count2++
    val count3: Int = 10 
    count3++    // compile error
    val count4 = 10
    count4++    // compile error
}
```

This works just like in JavaScript: `let` (used to be `var`) and `const`.

`val`ues cannot be changed: they're values. What else is new? No `;`---finally! Note Kotlin has built-in [type inference](https://kotlinlang.org/spec/type-inference.html): specifying `:= Int` is not needed, the compiler knows this since you provide a whole number on the right-hand side of the equation sign. 

Spot the new syntax in the next section:

```kt
fun main(args: Array<String>) {
    var name: String = null // does not compile
    name = "Wouter"

    var nameGood: String? = null
    nameGood = "Wouter"

    System.out.println("My name is " + nameGood.orEmpty())

    println("My name is " + nameGood?.toLowerCase()) // without if check: use ?.
    if(nameGood != null) {
        println("My name surely is " + nameGood.toLowerCase()) // with if check: no ? after dot
    }
}
```

This is called **null safety**. To assign `null`, you explicitly have to use the question mark `?` sign. `orEmpty()` returns an empty string if the value it holds is effectively `null`. This method, or using `var?.` to access properties, omits needlessly checking with `if(...)` statements, complicating your codebase, such as the last two statements. 

{{% notice info %}}
All standard JDK API methods are still available to you. Remember that you are still working on a Java-specific Virtual Machine. Thus, `System.out.println()` is still valid (but can be shortened to `prinln()`, imported from package `kotlin.io`), although you have to omit the `;` at the end of the statement. So, in essence, you already know how to create threads, access files, ...! <br/>This is also very important in Android development, as many system-level Android API methods are still Java. 
{{% /notice %}}

### 2. Conditionals

`if()` is still `if()`, including the `else`. What's more interesting, however, is the possibility of replacing your if-else expressions with a `when` expression:

```kt
val answerString = when {
    count == 42 -> "I have the answer."
    count > 35 -> "The answer is close."
    else -> "The answer eludes me."
}

println(answerString)
```

Note that no ternary operator exists (`val bla = d == 10 ? "jup" : "nah"`).

### 3. Classes and Functions

Suppose we'd like to represent a pawn and its position. This is the Java way to do it:

```java
public class Pawn {
    private int x;
    private int y;

    public Pawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

public class Main {
    public static void main(Stringp[] args) {
        Pawn p = new Pawn(1, 2);
        System.out.println("p is at (" + p);
    }
} 
```

While this is the Kotlin way:

```kt
class Pawn(theX: Int, theY: Int) {
    var x = theX
        private set
    var y = theY

    override fun toString(): String {
        return "($x,$y)"
    }    
}
fun main(args: Array<String>) {
    val p = Pawn(1, 2)
   println("p is at $p")
}
```

That's a _lot_ shorter! What happened here?

1. We create a class with a constructor---on the same line. `class Pawn { ... }` would work just as well, but everything between `()` are constructor arguments. That means we can immediately use them in the assignments of the variables.
2. Properties of the class are by default `public`! So `p.y = 346` would be valid, but to countermeasure this, we set the setter to private. There are no "getter" and "setter" methods needed this way!
3. Remember to put the `main()` function outside any class. No separate (`static`) class needed for that. 
4. **String interpolation** exists in Kotlin. Within double quotes, you can access a variable using the `$` prefix. 

What is called a _secondary constructor_ can still be made, using the more "classic" approach:

```kt
class Pawn {
    var x = 0
    constructor(x: Int) {
        this.x = x
    }
}
```

However, this is to be avoided if only one constructor is sufficient. 

As for **defining functions**, just `fun name() {}` suffices. Functions are _public_ by default, contrary to Java's package-access! Adding `protected`/`private` before `fun` prevents this. 

- Want arguments? `fun name(arg1: Type, arg2: Type)`.
- Want default arguments? `fun name(arg1: Boolean = true)`
- Want varargs? `fun name(vararg bools: Boolean)`
- Want a return type? `fun name(): Type`. (Note that `void` is `Unit` in Kotlin)
- Want to call the function? `name(false)`
- Want to name arguments while calling? `name(arg1: false)`

More information about unit-returning functions [can be found here](https://kotlinlang.org/docs/functions.html#unit-returning-functions).

### 4. Arrays, Collections, Looping

Kotlin's `Array<>` generic class is the same as a `[]` in Java: it's set in size. Most of the time we'll want to use Java's `ArrayList<>` equivalent. Kotlin also leverages interfaces to hide the collection implementation. You will see `MutableList<>` often:

```kt
class Stuff(name: String) {
    val name = name
    override fun toString(): String {
        return name
    }
}
class Bag {
    val items: MutableList<Stuff> = mutableListOf(Stuff("pen"), Stuff("apple"))
}
```

{{% notice info %}}
Remember, as soon as you initialize an object and do not want to change it, use `val` instead of `var`. The list will grow and shrink in size as things get added and removed, but the reference to items, the list instance itself, will _not_ change. That is, `items = ArrayList<Stuff>()`, which creates a new empty list, is something we don't want to see somewhere in the code. 
{{% /notice %}}

Now that we have a bag of items, how do we print out each one? Add the following function to `Bag`:

```kt
fun rummageThrough() {
    // option 1
    for(item in items) {
        println(item)
    }
    // option 2
    items.forEach {
        println(it)
    }
}
```

The second function is the _functional_ option where we pass in a **closure** that prints an item. The only argument is automatically available as `it`. This is how the `forEach` function is defined in `_Collections.kt`:

```kt
public inline fun <T> Iterable<T>.forEach(action: (T) -> Unit): Unit {
    for (element in this) action(element)
}
```

Kotlin provides The Usual Suspects of functional loop tools, such as:

- `.filter {}`
- `.reduce {}`
- `.sumOf {}`
- `.removeIf {}`
- `.replaceAll {}`
- ...

To initialize arrays/lists/whatever, Kotlin provides handy utility methods so that we don't need to do silly plumbing as we're used to do in Java. For instance, in Java, creating an ArrayList and adding stuff using `new ArrayList<Bag>() { add(new Bag("apple"); add... }` is sometimes shortened using `Arrays.asList()`. In Kotlin, we simply rely on `mutableListOf(Bag("apple"))`, `arrayOf(...)`, `arrayListOf(...)`, etc.
