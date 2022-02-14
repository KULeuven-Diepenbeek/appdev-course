---
title: "1. Kotlin programming: Essentials"
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

### 2. Because Kotlin is Java 2.0

Writing Kotlin code on the JVM still means programming on the JVM---the _Java_ Virtual Machine. That is, in the end, you'll still need a firm grasp of Java if things get awry. The ability to decompile `.class` files lets you take a peek at what the Kotlin compiler does for us. See [advanced Kotlin aspects](/lang/kotlin-advanced).

Software developers are slowly integrating parts of Kotlin into existing Java architectures: Kotlin mingles well with existing Java-based solutions. The learning curve, therefore, is flattened: you can choose to gradually migrate towards the new functionalities instead of having to go all out with a big bang. 

However, since within this course you'll be creating _new_ projects, we'll opt for an all-in Kotlin solution here. In other courses such as Software Engineering Skills, you can choose to do parts in Kotlin, or everything---or nothing. We hope you've come to appreciate the beauty of this relatively new language by then. 

### 3. Because We Say So

The software department of [ACRO](https://iiw.kuleuven.be/onderzoek/acro), the KU Leuven research group at Diepenbeek Campus, focuses on functional languages, of which Kotlin certainly fits the bill. Kotlin's functional mechanics are nowhere near as complex as Prolog or Scala (it is a multi-purpose multi-paradigm language, not a pure functional one such as the aforementioned), and is familiar enough for students who are used to writing programs in Java. Thus, with relative little effort, a new (both for you and for the programming community) language can be learned. 

---

## How does Kotlin fit into the JVM?

It's actually very simple: you should treat Kotlin as a _separate language_ that compiles into the _same byte code_ class files as traditional Java `.class` files. These can be packaged into a `.jar` like any other Java app, and run using the JVM, that won't even know it started as Kotlin code:

![](/img/ktcompile.jpg "Compiling java/kotlin files. src: Kotlin in Action book")

The only block that stands out here is the **Kotlin runtime** that has to be packaged with the application, using the correct arguments when compiling command-line:

```
kotlinc hello.kt -include-runtime -d hello.jar
```

The `-include-runtime` check simply tells the Kotlin compiler to pack along Kotlin's API. That is, utility functions such as `print()`, that serve as simple wrappers for `System.out.println()`, need to be packaged in the same jar. That is, the Kotlin "runtime" (nothing _runtime_ about it) is just a _dependency_! <br/>The above schematic also reveals that interoperability between Java and Kotlin is very easy: they're both (eventually) JVM-class files! So, calling Kotlin from Java or Java from Kotlin is easy as pie.

{{% notice note %}}
The Kotlin language can also be compiled natively using the LLVM compiler, or compile into JavaScript and run on Node. We will be focusing on Kotlin for the JVM in this course. 
{{% /notice %}}

Now you're ready to get your hands dirty!

## A Crash Course in Kotlin

Roughly based upon Google's [Introduction to Kotlin crash course](https://developer.android.com/kotlin/learn).

**Play with Kotlin** to get to know the language before focusing on Android specifics. The best way to do this is creating simple Kotlin projects in Intellij (see [Kotlin examples](/lang/kotlin-examples)) or simply by browsing to https://play.kotlinlang.org/

### 1. Variables

As mentioned before, Kotlin has many functional aspects to it. For variable declaration, you can _pre-set_ a value, declaring it will never change (it's a constant), or just call it a "variable".

<div class="devselect">

```kt
// in Kotlin, you can leave out the argument in main() if you don't need it!
fun main(args: Array<String>) {
    println("Hi from Kotlin! the Main fun(ction) can be placed outside a class. Cool!")

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

```java
public class MainClass {
    public static void main(Stringp[] args) {
        System.out.println("Hi from... ugly old... Java? Great... ");

        var count1 = 10; // this works in JDK9+
        count1++;
        int count2 = 10;
        count2++;
        final var count3 = 10;
        count3++; // compile error
        final int count4 = 10;
        count4++; // compile error
    }
}
```
</div>

This works just like in JavaScript: `let` (used to be `var`) and `const`.

`val`ues cannot be changed: they're values. What else is new? No `;`---finally! Note Kotlin has built-in [type inference](https://kotlinlang.org/spec/type-inference.html): specifying `:= Int` is not needed, the compiler knows this since you provide a whole number on the right-hand side of the equation sign. 

Try to use `val` as much as possible: **make verything immutable**, unless you absolutely have to use a mutable variable. This is good practice that promotes functional-style programming and prevents making mistakes with, among others, concurrency. Note that `val person = Person()` means you cannot assign another `Person` to the `person` variable, but you can still mutate the reference itself: `person.age = 10`---unless the setter is private, that is. 

Spot the new syntax in the next section:

```kt
fun main() {
    var name: String = null // does not compile
    name = "Wouter"

    var nameGood: String? = null
    nameGood = "Wouter"

    // We can just as well use standard Java invocations.
    System.out.println("My name is " + nameGood.orEmpty())

    // this is simply a Kotlin-wrapper.
    println("My name is " + nameGood?.toLowerCase()) // without if check: use ?.
    if(nameGood != null) {
        println("My name surely is " + nameGood.toLowerCase()) // with if check: no ? after dot
    }
}
```

This is called **null safety**. To assign `null`, you explicitly have to use the question mark `?` sign. `orEmpty()` returns an empty string if the value it holds is effectively `null`. This method, or using `var?.` to access properties, omits needlessly checking with `if(...)` statements, complicating your codebase, such as the last two statements. 

{{% notice note %}}
All standard JDK API methods are still available to you. Remember that you are still working on a Java-specific Virtual Machine. Thus, `System.out.println()` is still valid (but can be shortened to `println()`, imported from package `kotlin.io`), although you have to omit the `;` at the end of the statement. So, in essence, you already know how to create threads, access files, ...! <br/>This is also very important in Android development, as many system-level Android API methods are still Java. 
{{% /notice %}}

### 2. Conditionals

`if()` is still `if()`, including the `else`. What's more interesting, however, is the possibility of replacing your if-else expressions with a `when` expression:

<div class="devselect">

```kt
val answerString = when {
    count == 42 -> "I have the answer."
    count > 35 -> "The answer is close."
    else -> "The answer eludes me."
}

println(answerString)
```

```java
// awkward! Need some kind of wrapper
private String cantAssignResultOfSwitchInAVar() {
    switch(count) {
        case 42: return "I have the answer.";
        case 41:
        case 40:
        case 39:    // wow, this is awkward! "> 35" is impossible in Java.
        case 38:
        case 37:
        case 36: return "The answer is close.";
        default: return "The answer eludes me.";
    }
    return ""; // awkward.
String answerString = cantAssignResultOfSwitchInAVar();
```
</div>

Note that in the example above, the expression seems to be missing. If `when` is used without brackets, Kotlin will check for Boolean values. `when` is very powerful in Kotlin, compared to `switch` in Java, that requires constants. You can `when` over any string/object instance/whatever:

<div class="devselect">

```kt
val color = Color(RED)
when(color) {
    Color(GREEN) -> print("its green")
    Color(RED) -> print("its red")
}
```

```java
Color color = new Color(RED);
if(color.equals(new Color(GREEN))) {
    System.out.println("its green")
} else if(color.equals(new Color(RED))) {
    System.out.println("its red")
}
```
</div>

Kotlin uses _equality checks_ behind the scenes, the only possible way to implement it in Java. Take a look at the Java implementation to uncover its details.

Note that no ternary operator exists (`val bla = d == 10 ? "jup" : "nah"`), although that can be written in a single-line using Kotlin's `if`:

{{% notice note %}}
In Kotlin, `if` and `when` constructs are **expressions**: they hold a value. In Java, they are **statements** that require code blocks. That means that both constructs can appear on the right-hand side of a variable assignment in one line. Don't let the weird syntax scare you: `var max = if(a > b) a else b`. This makes the ternary operator redundant.
{{% /notice %}}

### 3. Classes and Functions

#### Classes

Suppose we'd like to represent a pawn and its position. This is the (often painfully long) Java way to do it:

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
fun main() {
    val p = Pawn(1, 2)
   println("p is at $p")
}
```

That's a _lot_ shorter! What happened here?

1. We create a class with a constructor---on the same line. `class Pawn { ... }` would work just as well, but everything between `()` are constructor arguments. That means we can immediately use them in the assignments of the variables.
2. Properties of the class are by default `public`! So `p.y = 346` would be valid, but to countermeasure this, we set the setter to private. There are no "getter" and "setter" methods needed this way! You can still define them if you want using `get() {` below the variable name, as we did with the setter (that has no body, and thus stays the generated one).
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

The above `Pawn(theX: Int, theY: Int)` can be even further reduced! Those are primary constructor arguments, but still require the definition of fields (`var x = ...`). By adding the keyword `val` or `var` (depending on whether or not you want your field to be immutable), Kotlin does that for you:

```kt
class Pawn(var x: Int, var y: Int) {
    override fun toString(): String {
        return "($x,$y)"
    }
}
fun main() {
    val p = Pawn(1, 2)
   println("p is at $p")
}
```

With the above `var` in the `Pawn()` arguments, you can say `p.x = 23` after defining `p` in `main`---which cannot be altered when using `val`.


#### Functions

As for **defining functions**, just `fun name() {}` suffices. Functions are _public_ by default, contrary to Java's package-access! Adding `protected`/`private` before `fun` prevents this. 

- Want arguments? `fun name(arg1: Type, arg2: Type)`.
- Want default arguments? `fun name(arg1: Boolean = true)`
- Want varargs? `fun name(vararg bools: Boolean)`
- Want a return type? `fun name(): Type`. (Note that `void` is `Unit` in Kotlin)
- Want to call the function? `name(false)`
- Want to name arguments while calling? `name(arg1: false)`

{{% notice note %}}
Single-line functions, called **expression bodies**, can be simplified from `fun hi(): String { return "sup!" }` to `fun hi(): String = "sup!"`. The body is specified after the `=` symbol, and you'll see these functions often. The last statement will automatically be returned, so `return` can also be omitted. Expression bodies become very powerful when combined with `when`: `fun hi(): String = when { ... }`. <br/>
The [official Kotlin coding conventions style guide](https://kotlinlang.org/docs/coding-conventions.html#functions) recommends you use expression bodies when the body consists of a single expression (getters/setters come to mind).
{{% /notice %}}

More information about unit-returning functions [can be found here](https://kotlinlang.org/docs/functions.html#unit-returning-functions).

Sometimes, in Java, you need to quickly create a new subclass or an implementation of an interface, to be able to provide an event listener, for example. The typical syntax for that is very convoluted:

```java
window.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Good job, right on target!");
    }
    @Override
    public void ...
    // ALL methods of the interface need to be provided!
})
```

In Kotlin, an `object` is used, which is a "temporary class" that fits the bill:

```kt
window.addMouseListener(object: MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { 
        println("Good job, right on target!")
    }
})
```

Note that the `override` keyword is required here. In Java, it's just an annotation that adds to the documentation. In Kotlin, it's part of the syntax.

For more information, see https://kotlinlang.org/docs/object-declarations.html#inheriting-anonymous-objects-from-supertypes

### 4. Higher order functions

Functions can also be variables. Functions can be created ad-hoc, and be disposed of when no longer needed, as part of the local stack. For instance:

```kt
fun main() {
    var age = 30
    var adder: (Int) -> Int = { x ->
        x + 1
    }
    age = adder(age)
    println("Hello, world!!! I'm currently $age old")
}
```

Here, `adder` is again declared using an _expression body_. Try to get used to it! Functions can be declared within functions within functions within ... Its scope closes the variables around it, meaning you can shield off variables created inside functions from the outside world, but not the other way around. `age` is visible inside `adder`, but `x` is not visible inside `main`.

The above code is easily replicated in JavaScript:

```js
function main() {
    var age = 30
    var adder = function(x) {
        return x + 1
    }
    age = adder(age)
    console.log(`Hello, world!!! I'm currently ${age} old`)
}
```

But not so easily done in Java, although later JDK versions also introduced (clumsy versions of) lambda's. 

A few more things to remember:

- Inside expression bodies, `return` is missing, but the result of the last line is returned instead. 
- If a return type is missing, `kotlin.Unit` is returned, [corresponding with void in Java](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/). There's also a [Nothing type](https://play.kotlinlang.org/koans/Introduction/Nothing%20type/Task.kt) to dictate a function will always return an exception, such as an assertion.

Functions can return functions or accept functions as arguments. This is handy when recycling logic that sorts, collects, or filters collections. Speaking of...

### 5. Arrays, Collections, Looping

Kotlin's `Array<>` generic class is the same as a `[]` in Java: it's set in size. In Kotlin, arrays are simply classes: there's no distinction between low-level arrays and high-level collections. However, most of the time, we'll want to use Java's `ArrayList<>` equivalent. Kotlin also leverages interfaces to hide the collection implementation. You will see `MutableList<>` often:

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

{{% notice note %}}
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
    // option 3, the closest thing to an ugly ol' for we have
    for(x in 0..items.size) {
        print(items)
    }
}
```

The second function is the _functional_ option where we pass in a **closure** that prints an item. The only argument is automatically available as `it`. This is how the `forEach` function is defined in `_Collections.kt`:

```kt
public inline fun <T> Iterable<T>.forEach(action: (T) -> Unit): Unit {
    for (element in this) action(element)
}
```

_Hold on there!_ Where's the argument `action` in our example? We say `items.forEach {` and not `items.forEach(arg) {`. This is a special case: if the last argument is a closure, the block that is attached to the function _is_ the argument itself! This is yet another bit of syntactic sugar to ease the use of passing in functions as arguments. 

Not convinced? This piece of Java code:

```java
// wrongfully assumes "forEach" exists as a method on the collection
public interface ForEachAction {
    void act(Item i);
}
items.forEach(new ForEachAction() {
    @Override
    public void act(Item i) {
        System.out.println(i);
    }
})
```

Is the same as _each_ of the following lines in Kotlin:

```kt
// step 1: still too verbose
items.forEach({ i: Item -> println(i) })

// step 2: a lambda expression can be moved out of the parenthesis if it's the last argument
items.forEach() { i: Item -> println(i) }

// step 3: the empty parenthesis can also be removed if the lambda is the only argument
items.forEach { i: Item -> println(i) }

// step 4: parameter type inferred
items.forEach { i -> println(i) s}

// step 5: use the default parameter name "it"
items.forEach { println(it) }
```

The reason the above `ForEachAction` interface gets covered in a Kotlin lambda without having to refer to the method `act` at all is that it's an interface with a single method: a _functional interface_ or a Single Action Method (SAM) interface. The JDK and Android API are littered with these interfaces, and you already know many of those: `java.lang.Runnable`, `java.util.Comparator`, `javax.event.EventhHandler`, ... This means it's easy to replace anonymous inner functions with Kotlin lambda's when working with JDK/Android APIs. [See the kotlin docs](https://kotlinlang.org/docs/fun-interfaces.html) for more information.

Next to `forEach`, Kotlin provides The Usual Suspects of functional loop tools, such as:

- `.filter {}`
- `.reduce {}`
- `.sumOf {}`
- `.maxOf {}`
- `.removeIf {}`
- `.replaceAll {}`
- ...

To initialize arrays/lists/whatever, Kotlin provides handy utility methods so that we don't need to do silly plumbing as we're used to do in Java. For instance, in Java, creating an ArrayList and adding stuff using `new ArrayList<Bag>() { add(new Bag("apple"); add... }` is sometimes shortened using `Arrays.asList()`. In Kotlin, we simply rely on `mutableListOf(Bag("apple"))`, `arrayOf(...)`, `arrayListOf(...)`, etc. There are no new collections introduced in the language: we simply leverage Java's set. The only new things are the utility wrapper functions that come with the Kotlin runtime library.

{{% notice note %}}
Now that you know the basics of Kotlin, you can start programming in the language and adhere to the structure and style of Java development as you are used to. The more you write Kotlin, the more you will gravitate towards functional-style programming. [Advanced techniques](/lang/kotlin-advanced) shed more light on these. If you're impatient, go ahead and start your first venture into the [Android dungeons](/android)!<br/>Remember, if you're stuck, open up the [Kotlin docs](https://kotlinlang.org/docs/home.html) and refresh your memory.
{{% /notice %}}
