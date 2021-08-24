package be.kuleuven.sensorexample

import android.content.Context
import android.hardware.SensorManager
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.kuleuven.sensorexample.sensors.SensorResult
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class SensorInspectionInstrumentedTest {

    // Auto-bootstrap the activity thanks to our dependency junit-ktx
    // You can't create an activity by yourself via a constructor call!
    @get:Rule
    val rule = activityScenarioRule<MainActivity>()

    val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test fun clickOnActivateShouldChangeTxtOut() {
        rule.scenario.onActivity {
            it.binding.btnActivate.performClick()
            assertTrue(it.binding.txtOut.text.toString().contains("Sensors started, listening..."))
        }
    }

    @Test fun changedSensorValueShouldChangeTxtOutAfterActivating() {
        rule.scenario.onActivity {
            it.binding.btnActivate.performClick()
            // This really stinks. There is NO WAY to simulate an onSensorChanged() event on Android level
            // There is also NO WAY to access the private field mSensorListeners from SystemSensorManager, a hidden class...
            // Thus, the only way to "test" this is to trigger the events ourselves!
            assertEquals(2, it.sensors.size)
            it.sensors.forEach { sensor ->
                sensor.trigger(SensorResult(1F, 2F, 3F))
            }

            val output = it.binding.txtOut.text.toString()
            assertTrue("Expected illuminance to be present in $output", output.contains("Illuminance: 1.0lm"))
            assertTrue("Expected humidity to be present in $output", output.contains("Humidity: 1.0%"))
        }
    }
}