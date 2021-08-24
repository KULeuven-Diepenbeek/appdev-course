package be.kuleuven.sensorexample.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class Sensable(protected val sensorManager: SensorManager, private val onSense: (SensorResult) -> Unit) : SensorEventListener {
    abstract val type: Int

    // For testing purposes. See unit tests.
    fun trigger(result: SensorResult) = onSense(result)

    fun sense() {
        if(!isSensable()) return
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        onSense(SensorResult.fromEvent(event))
    }

    fun isSensable(): Boolean =
        sensor != null

    fun pause() =
        sensorManager.unregisterListener(this)
    fun resume() =
        sense()

    val sensor: Sensor?
        get() = sensorManager.getDefaultSensor(type)

}

data class SensorResult(val x: Float, val y: Float = 0F, val z: Float = 0F) {
    companion object {
        fun fromEvent(event: SensorEvent): SensorResult {
            return if(event.values.size > 1) SensorResult(event.values[0], event.values[1], event.values[2]) else SensorResult(event.values[0])
        }
    }
}