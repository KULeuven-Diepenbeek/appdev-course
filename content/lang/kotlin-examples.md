---
title: 2. Kotlin Examples/Exercises
---

All examples can be found in the GitHub repository of this very course, at https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples

We'll be using **IntelliJ** to create simple Kotlin projects, so leave Android Studio closed for the moment. While creating a new project, choose 

1. A **Gradle** style project, 
2. Enable the **Kotlin DSL Build Script**, and finally
3. Check **Kotlin/JVM** (uncheck Java)

![](/img/newkotlin.jpg "New Project settings for Kotlin")

Wait for the initial Gradle download/build to finish. Create your Kotlin class/source files in `src/main/kotlin`. Do not forget creating the correct package structure. See the examples in the above GitHub link.

## 1. Taxi Driving: Basic Kotlin syntax

Remember the taxi exercise from INF1 last year? The one in BlueJ that teaches you about **object composition**? 

> Model a taxi company. The company can hire drivers, buy cars, and ultimately, drive clients from an address in point A to point B, to hopefully make some money.

This is simple enough in Java and teaches you how to:

1. Create classes
2. Link classes together (a `TaxiFirm` holds a reference to one or more `Car`s and `Driver`s)
3. Create lists of things (extra)
4. Define methods to call other methods on member variables
5. Think about accessibility, constructors and getters/setters

First, take a look at the [java solution](https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples/java/taxi). Then, try to do it yourself in the Kotlin JVM. If all else fails, peek at the provided solution. 

## 2. Walking in JFX: Java interoperability

Remember the basic JavaFX exercises from INF1, where we created a smiley or person, virtually walking around on the screen? Those labs acted as a first acquaintance with the JavaFX and Movel-View-Controller concepts. The assignment was as follows:

> Create a custom `Region` and render a simple person consisting out of multiple rectangles. Create buttons up/down/left/right and implement the click events such that the person moves around on the screen in the correct direction.

This teaches you how to:

1. Adhere to the Model-View-Controller pattern
2. Create an `AnchorPane` and put stuff onto it using SceneBuilder
3. Catch and implement events in the controller
4. Draw custom things in the view
5. Decouple the model from UI logic

If that doesn't refresh your memory, take a look at the [java solution](https://github.com/KULeuven-Diepenbeek/appdev-course/tree/main/examples/java/walkingfx). Then, try to do it yourself in the Kotlin JVM. If all else fails, peek at the provided solution. 

{{% notice info %}}
Since this is a JavaFX Gradle application, it cannot be started by pressing the play button: the JavaFX module options should be passed along. We rely on the `org.openjfx.javafxplugin` for this: see the `build.gradle` file. To run the application, execute the Gradle task application -- run.
{{% /notice %}}
