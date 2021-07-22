package be.kuleuven.taxi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaxiFirm {
    // remember that List<> is an interface. This is the de-facto way to do it in Java.
    // We indeed have taught you in INF1 to directly use ArrayList<> to simplify things.
    private final List<Car> carpark;
    private final List<Driver> drivers;

    public TaxiFirm() {
        // the implementation. It MUST be placed in the constructor or above since it's final.
        this.carpark = new ArrayList<>();
        this.drivers = new ArrayList<>();
    }

    public void buyCar(Car car) {
        carpark.add(car);
    }

    public void hireDriver(Driver driver) {
        drivers.add(driver);
    }

    public void bookClient(Client client) {
        // This is relatively new in Java, you can also do "functional" things here using the streaming API.
        // Don't worry, you don't need to know this, we're just showing off here.
        // We could have added "var" but want to show you the return type.
        Optional<Car> availableCar = carpark.stream().filter(car -> !car.isDriving()).findFirst();
        Optional<Driver> availableDriver = drivers.stream().filter(driver -> !driver.isWorking()).findFirst();

        if(availableCar.isEmpty() || availableDriver.isEmpty()) {
            throw new WeNeedToReferThisClientException("Sorry " + client.getName() + ", no cars/drivers available, we'll have to refer you to another taxi firm!");
        }

        // we need to unwrap the optional value here. In Kotlin, 'Optional<Type>' is just 'Type?', and unwrapping is just '!!'.
        driveClient(client, availableDriver.get(), availableCar.get());
    }

    public void bookClientNonFunctional(Client client) {
        // in Kotlin's traditional for, "var" is omitted
        // remember, in Kotlin, for(int i = 0; i < ... does NOT exist!
        Car availableCar = null;
        for(var car : carpark) {
            if(!car.isDriving()) {
                availableCar = car;
                break; // optional
            }
        }
        Driver availableDriver = null;
        for(var driver : drivers) {
            if(!driver.isWorking()) {
                availableDriver = driver;
            }
        }

        if(availableCar == null || availableDriver == null) {
            throw new WeNeedToReferThisClientException("Sorry " + client.getName() + ", no cars/drivers available, we'll have to refer you to another taxi firm!");
        }

        driveClient(client, availableDriver, availableCar);
    }

    private void driveClient(Client client, Driver driver, Car car) {
        System.out.println("All right " + client.getName() + ", " + driver.getName() + " will drive you in the " + car.getType() + ", enjoy!");
        driver.drive(client).with(car);
    }
}
