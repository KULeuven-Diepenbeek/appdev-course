---
title: 1. Life Cycle - Activities
---

## What's an activity?

> An activity is a **single, focused thing** that a user can do.

To put it simply, an activity is a window in your app. Each window should do one thing, such as:

- Let the user login
- Show a welcome screen
- Let the user pick something out of a list
- Show the user detail information of the picked item
- Let the user modify settings
- ...

![](/img/activities.png "Two example activities.")


(Images used on this page sourced from https://franklineduardojimenezgiraldo.gitbooks.io/android-studio/)

Since a single "activity"/action/screen/window/whatever-the-name is not going to cut it, multiple activities have to be developed and **wired together**. This is done using _wireframing_. A wireframe dictates the flow of the application. The above image represents a welcome screen, and a login screen. It might transition to the login screen after tapping or after three seconds. After pressing "Sign Up", it might transition again, to a master/detail screen. The resulting wireframe might look like this:

![](/img/wireframe.png "An example wireframe that defines relationships between activities.")

Since in many activities, components reappear, they get **re-used** by splitting an activity into _fragments_. We'll get to that in the [complex layouting - fragments](/android/fragments) part.

## Creating activities in Android Studio

Let's try to create **our first activity**: login screen. Which components can you identify in the above schematics?

1. A "Login" label or graphic
2. A username text field
3. A password text field
4. A login button
5. A "or" label
6. A "create account" button/anchor link

Right. These are simple enough. Create a new project, starting with an _Empty Activity_ under template _Phone and Tablet_. It'll create a single `MainActivity.kt` class with the following minimalistic code:

```kt
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

The `activity_main.xml` will have a single `TextView` element with the label "Hello World!". Try to **bootstrap** the project before making any changes to ensure your compiler and emulator are set up correctly. 

The activity layout itself is described in XML. The root is currently `<androidx.constraintlayout.widget.ConstraintLayout/>"`. Remember JavaFX's `AnchorPane`, were you could drop components into using SceneBuilder? This is more or less the same, but the "Android way". There are of course multiple Layouts available. 

Browse through the [ConstraintLayout documentation](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout) and watch the [short introduction video](https://www.youtube.com/watch?v=XamMbnzI5vE) on how to build interfaces with it. It allows for multiple flexible ways to define constraints that dictate the position of the components: relative positioning, margins, centering, circular positioning, chaining, dimension constraining, and so forth. We'll minimize complexity by resorting to the GUI-editor embedded into Android Studio instead of writing too much UI-specific code. 

{{% notice note %}}
Fiddle with the layout editor to place the six needed components for the login screen.
{{% /notice %}}

Inspect and modify desired properties of components if needed in the right-hand pane of the Design Editor (especially the ID on the top-right):

![](/img/designproperties.jpg "Changing the text property of a TextView.")

Without specifying **constraints**, the components will be re-positioned to `(0, )` after publishing to your device. To add constraints, drag the circles to touching edges of the `ConstraintLayout`, or to other elements present in the activity. This "locks" the component, allowing it only to move by adhering to the constraints. After setting all constraints, try moving one of the components: others will move along with it due to the constraints. 

## Adding actions

Once you've got a login button up and running, it would be nice if it actually did something when pressing it. One possibility is to rely on the statically generated ids in `R` to manipulate properties:

```kotlin
findViewById<TextView>(R.id.myIdSetInTheLayout).text = "cool I changed something!!"
findViewById<TextView>(R.id.amotherId).text = "great stuff"
```

However, this is (1) cumbersome, (2) relies on IDs as Integers, and (3) not very pretty. Instead, the far easier way to do this is to use [Android View Binding](https://developer.android.com/topic/libraries/view-binding):

> **View binding** is a feature that allows you to more easily write code that interacts with views. Once view binding is enabled in a module, it generates a binding class for each XML layout file present in that module. An instance of a binding class contains direct references to all views that have an ID in the corresponding layout.

To enable, simply add the `buildFeature` in your `build.gradle.kts` file:

```
android {
    ...
    buildFeatures {
        viewBinding = true
    }
}
```

After refreshing, every XML layout file will be accompanied by a secretly _generated Java file_ that contains the items to access and attach events to. The name of the binding class is generated by converting the name of the XML file to Pascal case and adding the word "Binding" to the end. `activity_main.xml` will thus have an `ActivityMainBinding` class. 

In your "controller" (you do remember the Model-View-Controller pattern, right?) that extends from `AppCompatAcitivy`, add a `lateinit` binding field, and refer to it using `inflate()`. Then, we'll set the content view to that particular binding root. If that is done, we can add events:

```kt
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // warning, this is changed, it used to be R.main.id
        setContentView(binding.root)

        // just to prove things changed
        binding.btnLogin.setText("lol")        
    }
}
```

In JavaFX, this is a bit simpler (at first):

```java
public class SomeController {
    @FXML
    private BUtton btnLogin;

    @FXML
    public void initialize() {
        btnLogin.setOnClick....
    }
}
```

Here, we can create fields and auto-wire them. In Android, you have to rely on the generated binding class where the fields are: less messy but one more step needed to take. Remember that the more components you have, the more convoluted your FXML Controller is going to get. This is well-hidden behind the binding object in Android systems. 

We react to events such as the `click` ("tap") event in the same way as you would do in JavaFX:

<div class="devselect">

```kt
binding.btnLogin.setOnClickListener { view ->
    Snackbar.make(view, "Nice, clicked a button", Snackbar.LENGTH_LONG).setAction("Action", null).show()
}
```

```java
binding.getLoginButton().setOnClickListener(new OnClickListener() { 
    @Override
    public void onClick(View var1) {
        Snackbar.make(view, "Nice, clicked a button", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
})
```

</div>

If you can't make sense of the lambda syntax, take a peek at the conventional Java implementation.

`Snackbar` is a fast way to provide periodical feedback that automatically disappears. Ta-daa:

![](/img/loginbtn.jpg)

What else are possible events you can listen to?

- `setOnCapturedPointerListener`
- `setOnApplyWindowInsetsListener`
- `setOnFocusChangeListener`
- `setOnDragListener`
- `setOnKeyListener`
- `setOnHoverListener`
- ...

{{% task %}}
Retrieve the password value and check it with some hard-coded value. If not correct, show a warning message using a `Snackbar`. If the username is empty, also show a warning message.
{{% /task %}}

## Adding a second Activity

What should happen once we're logged in successfully? Take another look at the first image on this page: a **welcome screen** would be nice. 

Create a second layout XML file by right-clicking on the `res` folder (or layout), select New -- Layout Resource File, and name it `activity_welcome`. You're given the option to change the root element, but another `ConstraintLayout` is fine, since we're now familiar with the basics of element placing in that particular layout. Go to the design editor and pull in a nice welcome text and a sample image that corresponds to the user's avatar. When you're done with that, create the corresponding controller class called `WelcomeActivity`. 

Next, we need a piece of code to change the activity to the new one, provided the password is correct. That's done using an **Intent**, of which we'll see [more in the coming chapter](/android/intents). An intent is a way to pass messages from one activity to another, but also to tell one activity it should transition to the other:

<div class="devselect">

```kt
val intent = Intent(this, MyNewActivity::class.java)
startActivity(intent)
```

```java
Intent intent = new Intent(this, MyNewActivity.class)
startActivity(intent)
```
</div>

Note that `::class.java` is the Kotlin way to grab hold of the static `.class` instance of a class. Run your app and see if it works. Chances are you'll see it crash with the following message in the console:

```
E/AndroidRuntime: FATAL EXCEPTION: main
    Process: be.kuleuven.login, PID: 8457
    android.content.ActivityNotFoundException: Unable to find explicit activity class {be.kuleuven.login/be.kuleuven.login.WelcomeActivity}; have you declared this activity in your AndroidManifest.xml?
        at android.app.Instrumentation.checkStartActivityResult(Instrumentation.java:2065)
        at android.app.Instrumentation.execStartActivity(Instrumentation.java:1727)
        at android.app.Activity.startActivityForResult(Activity.java:5320)
```

Whoops. Each activity needs to be defined in the `AndroidManifest.xml` file where you can specify which one is the "main" that needs to be boostrapped as your application launches. Add an `<activity/>` tag there and try to guess the correct properties. Android Studio will auto-complete things for you. Make sure `exported` is set to `true`.

Try again and it should work!

{{% notice note %}}
A note on (text) sizes: in Common Attributes, changing the text size means selecting a unit in `sp`, not `px`. What's an `sp`? To be able to support **different pixel densities** of different screens, we do not rely on hard-coded pixels but let this be calculated. SP = _Scalable Pixels_ and is used for scaling text. Otherwise, use `dp`. DP = _Density-Independent Pixels_. See the [andorid multiscreen dev guide](https://developer.android.com/training/multiscreen/screendensities) for more information on how pixels are effectively calculated. Watch the video to get a better grasp of Android's _logical pixel densities_. **Never use `px`**! <br/>The same is true for graphic assets, but to avoid automatic scaling which usually ruins your PNGs, provide at least four different versions: one for each `dp` "bucket": MDPI, HDPI, XHDPI and XXHDPI. Again, see the documentation. 
{{% /notice %}}

To learn more about message passing, see [messaging: intents](/android/intents).

## The lifecycle of an activity

See [Android Developer Guide: Understanding the Activity Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)

So far, we have leveraged the `onCreate()` state to wire together things like events and fetch our binding object. However, an activity has multiple stages to go through, and each of them can be hooked into if desired. `onCreatae()` just so happens to be the first (obligatory!) callback to implement, next to other five important ones: `onStart()`, `onResume()`, `onPause()`, `onStop()`, and `onDestroy()`. Take a look at this schematic to see how they relate to each other:

![](/img/activity_lifecycle.png "A simplified illustration of the activity lifecycle. Src: android.developer.com")

As soon as the user navigates from our main activity to `MyNewActivity`, the main activity will be paused. Pressing BACK causes `onResume()` to trigger, while the other activity will be paused. When the second activity was successfully (1) created, (2) started and (3) resumed, the previous one will be stopped. Pressing back results in (1) second paused, (2) main started and (3) resumed, so that (4) second is stopped and ultimately (5) destroyed. Try it yourself!

{{% task %}}
Implement all six callbacks on the two activities (see above, section "_Adding a second Activity_"). Print a message (in the console if debugging, or using [a simple Toast message](https://developer.android.com/guide/topics/ui/notifiers/toasts)) for each one to get a sense of what state the application/activity is in. You'll notice that `onResume()` is also always called during launch.
{{% /task %}}

### Saving and restoring UI state

Besides obvious uses such as saving draft text in `onStop()`, knowing about the lifecycle is important because as soon as the user rotates the device or switches into multi-window mode, the current activity is destroyed and restarted. The problem is that any UI state is **wiped away**, but the user still expects an activity's UI state to be persistent!

For each activity you design, think about its implications. What does the user expect when he or she returns to it? Should it be wiped? Yes? No? User expectations ans system behavior should match. The Android Dev Guide states:

> Depending upon the action a user takes, they either expect that activity state to be cleared or the state to be preserved. In some cases the system automatically does what is expected by the user. In other cases the system does the opposite of what the user expects.

In order to save additional instance data before the rotation transition occurs, override `onSaveInstanceState()` and put data in the `Bundle` object:

```kt
override fun onSaveInstanceState(outState: Bundle) {
    outState.run {
        putString("name", binding.txtUsername.text.toString())
    }
    super.onSaveInstanceState(outState)
}
```

Retrieval is possible in `onRestoreInstamceState()`, or even in `onCreate()` if for whatever reason the Android OS decided to destroy your activity and recreate it. Make sure to check the contents of the single argument. `isEmpty()` is a handy check function.

{{% notice note %}}
There are a plethora of options for preserving and restoring UI state. See [saving states docs](https://developer.android.com/topic/libraries/architecture/saving-states): use a viewmodel, use raw instance state such as the example above, or even persistent storage. All options come with their own advantages and disadvantages. 
{{% /notice %}}
