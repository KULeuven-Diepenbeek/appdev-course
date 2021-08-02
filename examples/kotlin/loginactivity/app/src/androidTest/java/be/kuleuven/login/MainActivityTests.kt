package be.kuleuven.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTests {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)
    // the OLD but still documented way: var mIntentsRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun givenIncorrectPassword_whenLoginPressed_thenErrorMessage() {
        onView(withId(R.id.txtPassword)).perform(ViewActions.typeText("whoops"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

        assertEquals("expected no other intents to be fired", 0, Intents.getIntents().size)
        onView(withText("Invalid password!")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun givenNoPassword_whenImmediatelyLoginPressed_thenErrorMessage() {
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

        assertEquals("expected no other intents to be fired", 0, Intents.getIntents().size)
        onView(withText("Invalid password!")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun givenCorrectPassword_whenLoginPressed_thenTransitionToWelcome() {
        onView(withId(R.id.txtPassword)).perform(ViewActions.typeText("supersecret"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(WelcomeActivity::class.java.name))
    }
}