package be.kuleuven.taxi;

public class Car {
    private final String type;
    private boolean driving;

    public String getType() {
        return type;
    }

    public boolean isDriving() {
        return driving;
    }

    public void startEngine() {
        driving = true;
    }

    public void stop() {
        driving = false;
    }

    public Car(String type) {
        this.type = type;
        driving = false;
    }
}
