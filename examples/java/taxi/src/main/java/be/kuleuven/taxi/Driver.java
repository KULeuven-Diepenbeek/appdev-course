package be.kuleuven.taxi;

public class Driver {
    private final String name;
    private final int skillLevel;
    private boolean working = false;

    public String getName() {
        return name;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public Driver drive(Client client) {
        working = true;
        return this;
    }

    public boolean isWorking() {
        return working;
    }

    public void with(Car car) {
        if(car.isDriving()) {
            throw new CarIsAlreadyDrivenExceptino("Sorry boss, I can't, my colleague is already driving this car!");
        }
        car.startEngine();
    }

    public Driver(String name, int skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }
}
