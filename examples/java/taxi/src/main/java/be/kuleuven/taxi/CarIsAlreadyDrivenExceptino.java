package be.kuleuven.taxi;

public class CarIsAlreadyDrivenExceptino extends RuntimeException {
    public CarIsAlreadyDrivenExceptino(String message) {
        super(message);
    }
}
