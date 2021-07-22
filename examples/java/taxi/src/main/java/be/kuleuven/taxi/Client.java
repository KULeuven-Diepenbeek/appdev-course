package be.kuleuven.taxi;

public class Client {
    private final String name;
    private Address from;
    private Address to;

    public Client(String name, Address from, Address to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }
}
