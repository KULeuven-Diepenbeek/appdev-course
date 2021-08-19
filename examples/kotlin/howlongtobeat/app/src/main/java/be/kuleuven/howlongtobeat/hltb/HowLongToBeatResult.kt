package be.kuleuven.howlongtobeat.hltb

import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class HowLongToBeatResult(val title: String, val cartCode: String, val howlong: Double, val boxartUrl: String = "") : java.io.Serializable {
    companion object {
        const val RESULT = "HowLongToBeatResult"
        const val SNAPSHOT_URI = "SnapshotUri"
    }

    fun hasBoxart(): Boolean = boxartUrl.startsWith(HLTBClientImpl.DOMAIN)
    fun boxartUrl(): URL = URL(boxartUrl)
    override fun toString(): String = "$title ($howlong hrs)"
}