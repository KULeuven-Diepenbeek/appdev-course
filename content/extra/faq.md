---
title: 2. Common Errors FAQ
---

### 1. minSdkVersion mismatch

Error:

> "Expected minSdkVersion >= 21 but found 1"

Cause: mismatch in `build.gradle` of android SDK and configured SDK in your emulator.

Solution: just keep these the same. The error disappeared here after selecting for example 30/30.

### 2. Emulator process was killed?

Error:

> Emulator: process was killed

Cause: a hundred different things. Consult the Android Studio error log for more details---these aren't displayed in the IDE. Either pick Help -- show Log (or show Log in Finder if you're on a Mac), or locate it on the HDD (Mac: `~/Logs/Google/AndroidStudioPreviewX`, Win: `C:\Users\<name>\.AndroidStudioX\system\log`)

Find the process was killed message, and you'll likely see another error that caused that one. For instance:

- Too little disk space.
- Logged in as root user in Linux. `.ini` file could not be found.
- `x86` emulator installed on an `x64`-architecture system
- Apple M1 silicon-related errors if you did not install the latest preview edition.

### 3. Virtualization HAXVM-related errors

Error:

> Unable to install Intel® HAXM

Or: 

> Your CPU does not support VT-x.

Or:

> Unfortunately, your computer does not support hardware accelerated virtualization.

Cause 1: Are you on a Mac M1 and did you install the latest release instead of the preview release? You need to download an Android Virtual Device based on an ARM systems image. This is done automatically in the latest preview edition of Android Studio. See [installation guide](/extra/install).

Cause 2: Are you on a 64-bit system but did you install 32-bit emulators or components? Pay careful attention to the processor architecture of the installation and your machine---these should match!

Cause 3: Your HAXM version is not up to date. Try installing a more recent version of HAXM (7.8.0 is the latest) or another Win-specific hypervisor. See https://developer.android.com/studio/run/emulator-acceleration for Win10/11 specific troubleshooting.

### 4. Invalid Entry CRC while deploying

Error:

> Failed to transform material-1.3.0.aar invalid entry CRC expected 0x9797... but got 0x48546....

Cause: I have no idea, and Google/Stack Overflow doesn't seem to know either. It happens sometimes after building and deploying. The emulator boots, but the app won't load in the emulator. 

Solution: Rebuild. Do not restart the emulator, just leave it open. Simply rebuilding seems to fix this problem. 

### 5. LifecycleOwners must call register before they are STARTED.

Error:

> ... is attempting to register while current state is STARTED. LifecycleOwners must call register before they are STARTED.

Cause:

Are you calling `registerForActivityResult` in `onCreate()` using a callback of sorts? If so, your fragment/activity is still in STARTED state and should be in CREATED state for this particular intent to receive the results. Usually if you want to receive a camera image. 

Solution: move your listener outside of create scope (e.g. `onViewCreated()`).

### 6. NoActivityResumedException when running instrumented tests

Error:

```
androidx.test.espresso.NoActivityResumedException: No activities found. Did you forget to launch the activity by calling getActivity() or startActivitySync or similar?
    at dalvik.system.VMStack.getThreadStackTrace(Native Method)
    at java.lang.Thread.getStackTrace(Thread.java:1736)
    at androidx.test.espresso.base.DefaultFailureHandler.getUserFriendlyError(DefaultFailureHandler.java:12)
    at androidx.test.espresso.base.DefaultFailureHandler.handle(DefaultFailureHandler.java:7)
    at androidx.test.espresso.ViewInteraction.waitForAndHandleInteractionResults(ViewInteraction.java:8)
    at androidx.test.espresso.ViewInteraction.desugaredPerform(ViewInteraction.java:11)
    at androidx.test.espresso.ViewInteraction.perform(ViewInteraction.java:4)
    at be.kuleuven.login.MainActivityTests.givenCorrectPassword_whenLoginPressed_thenTransitionToWelcome(MainActivityTests.kt:60)
```

Cause: you forgot to bootstrap an activity in your test using `@get:Rule` and `@Before`/`@After` methods. See the [Kotlin/Android TDD](/lang/tdd) chapter for more information.

Solution: Initialize your activity before making any assumptions!

### 7.  Build failures: KaptWithoutKotlincTask$...

Error:

```
FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:kaptDebugKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask$KaptExecutionWorkAction
   > java.lang.reflect.InvocationTargetException (no error message)
```

Cause: You've enabled `kotlin-kapt`---probably for Room database tooling. That means you've done something wrong in your Dao or object-to-save. For instance, made the primary key an immutable `val` instead of a `var`. Re-check your database objects and make sure everything is as it should be!

Build radle with `./gradle build --stacktrace` to get more output and see the underlying exception. 

Solution: fix the Room-specific mistakes (annotation-related). Another possible cuse is your M1 Mac chipset: see [data storage](/android/data-storage), add the `kapt "org.xerial:sqlite-jdbc:3.34.0"` dependency yourself. 


### 8. androix.annotation Gradle version incompatibilities

Error:

> Inconsistencies in the existing project dependencies found.

```
This operation requires the libraries
androix.navigation:navigation-fragment-ktx:+,
androix.navigation:navigation-ui-ktx:+.

Problem: Inconsistencies in the existing project dependencies found.
Version incompatiblity between:
- com.google.androidmaterial:material:1.8.0@aar and:
- androidx.appcompat:appcompat:1.6.1@aar

With the dependency:
- androidx.annotation:*:1.1.0
```

Cause: you added a navigation graph in `/res` for the first time so extra dependencies are added (automatically), but `androidx.annotation` is referenced twice with different versions as a **transient dependency**. This is an internal Google issue. 

Solution: update `material` and/or `appcompat` to their respective latest versions, search for them in the Maven repo at https://mvnrepository.com/artifact/androidx.appcompat/appcompat 

If that still doesn't work, add a reference to the transient dependency yourself by adding `api 'androidx.annotation:annotation:1.1.0'` as a dependency in `build.gradle`. See [this related GitHub issue](https://github.com/wix/Detox/issues/1631).


### 9. Help, my layout disappears in design/run mode!

Error: in XML layout design mode or when running the app, none of your UI elements appear, even though they are present and correct in the XML file itself. 

Cause: you likely have made a layout sizing/constraint error. Double check the following properties:

- Is everything constrained correctly when using a `ConstraintLayout`, relative to each other?
- Are the `width` and `height` properties set correctly?
   + If designing a single item for a recyclerview, have you incorrectly set the height to `match_parent`, leaving no room for other elements? 
   + Try setting either property to `0dp` or `wrap_content` and see if that helps. 

Solution: fix your layout XML accordingly! If still unsure what to do, see the examples folder in the course GitHub repository. 

