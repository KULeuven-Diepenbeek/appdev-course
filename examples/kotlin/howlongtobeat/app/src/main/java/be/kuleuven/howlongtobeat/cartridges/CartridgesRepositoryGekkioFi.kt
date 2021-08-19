package be.kuleuven.howlongtobeat.cartridges

import android.content.Context
import java.io.InputStream
import kotlin.coroutines.suspendCoroutine

/**
 * Reads cartridges.csv export from https://gbhwdb.gekkio.fi/cartridges/
 * Headers: type,name,title,slug,url,contributor,code,...
 * E.g.: DMG-MQE-2,"Super Mario Land 2 - 6 Golden Coins (USA, Europe) (Rev 2)",Entry #1,creeps-1,https://gbhwdb.gekkio.fi/cartridges/DMG-MQE-2/creeps-1.html,creeps,DMG-MQ-USA-1,
 */
class CartridgesRepositoryGekkioFi(csvStream: InputStream) : CartridgesRepository {

    companion object {
        fun fromAsset(context: Context): CartridgesRepositoryGekkioFi =
            CartridgesRepositoryGekkioFi(context.assets.open("cartridges.csv"))
    }

    val cartridges: List<Cartridge> =
        csvStream.bufferedReader().use {
            it.readLines()
        }.filter {
            !it.startsWith("type,name,title,")
        }.map {
            val data = it.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
            Cartridge(data[0].replace("\"", "").trim(), data[1].replace("\"", "").trim(), data[6].replace("\"", "").trim())
        }.filter { it.code != "" }
            .toList()

    override suspend fun find(code: String?): Cartridge? = suspendCoroutine { cont ->
        if(code == null) {
            cont.resumeWith(Result.success(null))
        } else {
            val possiblyFound = cartridges.filter {
                it.code == code || it.code.contains(code) || code.contains(it.code)
            }.firstOrNull()
            cont.resumeWith(Result.success(possiblyFound))
        }
    }

}
