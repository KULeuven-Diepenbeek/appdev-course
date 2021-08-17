package be.kuleuven.howlongtobeat.hltb

import kotlinx.serialization.Serializable

@Serializable
data class HowLongToBeatResult(val title: String, val howlong: Double, val finished: Boolean = false) : java.io.Serializable {
    companion object {
        const val RESULT = "HowLongToBeatResult"
        const val CODE = "CartCode"
    }
}