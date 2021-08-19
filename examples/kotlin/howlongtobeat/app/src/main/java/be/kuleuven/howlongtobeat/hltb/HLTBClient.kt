package be.kuleuven.howlongtobeat.hltb

import android.content.Context
import be.kuleuven.howlongtobeat.cartridges.Cartridge
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.coroutines.suspendCoroutine

class HLTBClient(val context: Context) {

    companion object {
        const val DOMAIN = "https://howlongtobeat.com"
    }

    // Inspired by https://www.npmjs.com/package/howlongtobeat
    // The API is abysmal, but hey, it works...
    class HLTBRequest(val query: String, responseListener: Response.Listener<String>) :
        StringRequest(Method.POST, "$DOMAIN/search_results.php?page=1", responseListener,
        Response.ErrorListener {
            println("Something went wrong: ${it.message}")
        }) {
        override fun getBodyContentType(): String {
            return "application/x-www-form-urlencoded"
        }

        override fun getParams(): MutableMap<String, String> {
            return hashMapOf(
                "queryString" to query,
                "t" to "games",
                "sorthead" to "popular",
                "sortd" to "0",
                "plat" to "",
                "length_type" to "main",
                "length_min" to "",
                "length_max" to "",
                "detail" to "0"
            )
        }

        override fun getHeaders(): MutableMap<String, String> {
            return hashMapOf("User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:90.0) Gecko/20100101 Firefox/90.0")
        }
    }

    suspend fun find(cart: Cartridge): List<HowLongToBeatResult>? = suspendCoroutine { cont ->
        val queue = Volley.newRequestQueue(context)
        val req = HLTBRequest(cart.title) {
            val hltbResults = HowLongToBeatResultParser.parse(it, cart)
            cont.resumeWith(Result.success(hltbResults))
        }
        queue.add(req)
    }
}