package be.kuleuven.taxi;

public class WeNeedToReferThisClientException extends RuntimeException {
    public WeNeedToReferThisClientException(String message) {
        super(message);
    }
}
