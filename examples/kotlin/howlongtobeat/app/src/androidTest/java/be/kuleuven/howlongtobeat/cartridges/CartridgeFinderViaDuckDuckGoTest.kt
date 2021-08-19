package be.kuleuven.howlongtobeat.cartridges

import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CartridgeFinderViaDuckDuckGoTest {

    private lateinit var repo: CartridgeFinderViaDuckDuckGo

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        repo = CartridgeFinderViaDuckDuckGo(appContext)
        // no need to set the main dispatcher, it's running on the device itself!
    }

    @Test
    fun find_knownCodesForDuckDuckGo_returnsCorrectCartridge() = runBlocking {
        launch(Dispatchers.Main) {
            val smbDeluxe = repo.find("CGB-AHYE-USA")
            assertEquals("super mario bros deluxe", smbDeluxe?.title)
            assertEquals("CGB-AHYE-USA", smbDeluxe?.code)

            val marioGolf = repo.find("cgb-awxp-eur-1")
            assertEquals("mario golf", marioGolf?.title)
            assertEquals("cgb-awxp-eur-1", marioGolf?.code)
        }
        println("launched main dispatcher") // this must be there: Kotlin-to-Java doesn't recognize the retval because of runBlocking.
    }
}