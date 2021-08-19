package be.kuleuven.howlongtobeat.hltb

import androidx.test.platform.app.InstrumentationRegistry
import be.kuleuven.howlongtobeat.cartridges.Cartridge
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HLTBClientImplTest {

    private lateinit var client: HLTBClientImpl

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        client = HLTBClientImpl(appContext)
    }

    @Test
    fun find_unknownGame_returnsNull() = runBlocking {
        launch(Dispatchers.Main) {
            val results = client.find(Cartridge("type", "moesjamarramarra tis niet omdat ik wijs dat ge moet kijken he", "invalid"))
            assertNull(results)
        }
        println("Dispatche launched")
    }

    @Test
    fun find_someValidGame_retrievesSomeGamesBasedOnTitle()  = runBlocking {
        launch(Dispatchers.Main) {
            val results = client.find(Cartridge("type", "Gex: Enter the Gecko", "CGB-GEX-666"))
            assertEquals(1, results?.size)
            val gex = results?.single()!!

            assertEquals("Gex Enter the Gecko", gex.title)
            assertEquals("CGB-GEX-666", gex.cartCode)
            assertEquals(9.0, gex.howlong) // that long, huh.
        }
        println("Dispatche launched")
    }

}