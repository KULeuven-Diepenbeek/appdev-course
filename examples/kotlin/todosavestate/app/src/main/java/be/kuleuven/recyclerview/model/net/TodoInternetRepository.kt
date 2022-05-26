package be.kuleuven.recyclerview.model.net

import android.content.Context
import be.kuleuven.recyclerview.model.Todo
import be.kuleuven.recyclerview.model.TodoRepository
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class TodoInternetRepository(val context: Context, val onResponseFetched: (List<Todo>) -> Unit) : TodoRepository {

    private fun howLongToBeatRequestObj(): JSONObject {
        val obj = JSONObject().apply {
            put("queryString", "super mario land")
            put("t", "games")
            put("sorthead", "popular")
            put("sortd", "0")
            put("plat", "")
            put("length_type", "main")
            put("length_min", "")
            put("length_max", "")
            put("detail", "0")
        }

        return obj
    }

    // Inspired by https://www.npmjs.com/package/howlongtobeat
    // The API is abysmal, but hey, it works...
    class HLTBRequest(val requestObj: JSONObject, responseListener: Response.Listener<String>) :
        StringRequest(Method.POST, "https://howlongtobeat.com/search_results.php?page=1", responseListener,
        Response.ErrorListener {
            println("Something went wrong: ${it.message}")
        }) {
        override fun getBodyContentType(): String {
            return "application/x-www-form-urlencoded"
        }

        override fun getParams(): MutableMap<String, String> {
            return hashMapOf(
                "queryString" to "super mario land",
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
            return hashMapOf(
                "User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:100.0) Gecko/20100101 Firefox/100.0",
                "Referer" to "https://howlongtobeat.com/",
            )
        }
    }

    override fun load(): List<Todo> {
        val queue = Volley.newRequestQueue(context)
        val req = HLTBRequest(howLongToBeatRequestObj()) {
            println("Raw HTML Response: $it")
            onResponseFetched(HowLongToBeatResultParser.parse(it))
        }
        queue.add(req)
        // Remember, this is ASYNC, so we can't return anything here (yet), that's what onResponse() is for...
        // don't forget to add android.permission.INTERNET to your manifest!
        return arrayListOf()
    }

    override fun save(items: List<Todo>) {
        println("Not implemented!")
    }
}