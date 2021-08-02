package be.kuleuven.dates

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PeriodTests {

    @Test
    fun `Given overlapping periods When overlaptMet Then true`() {
        val jandec19 = Periode(
            LocalDate.of(2019, 1, 1),
            LocalDate.of(2019, 12, 31)
        )
        val maartnov19 = Periode(
            LocalDate.of(2019, 3, 1),
            LocalDate.of(2019, 11, 30)
        )
        assertTrue { jandec19.overlaptMet(maartnov19) }

        // you could also use HamCrest -- but since "is" is a reserved keyword, use backticks or import it as uppercase Is
        assertThat(jandec19.overlaptMet(maartnov19), `is`(true))
    }

    // TODO fixme! Implementation is not correct :-) That's up to the reader!
    @Test
    fun `Given non-overlapping periods When overlaptMet Then false`() {
        val jandec19 = Periode(
            LocalDate.of(2019, 1, 1),
            LocalDate.of(2019, 12, 31)
        )
        val maartnov20 = Periode(
            LocalDate.of(2020, 3, 1),
            LocalDate.of(2020, 11, 30)
        )
        assertFalse { jandec19.overlaptMet(maartnov20) }
    }


}
