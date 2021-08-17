package be.kuleuven.howlongtobeat.cartridges

import junit.framework.Assert.assertEquals
import org.junit.Test

class CartridgeTest {

    @Test
    fun titleReplacesIrrelevantDetailsFromName() {
        val cart = Cartridge("type", "name (irrelevant details please remove thxxx)", "DMG-whatever")
        assertEquals("name", cart.title)
    }

}