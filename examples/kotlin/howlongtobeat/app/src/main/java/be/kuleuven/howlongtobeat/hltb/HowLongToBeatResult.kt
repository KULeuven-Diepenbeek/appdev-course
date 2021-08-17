package be.kuleuven.howlongtobeat.hltb

import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class HowLongToBeatResult(val title: String, val howlong: Double, val boxart: String = "") : java.io.Serializable {
    companion object {
        const val RESULT = "HowLongToBeatResult"
    }

    fun hasBoxart(): Boolean = boxart.startsWith(HLTBClient.DOMAIN)
    fun boxartUrl(): URL = URL(boxart)
    override fun toString(): String = "$title ($howlong hrs)"
}