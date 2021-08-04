---
title: 1. Installation Guide
---

## 1. Downloading Android Studio

Go to [developer.android.com/studio](https://developer.android.com/studio) and download the latest **stable** release of the Android Studio IDE (green button). It comes with a JDK version to compile the necessary Java/Kotlin files and automatically downloads the correct Gradle version depending on your project file. 

Bear in mind that the installation will download the required Google Android Development Kit (SDK), chipping away yet another gigabyte form your download limit. 

Required **IDE disk space**: `1.5 GB`<br/>
Required **SDK disk space**: `3.1 GB`<br/>
Required **Gradle Lib disk space** (usually in `~/.gradle`): `1.4 GB`<br/>
Required **Cache disk space** (usually in `~/.android`): `2.3 GB`<br/>

Totaling to about `8.5 GB`!! You've been warned. 

### But I'm on a Mac!

If you are on a modern Mac with an M1 chipset (AMD64), download the latest **preview** version via the correct link in the archive page: https://developer.android.com/studio/archive
Select the "Beta build" (called Arctic Fox), not the Canary build. Be mindful of the chipset architecture, otherwise the emulator will not work. 

The program will be installed in `/Applications/Android Studio Preview.app`, and the Android SDK will be downloaded in `~/Library/Android`. The emulator cache will live in `~/.android`.

### But I'm on Linux!

A few notes for Linux users:

- Do not boot the IDE as root: emulators will be installed in in `/root/android` and cannot be found (error message will be something like that).
- If you get an error "permission denied `/dev/kvm`": Add your use to the `kvm` group using the command `useradd <username> kvm`. See https://developer.android.com/studio/run/emulator-acceleration on how to install the right packages in your distribution. Note that as of June 2021, `ia32-libs-multiarch` does not exist anymore and is not needed.

## 2. Starting your first app

### Building

When you're finally done with the download and installation procedure, boot up the IDE again and you should be greeted with a "New Project" screen. Choose **Basic Activity** from the **Phone and Tablet** template, press _Next_ and choose a name/location. Leave language on _Kotlin_ and choose _API 26 Android 8.0 (Oreo)_ for your Minimum SDK (should be selected by default). 

After pressing _Finish_, Android Studio will auto-download the correct Gradle version and dependencies. 

![](/img/newproject.jpg "Selecting a new project")

Try to **build** the project (top right: hammer icon, or from Build menu, or using shortcuts). It will take a while the first time. 

### Deploying

Try to **deploy** the project. Your virtual machine will probably not be installed/downloaded yet. Go to the AVD manager (top right, open up the second combobox, or using menu Tools -- AVD Manager). 

{{% notice warning %}}
Choosing the right API and target for your virtual device is extremely important. Be sure to match your CPU architecture (x64/arm64/...). Be sure to try and match the API version with the API version in your `build.gradle` targetSdk/compileSdk/buildToolsVersion to avoid compatibility issues.
{{% /notice %}}

The virtual device will likely have to be downloaded first, usually into `~/.android/avd`, where the `.ini` config file of the emulator also resides. 

![](/img/avdmanager.jpg "Go to the AVD Manager in the build tool menu button bar")

After correctly configuring your virtual device (the resolutions and device type do not matter that much right now), press the **Play button** again (Run app: `CTRL+R`). Hopefully, after a while, the emulator will do a cold boot (you should see the Android/Google logo and a home screen), and after that your compiled app will load up. 

### Fiddling

Try to alter a few things here and there to see if recompiling/redeploying works. Try to make yourself familiar with the project structure while you're at it:

```
|- app
    |- build
    |- libs
    |- src
        |- main
            |- java
                |- com
                    |- exampletest
                        MainActivity.kt
                        SomeFragment.kt
            |- res
                |- drawable
                    someimage.jpg
                    ic_laumcher_background.xml
                |- layout
                    activity_main.xml
                    content_main.xml
                    somefragment.xml
                |- menu
                    menu_main.xml
                |- navigation
                    nav_graph.xml
                |- values
                    colors.xml
                    strings.xml
                    ...
                |- mipmap
                    ...
            AndroidManifest.xml
        |- test
            |- java
                |- com
                    |- exampletest
                        SomeTest.kt
        |- androidTest
            |- java
                |- com
                    |- exampletest
                        ExampleInstrumentedTest.kt            
    build.gradle
    proguard-rules.pro
|- build
|- gradle
build.gradle
gradle.properties
settings.gradle
local.properties
gradlew
```

A few different things to remember:

#### Gradle

The toplevel `build.gradle` file should not be modified---changes usually happen in the `build.gradle` file in your `app` folder (In Android Studio: "Module"). 

The wrapper properties file should point to Gradle 7.x for Android projects. 

For more information on Gradle, see the [Software Engineering Skills: Java Gradle Projects](https://kuleuven-diepenbeek.github.io/ses-course/dependency-management/gradle/) lessons. Remember that in case you create Kotlin DSL tyle Gradle projects, you should be on the lookout for `build.gradle.kts` - see [Gradle Kotlin-style projects](https://kuleuven-diepenbeek.github.io/ses-course/dependency-management/kotlin-gradle/).

#### Code

In:

1. `src/main/java`, as expected.
2. `src/test/java`, as epected. These are [your unit tests](https://kuleuven-diepenbeek.github.io/ses-course/tdd/).
3. `src/androidTest/java`---woah, what's this? These are android-instrumented specific tests that need to run on the device itself: usually UI-related tests. We'll inspect thsese in [the chapter on TDD](/lang/tdd).

#### Resources

Android's UIs are _described_ instead of coded in accompanied `.xml` files. These live in `app/src/res` under subdirs `layout`, `menu` and `navigation`. See the [Activities lesson](/android/activities/) to learn more about these files and their contents. 

For now, go ahead and open up `app/src/res/values/strings.xml`. It should contain something like this:

```xml
<resources>
    <string name="app_name">TestApp2</string>
    <string name="action_settings">Settings</string>
    <!-- Strings used for fragments for navigation -->
    <string name="first_fragment_label">First Fragment</string>
    <string name="second_fragment_label">Second Fragment</string>
    <string name="next">Next</string>
    <string name="previous">Previous</string>

    <string name="hello_first_fragment">Hello first fragment</string>
    <string name="hello_second_fragment">Hello second fragment. Arg: %1$s</string>
</resources>
```

The resources can be accessed in the layout XMLs to put labels on buttons and re-use key/values. For example, a button can be defined as `<Button ... android:text="@string/next" .../>`, referring to the label "Next" in the above XML. 

Go ahead and try to change the label of the example button to "Hello World!". Go nuts. 

Double-click on `fragment_first.xml` or `activity_main.xml` to see the UI editor. On the **top right**, you can see "Code | Split | Design", to switch XML views, just like **SceneBuilder in JavaFX**. You do remember that from INF1, right? 

Click on Design and drag in a new `ImageView` from the _Common_ palette. It will open a dialog called _Pick a Resource_. Press `+`, choose "Import Drawables", ans select a random image file. Images are also described in XML and fairly complex: for each resolution a new one should be provided in case your app runs on a tablet/big screen/small screen/horizontal/vertical mode, etc. The name does not matter. 

![](/img/sampleimage.jpg "A nice test image for our test app.")

Note that after adding the image, the following code was added in the XML of the fragment:

```xml
    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/_ee2e58a037610e49f7165f1df0087e1"
        tools:layout_editor_absoluteX="0dp"
        tools:layout_editor_absoluteY="0dp" />
```

Redeploy your test app to verify if it works in your emulator.

Yay! Crackling fireworks! You've officially gained the right to proclaim _It Works On My Machine_.

#### Getting to know the IDE

_Android Studio_ is part of JetBrains' IDEA development environment. That is, it behaves and functions exactly like its other products:

- [CLion](https://www.jetbrains.com/clion/) for cross-platform C/C++ development, used in the [Software Design in C/C++ Course](https://kuleuven-diepenbeek.github.io/cpp-course/)
- [GoLand](https://www.jetbrains.com/go/), the most complete Go-centric IDE
- [IntelliJ](https://www.jetbrains.com/idea/) IntelliJ, the well-respected Java-centered development environment, used in INF1/[Software Engineering Skills](https://kuleuven-diepenbeek.github.io/ses-course/)

**Shortcuts**

Since Android Studio is built on top of IntelliJ's IDEA, the **shortcut key keymaps** are the same! 

Have a look at [the IDEA Reference keymap card](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)

**More features**

Consult [Meeting Android Studio](https://developer.android.com/studio/intro) at `developer.android.com` for a run-down on the User Interface and project structure guide of the IDE. This should be familiar to you, as you have used other JetBrains products in other courses in your engineering trajectory.

Try to (re-)familiarize yourself with the tool by trying to:

- Quickly access a certain file/method/class
- Zooming in and out, and switching to presenter mode
- Auto-completing a code fragment
- Automatically generating a constructor and getters/setters
- Navigating to a test method
- Running unit tests and switching to the tests while navigating the production code
- Executing specific Gradle tasks
- Setting breakpoints and walking through these in debug mode
- ...