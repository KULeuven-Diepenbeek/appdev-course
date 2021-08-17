package be.kuleuven.howlongtobeat.car

import be.kuleuven.howlongtobeat.cartridges.CartridgesRepository
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test



class CartridgesRepositoryTest {

    private lateinit var repo: CartridgesRepository

    @Before
    fun setUp() {
        repo = javaClass.getResource("/cartridges.csv").openStream().use {
            CartridgesRepository(it)
        }
    }

    @Test
    fun noSingleCodeShouldBeEmpty() {
        repo.cartridges.forEach {
            assertTrue("Code of ${it.title} should be filled in but is '${it.code}'", it.code.length > 4)
        }
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