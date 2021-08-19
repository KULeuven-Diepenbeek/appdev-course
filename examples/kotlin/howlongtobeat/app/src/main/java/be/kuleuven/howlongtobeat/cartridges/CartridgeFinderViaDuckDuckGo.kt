package be.kuleuven.howlongtobeat.cartridges

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.coroutines.suspendCoroutine

class CartridgeFinderViaDuckDuckGo(private val context: Context) : CartridgesRepository {

    class CartridgeFinderViaDuckDuckGoRequest(query: String, responseListener: Response.Listener<String>) :
        StringRequest(
            Method.GET, "https://html.duckduckgo.com/html/?q=${query}", responseListener,
            Response.ErrorListener {
                println("Something went wrong: ${it.message}")
            }) {

        override fun getHeaders(): MutableMap<String, String> {
            return hashMapOf("User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:90.0) Gecko/20100101 Firefox/90.0")
        }
    }

    override suspend fun find(code: String?): Cartridge? = suspendCoroutine { cont ->
        if(code == null) {
            cont.resumeWith(Result.success(null))
        } else {
            val queue = Volley.newRequestQueue(context)
            queue.add(CartridgeFinderViaDuckDuckGoRequest(code) { html ->
                cont.resumeWith(Result.success(DuckDuckGoResultParser.parse(html, code)))
            })
        }
    }
}