package be.kuleuven.taxis

class SimpleTaxiFirm(driver: Driver, car: Car) {
    // note that the type is not needed, Kotlin will figure it out.
    // note that "this." is not needed
    val driver = driver
    val car = car

    fun bookClient(client: Client) {
        driver.drive(client).with(car)
    }
}