package be.kuleuven.dates

import java.time.LocalDate

data class Periode(val startDatum: LocalDate, val eindDatum: LocalDate) {
    fun overlaptMet(ander: Periode): Boolean {
        return startDatum.isAfter(ander.startDatum) &&
                eindDatum.isBefore(ander.eindDatum)
    }
}
