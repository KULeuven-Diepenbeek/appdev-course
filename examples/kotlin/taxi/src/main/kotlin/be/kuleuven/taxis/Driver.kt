package be.kuleuven.taxis

class Driver(name: String, skillLevel: Int) {
    val name: String = name
    val skill: Int = skillLevel

    fun drive(client: Client): Driver {
        working = true
        return this
    }

    fun with(car: Car) {
        if(car.driving) {
            throw CarIsAlreadyDrivenException("Sorry boss, I can't, my colleague is already driving this car!")
        }
        car.startEngine()
    }

    var working: Boolean = false
}