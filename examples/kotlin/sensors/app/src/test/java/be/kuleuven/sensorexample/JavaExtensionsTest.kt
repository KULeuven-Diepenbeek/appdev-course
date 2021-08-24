package be.kuleuven.sensorexample

import junit.framework.TestCase.assertEquals
import org.junit.Test

class JavaExtensionsTest {

    @Test fun countOccurences_somePresent_returnsAmount() {
        val sentence = "the quick brown fox jumps over the foxy fox"
        assertEquals(3, sentence.countOccurences('x'))
    }

    @Test fun countOccurences_nonePresent_returnsZero() {
        val sentence = "sup dawg"
        assertEquals(0, sentence.countOccurences('x'))
        assertEquals(0, "".countOccurences('x'))
    }
}