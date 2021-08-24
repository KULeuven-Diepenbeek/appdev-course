package be.kuleuven.sensorexample.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class LetThereBeLightSensor(sensorManager: SensorManager, onSense: (SensorResult) -> Unit) : Sensable(sensorManager, onSense) {
    override val type: Int
        get() = Sensor.TYPE_LIGHT
}