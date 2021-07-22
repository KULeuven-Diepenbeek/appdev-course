package be.kuleuven.taxis

class ComplexTaxiFirm {
    // this is an interface. mutableListOf() returns the "default implementation", which is likely to be an ArrayList<>.
    var carpark: MutableList<Car> = mutableListOf()
    var drivers: MutableList<Driver> = mutableListOf()

    fun buyCar(car: Car) {
        carpark.add(car)
    }

    fun hireDriver(driver: Driver) {
        drivers.add(driver)
    }

    fun bookClient(client: Client) {
        // this is the functional way to filter a list, you should recognize this from Python
        val availableCar = carpark.filter { car -> !car.driving }.firstOrNull()
        val availableDriver = drivers.filter { driver -> !driver.working }.firstOrNull()

        if(availableCar == null || availableDriver == null) {
            throw WeNeedToReferThisClientException("Sorry ${client.name}, no cars/drivers available, we'll have to refer you to another taxi company")
        }

        driveCLient(client, availableDriver, availableCar)
    }

    fun bookClientNonFunctional(client: Client) {
        // this is the non-functional way you might be used to from Java
        // note we have to use vars and not constants (vals) as they get assigned inside the loop
        var availableCar: Car? = null
        carpark.forEach {
            if(!it.driving) // it is automatically available in the forEach loop
                availableCar = it
        }
        var availableDriver: Driver? = null
        // another much used way. not the absence of "var" in the for loop.
        for(driver in drivers) {
            if(!driver.working)
                availableDriver = driver
        }

        if(availableCar == null || availableDriver == null) {
            throw WeNeedToReferThisClientException("Sorry ${client.name}, no cars/drivers available, we'll have to refer you to another taxi company")
        }

        // double bangs denote it won't ever be null and will be converted
        driveCLient(client, availableDriver!!, availableCar!!)
    }

    private fun driveCLient(
        client: Client,
        availableDriver: Driver,
        availableCar: Car
    ) {
        println("All right ${client.name}, ${availableDriver.name} will drive you in the ${availableCar.type}, enjoy!")
        availableDriver.drive(client).with(availableCar)
    }

}