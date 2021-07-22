package be.kuleuven.taxis

fun main(args: Array<String>) {
    println("Taxi Driver 0.1 Beta 346")

    val taxiJaques = ComplexTaxiFirm()
    val bmw = Car("BMW 5 2.0d")
    val volvo = Car("Volvo V60 D4")
    val monique = Driver("Monique", 23)
    val josianne = Driver("Josianne", 33)

    taxiJaques.buyCar(bmw)
    taxiJaques.buyCar(volvo)
    taxiJaques.hireDriver(monique)
    taxiJaques.hireDriver(josianne)

    val jos = Client(
        "Jos",
        Address("Zeugsteeg", 10, "Leuven"),
        Address("Hoofdstraat", 11, "Brussel")
    )

    taxiJaques.bookClient(jos)
    taxiJaques.bookClientNonFunctional(jos)
    // this will throw an exception: all drivers and cars are taken at the moment
    taxiJaques.bookClient(jos)
}