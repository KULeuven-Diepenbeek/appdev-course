package be.kuleuven.sensorexample.sensors

import android.hardware.Sensor
import android.hardware.SensorManager

class IsItSoggyOutHereSensor(sensorManager: SensorManager, onSense: (SensorResult) -> Unit) : Sensable(sensorManager, onSense) {
    override val type: Int
        get() = Sensor.TYPE_RELATIVE_HUMIDITY
}