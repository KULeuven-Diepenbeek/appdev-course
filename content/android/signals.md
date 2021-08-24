---
title: 6. Embedded Signals
---

Smartphones typically come equipped with a lot of hardware trinkets: multiple camera lenses, bio-metric scanners, gyroscopes, light sensors, ... Accessing these in your app might lead to inventive use. We've already seen in the [intents chapter](/android/intents) that _implicit intents_ are a good way to let other apps or the Android system itself handle the security issues involved with accessing these embedded signals. Some commonly used systems are even provided for you in the `ActivityResultContracts` class, such as `TakePicture` and `TakePicturePreview`.

An overview of sensor documentation is available at the [Android Developers docs: Sensors](https://developer.android.com/guide/topics/sensors).

## How to access sensor data

### 1. Checking for capabilities

First off, not all phones are equal. That means you need to **identify sensor capabilities** before doing anything else! Also, depending on the Android API level, some sensors aren't available, so make sure to check (see [this overview table](https://developer.android.com/guide/topics/sensors/sensors_overview)).

Since a "Sensor" is quite abstract, and there are many types of sensors available, the Android API offers a general, but direct, abstraction layer in the `android.hardware` package. Once you've got a handle to the sensor, it is up to the developer to check values, such as:

- `resolution`
- `maximumRange`
- `power`
- `vendor`
- `version`
- ...

For instance, in the example below, we first check whether Google's gravity sensor v3 is available. If not, we fall back to a general accelerometer. If not, perhaps an appropriate message to the end user should be displayed:

```kt
sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
    val availableGravitySensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_GRAVITY)
    val googlev3Sensor = availableGravitySensors.first { it.vendor.contains("Google LLC") && it.version == 3 }
    println(googlev3Sensor.maximumRange)
} else {
    val acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    if(acceleroSensor == null) {
        // message to the user?
        return
    }
    println(acceleroSensor.maximumRange)
}
```

The sensor manager is actually a high-level interface that hides a lot of low-level details, such as kernel drivers that access the hardware itself. The Android app communicates with the framework, which lie on top of the sensor kernel-drivers, which operate the hardware:

![](/img/sensors.jpg "Source: slideshare.net, Chinmay VS, Sensors on Android")

Besides runtime-checking, if publishing to Google Play, you can also prescribe the sensor requirements in the manifest file with a `<uses-feature />` tag. See above the Android docs link for more information. 

Sensor usage always comes in three steps:

1. Get hold of the general `SensorManager`
2. Check capabilities.
3. If successful, get and use a `Sensor` instance through the manager.

### 2. Listening to data

Once all is good to go, you can register a listener to the sensor, which requires the implementation of the `SensorEventListener` interface. It comes with two methods: 

1. `onAccuracyChanged(sensor: Sensor, accuracy: Int)`
2. `onSensorChanged(event: SensorEvent)`

the `SensorEvent` object contains raw data (via the `values` property), sometimes up to three: one for each axis. 

{{% notice note %}}
Remember to register and unregister the event listener on the right moment. For activities, that would be in the `onResume` and `onPause` methods. Take the lifecycles of activities and fragments into account! 
{{% /notice %}}

When registering, you need to specify a delay: the _sampling rate_ that, depending on the sensor and your needs, could differ from the default `SENSOR_DELAY_NORMAL`, which is about `200.000` microseconds: 

```kt
sensorManager.registerListener(myListener, mySensor, SensorManager.SENSOR_DELAY_NORMAL)
```

If you would like to try this out in the emulator, be sure to go to the emulator settings, check the **virtual sensors** tab, and click on "more sensors". Then, fiddle with the sliders, and voila: you're triggering `onSensorChanged` events:

![](/img/emulator-sensors.jpg)

See the `examples/kotlin/sensors` demo project for more information. <br/>In the example, pay special attention to the way sensor events are unit tested---it's a hassle!

## Types of sensors

The Android docs categorizes different existing sensors into the following groups:

1. **Motion** sensors, such as:
    - Accelerometers: measures acceleration force
    - Gyroscopes: measures force of rotation
    - Gravity sensors: measures force of gravity
    - Step counters: measures the number of steps taken since the last reboot
2. **Position** sensors, such as:
    - Rotation vector component sensors
    - Magnetic fields: measure geomagnetic field strength
    - Orientation sensors: measure angles around x/y/z-axis
    - Proximity: distance from objects in cm
3. **Environment** sensors, such as:
    - Temperature sensors: measures ambient air or device temperature
    - Light sensors: measures illuminance
    - Pressure sensors:measures ambient air pressure
    - Humidity sensors: measures ambient relative humidity

The exact values of the `SensorEvent.values` vector can be consulted [in the Anrdoid docs](https://developer.android.com/guide/topics/sensors/sensors_motion). Details of usage, such as dimensions, mathematical representations, the calculation of angular speeds, and so forth are also available in the docs. A code sample called [the batch step sensor](https://github.com/android/sensors-samples/tree/main/BatchStepSensor/) is available on GitHub. 

Not all sensors return three-dimensional raw data. For instance, the light sensor only contains one value: illuminance, expressed in `lx`.

### GPS: Location Services

Getting hold of the location of the user requires special permissions, obviously. Since the GPS system is more heavily used than other sensors, it uses a more high-level, different API. 

Read about [using locations on Android here](https://developer.android.com/training/location). 

There are two kinds of "location access categories":

- Foreground location: navigation apps, requesting location for GPS data on a picture, etc.
- Background location: IoT apps that constantly track the location of the device. 

Both require different permissions and are accessed in different ways. For brevity, we will leave background location optional and up to the reader to discover. 

#### Foreground location access

First, declare permissions in the manifest file, and determine whether or not you are content with `COARSE_LOCATION`, or you really must need `FINE_LOCATION`:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

Then, after checking whether or not GPS is enabled, create a location manager, and register---again---an event listener, that implements `LocationListener`:

```kt
val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    // option 1: more complex
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, myListener)
    // option 2: only get the last location, a simple one-shot call
    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
}
```

The simplest solution is using `getLastKnownLocation`. If a `GPS_PROVIDER` is not present, you can fall back to network triangulation by checking if the provider `LocationManager.NETWORK_PROVIDER` is enabled. 

The `LocationListener` interface has a few methods, of which `onLocationChanged(location: Location)` is the most interesting, where you can pry out the `latitude` and `longitude` from the single argument. Note that this does _not_ give you an address: for that, you'll need to create a `Geocoder` object, pass in a locale (`Locale.getDefault()`), and call `getFromLocation`. 
