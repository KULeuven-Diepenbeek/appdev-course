package be.kuleuven.howlongtobeat.hltb

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class HLTBClient(val context: Context) {

    // Inspired by https://www.npmjs.com/package/howlongtobeat
    // The API is abysmal, but hey, it works...
    class HLTBRequest(val query: String, responseListener: Response.Listener<String>) :
        StringRequest(Method.POST, "https://howlongtobeat.com/search_results.php?page=1", responseListener,
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

    fun find(query: String, onResponseFetched: (List<HowLongToBeatResult>) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val req = HLTBRequest(query) {
            onResponseFetched(HowLongToBeatResultParser.parse(it))
        }
        queue.add(req)
    }
}