package be.kuleuven.sensorexample

import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import be.kuleuven.sensorexample.databinding.ActivityMainBinding
import be.kuleuven.sensorexample.sensors.IsItSoggyOutHereSensor
import be.kuleuven.sensorexample.sensors.LetThereBeLightSensor
import be.kuleuven.sensorexample.sensors.Sensable
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val sensors: MutableList<Sensable> = mutableListOf()

    val sensorManager: SensorManager
        get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.btnActivate.setOnClickListener(this::activateSensors)
        binding.txtOut.text = "Click to scan sensors!"

        setContentView(binding.root)
    }

    private fun updateSensorText(txt: String) {
        val stamp: String = SimpleDateFormat("HH:mm:ss.SSS").format(Date())
        if(binding.txtOut.text.toString().countOccurences('\n') > 10) {
            binding.txtOut.text = "Stamp: $stamp -- $txt"
        } else {
            binding.txtOut.text = "${binding.txtOut.text}\nStamp: $stamp -- $txt"
        }
    }

    private fun tryToActivateSensor(sensor: Sensable) {
        if(!sensor.isSensable()) {
            updateSensorText("Whoops, no ${sensor::class.simpleName} sensor present?")
            return
        }

        sensor.also {
            it.sense()
            sensors.add(it)
        }
    }

    override fun onPause() {
        super.onPause()
        sensors.forEach { it.pause() }
    }

    override fun onResume() {
        super.onResume()
        sensors.forEach { it.resume() }
    }

    private fun activateSensors(view: View) {
        tryToActivateSensor(LetThereBeLightSensor(sensorManager) {
            updateSensorText("Illuminance: ${it.x}lm")
        })
        tryToActivateSensor(IsItSoggyOutHereSensor(sensorManager) {
            updateSensorText("Humidity: ${it.x}%")
        })

        updateSensorText("Sensors started, listening...")
    }
}