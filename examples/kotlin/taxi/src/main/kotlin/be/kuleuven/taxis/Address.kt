package be.kuleuven.taxis

class Address(street: String, number: Int, city: String) {
    val street: String = street
    val number: Int = number
    val city: String = city

    override fun toString(): String {
        return "$street #$number, at $city"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (street != other.street) return false
        if (number != other.number) return false
        if (city != other.city) return false

        return true
    }

    override fun hashCode(): Int {
        var result = street.hashCode()
        result = 31 * result + number
        result = 31 * result + city.hashCode()
        return result
    }

}