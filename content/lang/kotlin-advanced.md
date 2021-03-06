---
title: "2. Kotlin programming: Advanced"
---

## A closer look at Java/Kotlin interop

As seen in the [kotlin basics](/lang/kotlin), both languages compile to `.class` files that the JVM can understand. And since class files can be decompiled by the likes of [procyon](https://github.com/mstrobel/procyon) (or just **within the IntelliJ IDE**: Tools--Kotlin--Show Kotlin Bytecode), let's see what happens when we do that to a bit of Kotlin code, just to deepen our understanding of how Kotlin works in relation to Java. 

Suppose we build a `joinToString` function to print out a string representation of a collection with separators. Copy the following to a single file called `collections.kt`:

```kt
fun <T> joinToString(collection: Collection<T>, separator: String = ":", prefix: String = "[", suffix: String = "]"): String {
    val builder = StringBuilder(prefix)
    for((index, element) in collection.withIndex()) {
        if(index > 0) builder.append(separator)
        builder.append(element)
    }
    builder.append(suffix)
    return builder.toString()
}

data class Person(val name: String, val age: Int)

fun main() {
    val someCollection = mapOf("Jos" to Person("Jos", 20), "Lowie" to Person("Lowie", 56))

    println(joinToString(someCollection.values))
    println(joinToString(someCollection.values, ", ", prefix = "(", suffix = ")"))
}
```

A few things that would be impossible to do in Java;

1. Every class should be inside the same-named file. That is, the `Person` "data" (does not exist in Java) class should reside in a separate file. 
2. Top-level functions do not exist and should be placed in static classes. 
3. Default arguments do not exist. Java code is usually heavily overloaded, with a lot of duplication as a consequence. 
4. Non-nullable references do not exist in Java. We did not use `?`, so the arguments to `joinToString` cannot be null. 
5. The easy-access index/value for loop and `withIndex()` does not exist in Java.
6. Creating tuples using [an **infix** notation](https://kotlinlang.org/docs/functions.html#infix-notation): `"Jos" to Person()`. 

{{% notice note %}}
Create your own infix notation functions by prepending functions with `infix`. <br/>For example, an expressive way to add two numbers could be `infix fun Int.plus(other: Int): Int = this + other`. That way, you can write `2 plus 3` instead of `2.plus(3)`! See the [kotlin docs](https://kotlinlang.org/docs/functions.html#infix-notation) for more details. 
{{% /notice %}}


When building using IntelliJ, the output consists of two files in `build/classes`, perhaps as expected: 

- `CollectionsKt.class`
- `Person.class`

Let's decompile the collections file using `java -jar procyon-decompiler.jar path/to/CollectionsKt.class` (or just use the IntelliJ menu action, see above). The output is the following Java code:

```java
//
// Decompiled by Procyon v0.6-prerelease
//

package be.kuleuven.adv;

import java.util.Map;
import kotlin.collections.MapsKt;
import kotlin.TuplesKt;
import kotlin.Pair;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import kotlin.Metadata;

@Metadata(mv = { 1, 5, 1 }, k = 2, xi = 48, d1 = { "\u0000 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a8\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\u0001\u001a\u0019\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b??\u0006\u0002\u0010\f??\u0006\r" }, d2 = { "joinToString", "", "T", "collection", "", "separator", "prefix", "suffix", "main", "", "args", "", "([Ljava/lang/String;)V", "advanced-snippets" })
public final class CollectionsKt
{
    @NotNull
    public static final <T> String joinToString(@NotNull final Collection<? extends T> collection, @NotNull final String separator, @NotNull final String prefix, @NotNull final String suffix) {
        Intrinsics.checkNotNullParameter((Object)collection, "collection");
        Intrinsics.checkNotNullParameter((Object)separator, "separator");
        Intrinsics.checkNotNullParameter((Object)prefix, "prefix");
        Intrinsics.checkNotNullParameter((Object)suffix, "suffix");
        final StringBuilder builder = new StringBuilder(prefix);
        final Iterator<? extends T> iterator = collection.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final int index = n;
            ++n;
            final Object element = iterator.next();
            if (index > 0) {
                builder.append(separator);
            }
            builder.append(element);
        }
        builder.append(suffix);
        final String string = builder.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, "builder.toString()");
        return string;
    }

    public static /* synthetic */ String joinToString$default(final Collection collection, String separator, String prefix, String suffix, final int n, final Object o) {
        if ((n & 0x2) != 0x0) {
            separator = ":";
        }
        if ((n & 0x4) != 0x0) {
            prefix = "[";
        }
        if ((n & 0x8) != 0x0) {
            suffix = "]";
        }
        return joinToString((Collection<?>)collection, separator, prefix, suffix);
    }

    public static final void main(@NotNull final String[] args) {
        Intrinsics.checkNotNullParameter((Object)args, "args");
        final Map someCollection = MapsKt.mapOf(new Pair[] { TuplesKt.to((Object)"Jos", (Object)new Person("Jos", 20)), TuplesKt.to((Object)"Lowie", (Object)new Person("Lowie", 56)) });
        System.out.println((Object)joinToString$default(someCollection.values(), null, null, null, 14, null));
        System.out.println((Object)joinToString(someCollection.values(), ", ", "(", ")"));
    }
}
```

What's interesting here?

1. Kotlin generated a class named `CollectionsKt` to match the filename. 
2. All our top-level functions are converted to `public static final` methods.
3. `checkNotNullParameter()` is sprinkled around everywhere---even just before returning values. 
4. The fancy for loop has been replaced by a not-so-fancy `while(iterator.hasNext())`: plain old (ugly) Java code.
5. a second `$default` method has been generated because we call `joinToString()` two times using different arguments: once with no defaults provided, once with all provided.
6. `Array<String>` in our main method is indeed a `String[]` classic Java array. 
7. Default values are filled in if arguments are empty with generated `if{}` checks.
8. `to` turns out to be a method in `TuplesKt`, not a construct of the language! 
9. Semicolons are back!

To conclude, we can assume that the Kotlin compiler always spews out fully Java-complaint code in such a way that our classic Java projects seamlessly integrate with the more modern language. The only problem is the `import kotlin.` statements, where the kotlin runtime jar is required to be in the classpath. 

{{% notice note %}}
If you do not understand the syntax of Kotlin at one point. **decompile the class file**. Try to compile and decompile some of the [provided advanced snippets](https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples/kotlin/advanced-snippets) in the course repository and you'll gain a much better understanding of the inner workings of both Kotlin and Java. We highly recommend you try to do this at least once!
{{% /notice %}}

## A class inheritance example

Since `class` files are `final` by default, you'll need to add the `open` keyword to classes you'd like to extend from. 

### Subclassing, getters/setters, equality checks

Below is another elaborated example that demonstrates some of Kotlin's inheritance quirks:

```kt
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

fun main() {
    val george = Monkey("George")
    val jeffrey = Monkey(george)

    // can't. there's a "private" constructor
    // Seems easier in Java, isn't it?
    // val anonymous = VeryPrivateMonkey()

    george.play()
    jeffrey.play()

    println("Are George and Jeffrey alike? " + (george == jeffrey))
}
```

The above code demonstrates the following concepts:

- Access modifiers, and custom ones in a setter
- primary and secondary constructors
- init blocks and late binding on properties
- the obligatory explicit usage of the keyword `override`
- how to implement a basic `equals` method the Kotlin way
- how to use that equality check: with `==` instead of `.equals()`. The identity/reference check uses three `=` signs: `===`, as in JavaScript.

### Interfaces, default implementations, properties

Suppose we want to oblige the implementation of a getter of a property, but we're building an interface, not a class. In Kotlin, you can add properties to interfaces: these will generate `getProperty()` function definitions one has to override:

```kt
package be.kuleuven.adv

// note that these implementations generate four different class files: NameProvider, NickNameProvider, PoshNameProvider, and [FilenameKt]
// see build/classes/kotlin

// interfaces can hold properties. There are just getter method definitions
// interfaces can hold default implementations. (JDK 8+, this is also legal in Java)
interface NameProvider {
    val name: String
    val email: String
        get() {
            return "$name@hotmail.com"
        }
}

// We have to provide the "getName()" method, but we're simply creating a backing field here
class NickNameProvider(override val name: String) : NameProvider

// Alternatively, implement the getter.
class PoshNameProvider() : NameProvider {
    override val name: String
        get() = "Prof. Dr. Genius"
}

fun main() {
    val myProf = PoshNameProvider()
    val me = NickNameProvider("Exterminator 2000")

    println("Reach me at ${me.email} - following a lecture of ${myProf.name}")
}
```

The above code demonstrates the following concepts:

- Properties in interfaces, with custom getters that reference to others if needed. Note that you cannot dictate where to store the value: it's an interface, after all. 
- Implementing that interface and simply using `override val` in the primary constructor to satisfy the constraints.
- Implementing the getter yourself manually. 
- Again, class bodies that are empty (`class bla {}`) are redundant: there are no curly braces used in the definition of `NickNameProvider`.
- Again, string interpolation, not limited to a single expression, using curly braces inside double quotes. 