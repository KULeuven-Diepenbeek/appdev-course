package be.kuleuven.taxis

class Car(type: String) {
    val type: String = type
    var driving: Boolean = false
        private set

    fun startEngine() {
        driving = true
    }
    fun stop() {
        driving = false
    }
}