package be.kuleuven.taxis

class Car(type: String) {
    val type: String = type
    var driving: Boolean = false
        private set

    // remember, "void" is not needed if nothing is returned.
    // also, "public" is the default here!
    fun startEngine() {
        driving = true
    }
    fun stop() {
        driving = false
    }
}