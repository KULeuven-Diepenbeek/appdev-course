package be.kuleuven.howlongtobeat.model

import be.kuleuven.howlongtobeat.ImageRecognizer
import be.kuleuven.howlongtobeat.UnableToFindGameException
import be.kuleuven.howlongtobeat.cartridges.Cartridge
import be.kuleuven.howlongtobeat.cartridges.CartridgesRepository
import be.kuleuven.howlongtobeat.hltb.HLTBClient
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors

class GameFinderTest {

    private lateinit var finder: GameFinder
    @MockK
    private lateinit var hltbClient: HLTBClient
    @MockK
    private lateinit var imageRecognizer: ImageRecognizer
    @MockK
    private lateinit var cartridgesRepository: CartridgesRepository

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before fun setUp() {
        MockKAnnotations.init(this)
        finder = GameFinder(hltbClient, listOf(cartridgesRepository), imageRecognizer)
        Dispatchers.setMain(dispatcher)
    }

    @After fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.close()
    }

    @Test fun findGameBasedOnCameraSnap_UnrecognizableAccordingToGoogleVision_fails() = runBlocking {
        coEvery { imageRecognizer.recognizeCartCode(any()) } returns null

        try {
            finder.findGameBasedOnCameraSnap(mockk()) {
            }
            Assert.fail("Expected exception to occur")
        } catch(expected: UnableToFindGameException) {
            Assert.assertEquals("No cart code in your pic found", expected.message)
        }
    }

    @Test fun findGameBasedOnCameraSnap_UnknownCartridgeAccordingToDBs_fails() = runBlocking {
        coEvery { imageRecognizer.recognizeCartCode(any()) } returns "DMG-MQ-EUR"
        coEvery { cartridgesRepository.find("DMG-MQ-EUR") } returns null

        try {
            finder.findGameBasedOnCameraSnap(mockk()) {
            }
            Assert.fail("Expected exception to occur")
        } catch(expected: UnableToFindGameException) {
            Assert.assertEquals("DMG-MQ-EUR is an unknown game cart.", expected.message)
        }
    }

    @Test fun findGameBasedOnCameraSnap_UnknownCartridgeAccordingToFirstDBButSecondFindsIt_returnsHltbResults() = runBlocking {
        val secondCartridgeDb = mockk<CartridgesRepository>()
        val cart = Cartridge("type", "Mario Land 356", "DMG-MQ-EUR")

        coEvery { imageRecognizer.recognizeCartCode(any()) } returns cart.code
        coEvery { cartridgesRepository.find(cart.code) } returns null
        coEvery { secondCartridgeDb.find(cart.code) } returns cart
        coEvery { hltbClient.find(cart) } returns listOf(HowLongToBeatResult(cart.title, cart.code, 34.5))
        finder = GameFinder(hltbClient, listOf(cartridgesRepository, secondCartridgeDb), imageRecognizer)

        val foundGames = finder.findGameBasedOnCameraSnap(mockk()) {
        }

        assertEquals(1, foundGames.size)
        assertEquals(34.5, foundGames.single().howlong)
    }

    @Test fun findGameBasedOnCameraSnap_UnknownGameAccordingToHLTB_fails() = runBlocking {
        val cart = Cartridge("type", "Mario Land 356", "DMG-MQ-EUR")

        coEvery { imageRecognizer.recognizeCartCode(any()) } returns cart.code
        coEvery { cartridgesRepository.find(cart.code) } returns cart
        coEvery { hltbClient.find(cart) } returns null

        try {
            finder.findGameBasedOnCameraSnap(mockk()) {
            }
            Assert.fail("Expected exception to occur")
        } catch(expected: UnableToFindGameException) {
            Assert.assertEquals("HLTB does not know Mario Land 356", expected.message)
        }
    }

    @Test fun findGameBasedOnCameraSnap_validGame_returnsHltbResults()  = runBlocking {
        val cart = Cartridge("type", "Mario Land 356", "DMG-MQ-EUR")

        coEvery { imageRecognizer.recognizeCartCode(any()) } returns cart.code
        coEvery { cartridgesRepository.find(cart.code) } returns cart
        coEvery { hltbClient.find(cart) } returns listOf(HowLongToBeatResult(cart.title, cart.code, 34.5))

        val foundGames = finder.findGameBasedOnCameraSnap(mockk()) {
        }

        assertEquals(1, foundGames.size)
        assertEquals(34.5, foundGames.single().howlong)

    }
}