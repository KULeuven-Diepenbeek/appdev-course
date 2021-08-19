package be.kuleuven.howlongtobeat.cartridges


import junit.framework.TestCase.*
import org.junit.Test

class CartridgeTest {

    @Test
    fun isValid_OnlyCartridgeCodeItself_IsValid() {
        assertTrue(Cartridge.isValid("DMG-MQ-EUR"))
        assertTrue(Cartridge.isValid("DMG-MQ-USA-1"))
        assertTrue(Cartridge.isValid("CGB-ABC-DEF-WHATEVER"))
    }

    @Test
    fun isValid_Empty_IsNotValid() {
        assertFalse(Cartridge.isValid(""))
    }

    @Test
    fun isValid_CatridgeCodeWithOtherJunkOnNewLine_IsNotValid() {
        assertFalse(Cartridge.isValid("DMG-MQ-EUR\nMADE IN JAPAN"))
    }

    @Test
    fun titleReplacesIrrelevantDetailsFromName() {
        val cart = Cartridge("type", "name (irrelevant details please remove thxxx)", "DMG-whatever")
        assertEquals("name", cart.title)
    }

}