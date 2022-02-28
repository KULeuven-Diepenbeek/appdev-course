---
title: 2. Messaging - Intents
---

## Using intents to start an internal activity

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

### Passing objects through intents

Of course, passing in tens of different input fields can get cumbersome. Instead, let's provide a data class that represents the user:

<div class="devselect">

```kt
data class User(val name: String)
```

```java
public class User {
    private final String name;
    public String getName() {
        return name;
    }
    @Override
    public void toString() {
        return "[" + name + "]";
    }
    @Override
    public bool equals(object other) {
        if(other == null || other !instanceof User) {
            return false;
        }
        User otherUser = (User) other;
        return name.equals(otherUser.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
```

</div>

Remember that data classes are convenient ways to automatically implement getters/setters/equals/hashcode/tostring. Take a look at the Java code and try not to freak out. It's that ugly. 

Unfortunately, that is not enough. Intents only carry along basic key-value pairs of primitives, meaning our object has to be **serialized**. There are two options to do this:

1. The default Java way: implement `Serializable`. Use [Kotlin native serialization](https://betterprogramming.pub/why-and-how-to-use-kotlins-native-serialization-library-c88c0f14f93d) to make things easier here. 
2. The Android specific way: implement `Parcelable`. A "parcel" is a "package" that needs to be delivered form one side to the other. Parcels are much more efficient on Android devices than serializable. See the [parcelable implementation generator docs](https://developer.android.com/kotlin/parcelize) on how to enable this in your Kotlin-Android project. 

Let's settle with the first option. In order to add `@Serializable` to your data class, we need to install the `kotlinx.serialization` plugin. Add the plugin to your `plugins` block in your module `build.gradle.kts` _and_ add a dependency:

```kt
plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("plugin.serialization") version "1.5.21" // add this
}
...
dependencies {
    ...
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2") // add this
    ...
}
```

{{% notice warning %}}
Keep the `plugin.serialization` **version** the same as your Kotlin version! See the [official kotlinx.serialization github repo](https://github.com/Kotlin/kotlinx.serialization) and the [official kotlin serialization docs](https://kotlinlang.org/docs/serialization.html) on how to install and use the plugin.
{{% /notice %}}

Then, add an implementation dependency `    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")`. Lastly, update your `User` data class to add the `@Serializable` annotation that should get imported from the package `kotlinx.serialization`, and the Java `Serializable` interface to tell Android-specific methods it's a serializable object:

```kt
@Serializable
data class User(val name: String) : java.io.Serializable {
}
```

Now create a function that returns a new `User` object based on the viewbinding's values. Then pass the single `User` instance through the intent using `putExtra()`. Pull it back out using `getSerializableExtra()`. 

{{% notice note %}}
Android also supports [(two-way) **data binding**](https://developer.android.com/topic/libraries/data-binding/two-way), next to the **view binding** system introduced in [chapter 1.1](/android/activities). This allows designers to directly map properties of models into the layouts, thus avoiding having to access every single property to carry over into an intent or another object. Data binding is not part of this course but feel free to use it instead of view binding, should you wish to do so. 
{{% /notice %}}

Can you think of other smart ways to share data between activites? 

The Parcelize alternative is left as an exercise for the reader. 

## Using intents to start an internal service

Besides switching activities as screens within a single app, an intent can also pass messages along a background service that performs operations without a user interface. Services can be launched by creating a `JobScheduler` instance and passing it to `startService()` (instead of `startActivity()`) (see [API reference](https://developer.android.com/reference/android/app/job/JobScheduler)). Why would you want to do that? 

- To download a large file in the background without blocking the UI
- To convert a lot of images in the background
- To upload data to a back-end server using a HTTP POST
- ...

Services, and the difference between them and typical Java threads, are explained in detail in the [Services overview dev guide](https://developer.android.com/guide/components/services). 

The usage of services is not part of this course. 

## Using intents to interact with other apps

### Implicit intents

When you create an intent to switch to an activity, your own app knows how to handle that intent: it should go to that new activity that also lives within your own app. These are called **explicit intents**: you specify which application will satisfy the intent by supplying the class name of the activity. 

Next to _explicit_ intents, you can also create **implicit** intents. What's that? According to the docs:

> **Implicit** intents do not name a specific component, but instead declare a general action to perform, which allows a component from another app to handle it. For example, if you want to show the user a location on a map, you can use an implicit intent to request that another capable app show a specified location on a map.

Some useful uses of implicit intents:

- Ask "any app" to take a picture and return a reference to us
- Ask "any app" to browse on a map to a specific location
- Ask "any app" to print something
- Ask "any app" to dail a number
- ...

In short, **messaging between specific apps** requires the creation of implicit intents that you do NOT handle yourself. 

The code for this is really simple:

```kt
val number = Uri.parse("tel:011112233")
startActivity(Intent(Intent.ACTION_CALL, number))
```

Each system-wide known implicit intent is specified with a pre-defined String that can be auto-completed:

![](/img/implicitintent.jpg "the Auto-completion lists of possible (implicit) intents.")

See the [guide to intents](https://developer.android.com/training/basics/intents/sending) for more examples such as opening a map, a webpage, creating an e-mail with attachments, and so forth. 

{{% notice warning %}}
Applications that are not allowed to make calls will generate a `SecurityException` while attempting to start the `action.CALL` intent with the message "Permission Denial", stating which specific permission is missing (`android.permission.CALL_PHONE` in case of calling). Add these in your [android manifest file](https://developer.android.com/training/permissions/declaring) if needed. More on that in the [**security by design**](/android/security) chapter.
{{% /notice %}}

In case no single application knows how to handle your implicit intent, not even your own, `startActivity()` will throw an `ActivityNotFoundException`. It would thus be in your best interest to wrap your activity starts with a try block!

A much better, secure way of coding would be to [protect implicit intents with runtime checks](https://www.youtube.com/watch?v=HGElAW224dE). Suppose you want to open the camera and afterwards receive the image URI. The app you want might simply not be there on certain devices, or that the user has restricted profile access. The key idea here is to _check something before using it_, instead of letting it crash afterwards. That's done via `resolveActivity()`:

```kt
val intent = Intent(Intent.ACTION_CALL, number)
if(intent.resolveActivity(applicationContext.packageManager) != null) {
    startActivity(intent)
} else {
    msg("doesnt work on your device mate")
}
```

This has [known issues in certain Amdroid API versions](https://stackoverflow.com/questions/62535856/intent-resolveactivity-returns-null-in-api-30). A fallback would be to still catch the exception. 

#### Retrieving the result from intents

Let's try to capture a picture. Follow along in the [taking photos Android Dev guide](https://developer.android.com/training/camera/photobasics). Remember to advertise that your app depends on having a camera by adding `<uses-feature android:name="android.hardware.camera" android:required="true" />` in the manifest file. The intent we're going to use is `MediaStore.ACTION_IMAGE_CAPTURE`.

First, we need to register an "activity result" in our `onCreate()`, because it is only safe to call the method before the activity is in its STARTED state:

```kt
class MainActivity : AppCompatActivity() {
    private lateinit var pictureActivityResult: ActivityResultLauncher<Void>
    // ...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...
        pictureActivityResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bm: Bitmap ->
            msg("bitmap is ${bm.height} high", binding.root)
            // do something with the captured image
        }
    }
```

Next, in the onclick listener, after asking/checking for the correct permissions, fire off the result using `pictureActivityResult.launch(null)`.

Now where exactly do we specify which intent to fire off? That magic is obfuscated inside the `TakePicturePreview` class we're instantiating. You can provide your own what is called **activity contracts**, but a lot of commons are provided for you in the `ActivityResultcontracts` class. The picture preview source code looks like this:

```kt
public static class TakePicturePreview extends ActivityResultContract<Void, Bitmap> {

    @CallSuper
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, @Nullable Void input) {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    @Nullable
    @Override
    public final SynchronousResult<Bitmap> getSynchronousResult(@NonNull Context context,
            @Nullable Void input) {
        return null;
    }

    @Nullable
    @Override
    public final Bitmap parseResult(int resultCode, @Nullable Intent intent) {
        if (intent == null || resultCode != Activity.RESULT_OK) return null;
        return intent.getParcelableExtra("data");
    }
}
```

Aha, now we're getting somewhere! Upon further inspection, `TakePicture` (not the preview one) puts extra data into the intent to capture the whole output. Note the first generic type of the extended class: `Void`. So that is where that `Void` comes from in or `lateinit var pictureActivityResult`: other embedded activity result contracts will likely have other result types (`OpenDocument` has `String[]`, for instance). These details are provided for you, make use of them. 

Note that because of the implicit intent, handling the camera is left to **another app** which is already has permissions to access the camera. Therefore, we do NOT need an explicit `<uses-permission android:name="android.permission.CAMERA" />` entry in our manifest, but it's still important to _advertise_ we do use the camera with a `<uses-feature/>` tag. See the [security by design chapter](/android/security) for more information.

{{% notice note %}}
You'll learn the most while digging through the source code of the API itself while hacking your way through the code---and _not_ by just reading this page. Do not be afraid to press `CTRL+B` (Go To Declaration) in Android Studio! If you still have no idea what's going on, then `developer.android.com` is your best friend.
{{% /notice %}}

### Delivering/receiving a broadcast

Instead of keeping intents as messages within your application, you can also _broadcast_ them so that any app can receive them. The system delivers various broadcasts for system events, such as on bootup or when the device starts charging. Sending broadcasts can be done through `sendBroadcast()`. 

The usage of broadcasts is not part of this course.
