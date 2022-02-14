---
title: 3. Kotlin Examples/Exercises
---

All examples can be found in the GitHub repository of this very course, at https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples

We'll be using **IntelliJ** to create simple Kotlin projects, so leave Android Studio closed for the moment. While creating a new project, choose 

1. A **Gradle** style project, 
2. Enable the **Kotlin DSL Build Script**, and finally
3. Check **Kotlin/JVM** (uncheck Java)

![](/img/newkotlin.jpg "New Project settings for Kotlin")

Wait for the initial Gradle download/build to finish. Create your Kotlin class/source files in `src/main/kotlin`. Do not forget creating the correct package structure. See the examples in the above GitHub link.

The Kotlin DSL (_Domain Specific Language_) provides an alternative syntax to the traditional Groovy DSL in Gradle buidlfiles with superious IDE support. This means that every statement in your build script should be valid Kotlin syntax. For more information, inspect to the [Software Engineering Skills: Kotlin Gradle files](https://kuleuven-diepenbeek.github.io/ses-course/dependency-management/kotlin-gradle/) course information page. 

## 1. Backpack collecting: Basic Kotlin syntax

Remember the backpack exercise from INF1 last year? The one in BlueJ where you had to add different items into your backpack, of which each has its own weight, and calculate the total weight of the pack? 

Let's try to implement a simple version of that in Kotlin. 

1. Create a `Stuff` class. It has an _immutable_ `weight` property. 
2. Create a `Backpack` class. It should contain a collection where the `Stuff` instances can be put in: create an `add()` function, as well as a `weight` getter that calculates _ad-hoc_.
3. Create a `main` function. Fool around by adding stuff into the backpack. 


Things of note to take into account:

- Leverage primary constructors! Do you think a data class suffices?
- Hos is the getter of `weight` defined? As a property or a function?
- How to loop over the elements in the backpack? Which `foreach` style did you employ?
- What about nullable or non-nullable things? Can something be `null` here? What if the backpack is empty?


## 2. Taxi Driving: Basic Kotlin syntax

Remember the taxi exercise from INF1 last year? The one in BlueJ that teaches you about **object composition**? 

> Model a taxi company. The company can hire drivers, buy cars, and ultimately, drive clients from an address in point A to point B, to hopefully make some money.

This is simple enough in Java and teaches you how to:

1. Create classes
2. Link classes together (a `TaxiFirm` holds a reference to one or more `Car`s and `Driver`s)
3. Create lists of things (extra)
4. Define methods to call other methods on member variables
5. Think about accessibility, constructors and getters/setters

First, take a look at the [java solution](https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples/java/taxi). Then, try to do it yourself in the Kotlin JVM. If all else fails, peek at the provided solution. 

## 3. Walking in JFX: Java interoperability

Remember the basic JavaFX exercises from INF1, where we created a smiley or person, virtually walking around on the screen? Those labs acted as a first acquaintance with the JavaFX and Movel-View-Controller concepts. The assignment was as follows:

> Create a custom `Region` and render a simple person consisting out of multiple rectangles. Create buttons up/down/left/right and implement the click events such that the person moves around on the screen in the correct direction.

This teaches you how to:

1. Adhere to the Model-View-Controller pattern
2. Create an `AnchorPane` and put stuff onto it using SceneBuilder
3. Catch and implement events in the controller
4. Draw custom things in the view
5. Decouple the model from UI logic

If that doesn't refresh your memory, take a look at the [java solution](https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples/java/walkerfx). Then, try to do it yourself in the Kotlin JVM. If all else fails, peek at the provided solution. 

{{% notice info %}}
Since this is a JavaFX Gradle application, it cannot be started by pressing the play button: the JavaFX module options should be passed along. We rely on the `org.openjfx.javafxplugin` for this: see [this `build.gradle.kts` file you can copy over](https://raw.githubusercontent.com/KULeuven-Diepenbeek/appdev-course/main/examples/kotlin/walkerfx/build.gradle.kts). To run the application, execute the Gradle task application -- run.
{{% /notice %}}

When you're done, compare your solution with the one provided. The following constructs were modified from the Java version:

- Setters are usually directly accessed. Instead of `getChilden().add(x);`, you just use `children.add(x)`.
- An `init {}` block is needed in the view if you want to call a method in the primary constructor.
- Resource loading is a bit weird, as we need to grab the java class using `MyClass::class.java.getResources()`. See `MainApp.kt`.
- Note how little code we require (`125` lines in total), compared to the java implementation (`157` lines).
- Extending JavaFX classes also automatically calls the (default) constructor: `class Main : Application()`. Notice the brackets. Also note `extends` is replaced by a simple semicolon. 

There are a lot of things that can go wrong when calling Java from Kotlin. For example, some Kotlin-specific keywords, such as `when`, can be simply functions in Java. To call these, you need to use backticks. Consult the [Kotlin-Java interop guide](https://developer.android.com/kotlin/interop) if you encounter a weird error while calling a native Java method. 

## More Examples

More small and large examples can be found in the GitHub repository of this very course, at https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples/kotlin

On `kotlinlang.org`, examples that explore every aspect of the Kotlin language are neatly summarized using their online Playground: https://play.kotlinlang.org/byExample/overview. We **strongly recommend** you to check these out: they are very short, do not require anything to install, and touch upon every basic but important concept you'll need to master.

There are also Koans available https://play.kotlinlang.org/koans/overview that let you get familiar with the Kotlin syntax by training again and again until the syntax has been well-imprinted into your brain. 

