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
If using the Hamcrest API to write assertions, remember that `is` is a reserved keyword in Kotlin. That is, `assertThat(bla, is(such))` in Java does not compile in Kotlin. You can escape the is with a single backtick, or import it as an uppercase Is: `import org.hamcrest.CoreMatchers.'is' as Is` (use backticks).<br/>
If writing pure Kotlin, you can also opt to use [MockK](https://mockk.io/), a mocking library specifically designed for Kotlin that lets you write assumptions like this: `every { car.drive(Direction.NORTH) } returns Outcome.OK`. Examples can be found in [the demo project](/extra/demo).
{{% /notice %}}

## Android and TDD

In Android development, there are two kinds of unit tests, as explained by [Test your app - Android Developer docs](https://developer.android.com/studio/test): 

1. Classic _unit test_ classes, residing in `src/test/java`. These are the unit/integration tests you're used to, similar to the example above. 
2. Special _instrumented tests_, residing in `src/androidTest/java`. These are special tests that **run on the hardware** (or on an emulator). That is, they have access to the `Instrumentation` API giving you access to information such as `Context` of the app you're testing. 

Note that in Android development, **JUnit 4** is used, not JUnit 5 (although [it is not impossible](https://medium.com/@boonkeat/android-unit-testing-with-junit5-d1b8f9c620b6)). Instrumented tests are annotated with `@RunWith(AndroidJUnit4::class)` from the `androidx.test.ext.junit.runners` package. As soon as you run an instrumented test, the configured emulator will boot up. A separate `.apk` will be built containing your instrumented tests which will be executed on the device. 

As with the typical unit/integration/end2end test pyramid you've learned in the SES course, the same holds true here: try to write as many unit tests as possible, since bootstrapping the emulator and compiling a separate package is much, much slower! All integration, UI, and end2end tests are considered _instrumented_ tests in the Android architecture.

Looking for Android testing sample projects? https://github.com/android/testing-samples

### Database integration testing

After you've chewed on the [data storage chapter](/android/data-storage) and got your feet wet with Android's Room to access SQLite databases, you can try to write a few integration tests which test your queries and persistence layer. These tests are similar to the integration tests present in the [SESsy library](https://kuleuven-diepenbeek.github.io/ses-course/extra/sessy/) webapp. 

For exmaple, to test if something can be persisted:

```kt
@Test
fun todoItemCanBePersisted() {
    val item = Todo("brush my little pony", false)
    dao.insert(arrayListOf(item))

    val refreshedItem = dao.query().single()
    with(refreshedItem) {
        assertEquals(item.title, title)
        assertEquals(item.isDone, isDone)
        assertEquals(1, id)
    }
}
```

Employ the `Room.inMemoryDatabaseBuilder` to create your Database object. See the `todosavestate` example in the course repository for a full-fledged example. 

Since databases are part of the 3rd year program and plenty of integration testing happens in the SES course, integration-testing your Room objects is not a part of this course. Nonetheless, do take a look at the example: cross-pollination between software courses facilitates your learning!

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

{{% notice note %}}
The Espresso activity suite expects a `public` field for JUnit Rules to apply. Although in Kotlin, properties are `public` by dfeault, Kotlin's `val` in fact generates a private field with public getters by default. `@get:Rule` applies the rule to the field. `@JvmField` is an alternative way to tell Kotlin to produce a pure backing field. 
{{% /notice %}}

See the test in action:

{{< video "/vid/espresso.mp4" >}}

Do not forget to add the following _test dependencies_ to your Gradle app module:

```
dependencies {
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
}
```

If you created the project using an existing template in Android Studio, only the latter one will be missing. The `espresso` library version numbers must match!</br>
Feel free to fiddle with the Espresso toolset. The course repository has ample examples of these tests: see the `loginactivity` Kotlin example project, where the above example comes from.

However, this falls beyond the scope of this course. 

