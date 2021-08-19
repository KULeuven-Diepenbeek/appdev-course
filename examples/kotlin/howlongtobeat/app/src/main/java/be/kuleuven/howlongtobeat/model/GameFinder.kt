package be.kuleuven.howlongtobeat.model

import android.content.Context
import android.graphics.Bitmap
import be.kuleuven.howlongtobeat.ImageRecognizer
import be.kuleuven.howlongtobeat.UnableToFindGameException
import be.kuleuven.howlongtobeat.cartridges.CartridgeFinderViaDuckDuckGo
import be.kuleuven.howlongtobeat.cartridges.CartridgesRepository
import be.kuleuven.howlongtobeat.cartridges.CartridgesRepositoryGekkioFi
import be.kuleuven.howlongtobeat.cartridges.findFirstCartridgeForRepos
import be.kuleuven.howlongtobeat.google.GoogleVisionClient
import be.kuleuven.howlongtobeat.hltb.HLTBClient
import be.kuleuven.howlongtobeat.hltb.HLTBClientImpl
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult

/**
 * This class separates Android-specific logic with our own domain logic.
 * WHY? Because of GameFinderTest and mockability!
 */
class GameFinder(
    private val hltbClient: HLTBClient,
    private val cartRepos: List<CartridgesRepository>,
    private val imageRecognizer: ImageRecognizer) {

    companion object {
        fun default(context: Context): GameFinder {
            // If we fail to find info in the first repo, it falls back to the second one: a (scraped) DuckDuckGo search.
            val cartRepos = listOf(
                CartridgesRepositoryGekkioFi.fromAsset(context),
                CartridgeFinderViaDuckDuckGo(context)
            )
            return GameFinder(HLTBClientImpl(context), cartRepos, GoogleVisionClient())
        }
    }

    suspend fun findGameBasedOnCameraSnap(picToAnalyze: Bitmap, progress: (msg: String) -> Unit): List<HowLongToBeatResult> {
        progress("Recognizing game cart from picture...")
        val cartCode = imageRecognizer.recognizeCartCode(picToAnalyze)
            ?: throw UnableToFindGameException("No cart code in your pic found")

        progress("Found cart code $cartCode\nLooking in DBs for matching game...")
        val foundCart = findFirstCartridgeForRepos(cartCode, cartRepos)
            ?: throw UnableToFindGameException("$cartCode is an unknown game cart.")

        progress("Valid cart code: $cartCode\n Looking in HLTB for ${foundCart.title}...")
        val hltbResults = hltbClient.find(foundCart)
            ?: throw UnableToFindGameException("HLTB does not know ${foundCart.title}")

        return hltbResults
    }
}
