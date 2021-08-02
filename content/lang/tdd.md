---
title: 4. Test Driven Development
---

Learn the whys and the hows of TDD in [Software Engineering Skills: TDD](https://kuleuven-diepenbeek.github.io/ses-course/tdd/).

## Kotlin and JUnit 5

When writing tests in Kotlin, pretty much all conventional Java rules apply. Just write the JUnit 5 test as if you were writing a Java test, except that the syntax is Kotlin-specific. 

An example:

```kt
class PeriodTests {
   @Test
    fun `Given two overlapping periods When overlapsWith is called Then return true`() {
        val jandec19 = Periode(
            LocalDate.of(2019, 1, 1),
            LocalDate.of(2019, 12, 31)
        )
        val maartnov19 = Periode(
            LocalDate.of(2019, 3, 1),
            LocalDate.of(2019, 11, 31)
        )
        assertTrue { jandec19.overlapsWith(maartnov19) }
    }
}
```

A note on the imports:

- `@Test` is the JUnit annotation: `import org.junit.jupiter.api.Test`
- `assertTrue` is a kotlin-specific one, as you can see that a closure block is given as the single argument: `import kotlin.test.assertTrue`

Typical Java-based test functions are `overlapsWithWhenBothPeriodsAreFilledInIsTrue`. However, to enhance readability, kotlin allows you to write _full sentences_ as function names using backtics. This is especially handy in test classes, as long as you constrain yourself to a system: for example, Given x When y Then z. Note that sometimes Android Studio will complain about these full-sentence test names for Android projects. In that case, you'll have to revert to Java-style camel case method names. 

To enable JUnit 5 in your Gradle-enabled Kotlin project, all you have to do is add the following to your `build.gradle.kts`: 

```
dependencies {
    testImplementation(kotlin("test"))
    // testImplementation("org.hamcrest:hamcrest:2.2") -> this is optional
}

tasks.test {
    useJUnitPlatform()
}
```

{{% notice warning %}}
If using the Hamcrest API to write assertions, remember that `is` is a reserved keyword in Kotlin. That is, `assertThat(bla, is(such))` in Java does not compile in Kotlin. You can escape the is with a single backtick, or import it as an uppercase Is: `import org.hamcrest.CoreMatchers.'is' as Is` (use backticks).
{{% /notice %}}

## Android and TDD

In Android development, there are two kinds of unit tests, as explained by [Test your app - Android Developer docs](https://developer.android.com/studio/test): 

1. Classic _unit test_ classes, residing in `src/test/java`. These are the unit/integration tests you're used to, similar to the example above. 
2. Special _instrumented tests_, residing in `src/androidTest/java`. These are special tests that **run on the hardware** (or on an emulator). That is, they have access to the `Instrumentation` API giving you access to information such as `Context` of the app you're testing. 

Note that in Android development, **JUnit 4** is used, not JUnit 5 (although [it is not impossible](https://medium.com/@boonkeat/android-unit-testing-with-junit5-d1b8f9c620b6)). Instrumented tests are annotated with `@RunWith(AndroidJUnit4::class)` from the `androidx.test.ext.junit.runners` package. As soon as you run an instrumented test, the configured emulator will boot up. A separate `.apk` will be built containing your instrumented tests which will be executed on the device. 

As with the typical unit/integration/end2end test pyramid you've learned in the SES course, the same holds true here: try to write as many unit tests as possible, since bootstrapping the emulator and compiling a separate package is much, much slower! All integration, UI, and end2end tests are considered _instrumented_ tests in the Android architecture.

Looking for Android testing sample projects? https://github.com/android/testing-samples

### Scenario-based UI testing

Besides the exposed `Context` object in instrumented tests, we can also write **scenario tests**. For web-based programs, we can rely on WebDriver and a Selenium webbrowser plugin to record a specific scenario and verify the visibility and position of UI elements. 

In Android, the same concepts exist in the form of [the Espresso Test Recorder](https://developer.android.com/studio/test/espresso-test-recorder). 

Which procedures should be _instrumented_, and which ones should be unit testable? Anything that interacts with signals or hardware should also be instrument-tested at least once. Remember to stub/mock out as much as possible to convert these scenarios into unit tests. Espresso has interesting methods that allow you to stub intents: see [this activity test class](https://github.com/android/testing-samples/blob/master/ui/espresso/IntentsAdvancedSample/app/src/androidTest/java/com/example/android/testing/espresso/intents/AdvancedSample/ImageViewerActivityTest.java) as an example. 

Espesso allows us to select views, press on buttons, and verify something on-screen:

```kt
@RunWith(AndroidJUnit4::class)
class MainActivityTests {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun givenCorrectPassword_whenLoginPressed_thenTransitionToWelcome() {
        onView(withId(R.id.txtPassword)).perform(ViewActions.typeText("supersecret"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(WelcomeActivity::class.java.name))
    }
}
```

Feel free to fiddle with the Espresso toolset. The course repository has ample examples of these tests: see the `loginactivity` Kotlin example project, where the above example comes from.

However, this falls beyond the scope of this course. 

