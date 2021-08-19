package be.kuleuven.howlongtobeat.car

import be.kuleuven.howlongtobeat.cartridges.CartridgesRepositoryGekkioFi
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors


class CartridgesRepositoryGekkioFiTest {

    private lateinit var repo: CartridgesRepositoryGekkioFi

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setUp() {
        repo = javaClass.getResource("/cartridges.csv").openStream().use {
            CartridgesRepositoryGekkioFi(it)
        }
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.close()
    }

    @Test
    fun noSingleCodeShouldBeEmpty() {
        repo.cartridges.forEach {
            assertTrue("Code of ${it.title} should be filled in but is '${it.code}'", it.code.length > 4)
        }
    }

    @Test
    fun findReturnsCartridgeObjectOfCode() = runBlocking {
        launch(Dispatchers.Main) {
            val smbDeluxe = repo.find("CGB-AHYE-USA")
            assertTrue(smbDeluxe!!.name.contains("Super Mario Bros. Deluxe"))
        }
        println("done")
    }

    @Test
    fun readsWholeCsvFileAsListOfCartridges() {
        assertFalse(repo.cartridges.isEmpty())
        val smbDeluxe = repo.cartridges.find {
            it.code == "CGB-AHYE-USA"
        }
        assertTrue(smbDeluxe != null)
        assertTrue(smbDeluxe!!.name.contains("Super Mario Bros. Deluxe"))
        assertEquals("Super Mario Bros. Deluxe", smbDeluxe.title)
    }

}