---
title: 2. Messaging - Intents
---

What if, instead of showing a generic welcome screen after logging in, we want to _personally_ welcome the user? 

Instead of "Welcome, have a nice day", as pictured in:

![](/img/activities.png "Two example activities from 1.1.")

We'd like to say "Welcome [username]"! That is, we want to **pass arguments** from one activity to the next. We've already created an intent to start a second activity using `Intent(this, MyNewActivity::class.java)`. After this piece of code, and before calling `startActivity()`, we can add stuff to the intent which is passed along the new activity:

```kt
val intent = Intent(this, WelcomeActivity::class.java)
intent.putExtra("username", binding.txtUsername.text.toString())
startActivity(intent)
```

On the receiving side, simply pull out the data using the appropriate `get` function in the `onCreate()` function after setting the content view to the correct root binding:

```kt
val username = intent.getStringExtra("username")
binding.txtWelcome.text = "Welcome, $username"
```

## Passing objects through intents

Of course, passing in tens of different input fields can get cumbersome. Instead, let's provide a data class that represents the user:

```kt
data class User(val name: String) {
}
```

Unfortunately, that is not enough. Intents only carry along basic key-value pairs of primitives, meaning our object has to be **serialized**. There are two options to do this:

1. The default Java way: implement `Serializable`. Use [Kotlin native serialization](https://betterprogramming.pub/why-and-how-to-use-kotlins-native-serialization-library-c88c0f14f93d) to make things easier here. 
2. The Android specific way: implement `Parcelable`. A "parcel" is a "package" that needs to be delivered form one side to the other. Parcels are much more efficient on Android devices than serializable. See the [parcelable implementation generator docs](https://developer.android.com/kotlin/parcelize) on how to enable this in your Kotlin-Android project. 

Let's settle with the first option. In order to add `@Serializable` to your data class, we need to [install the kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) plugin. Add the plugin to your `plugins` block in your module `build.gradle.kts` (keep the **version** the same as your Kotlin version!):

```kt
plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("plugin.serialization") version "1.5.21" // add this
}
```

Then, add an implementation dependency `    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")`. Lastly, update your `User` data class to add the `@Serializable` annotation that should get imported from the package `kotlinx.serialization`, and the Java `Serializable` interface to tell Android-specific methods it's a serializable object:

```kt
@Serializable
data class User(val name: String) : Serializable {
}
```

Now create a function that returns a new `User` object based on the viewbinding's values. Then pass the single `User` instance through the intent using `putExtra()`. Pull it back out using `getSerializableExtra()`. 

{{% notice note %}}
Android also supports [(two-way) **data binding**](https://developer.android.com/topic/libraries/data-binding/two-way), next to the **view binding** system introduced in [chapter 1.1](/android/activities). This allows designers to directly map properties of models into the layouts, thus avoiding having to access every single property to carry over into an intent or another object. Data binding is not part of this course but feel free to use it instead of view binding, should you wish to do so. 
{{% /notice %}}

Can you think of other smart ways to share data between activites? 

The Parcelize alternative is left as an exercise for the reader. 
