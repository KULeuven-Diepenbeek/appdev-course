package be.kuleuven.taxi;

public class Main {

    public static void main(String[] args) {
        System.out.println("Taxi Firm Java impl version e5346456!! GOGO");

        // note thar "var" also works here thanks to Java 10's type inference
        // this has nothing to do with Kotlin's "val" ("final") or "var", however.
        var taxiJaques = new TaxiFirm();

        var bmw = new Car("BMW 5 2.0d");
        var volvo = new Car("Volvo V60 D4");
        var monique = new Driver("Monique", 23);
        var josianne = new Driver("Josianne", 33);

        taxiJaques.buyCar(bmw);
        taxiJaques.buyCar(volvo);
        taxiJaques.hireDriver(monique);
        taxiJaques.hireDriver(josianne);

        var jos = new Client(
                "Jos",
                new Address("Zeugsteeg", 10, "Leuven"),
                new Address("Hoofdstraat", 11, "Brussel")
        );

        taxiJaques.bookClient(jos);
        taxiJaques.bookClientNonFunctional(jos);
        // this will throw an exception: all drivers and cars are taken at the moment
        taxiJaques.bookClient(jos);
    }
}
