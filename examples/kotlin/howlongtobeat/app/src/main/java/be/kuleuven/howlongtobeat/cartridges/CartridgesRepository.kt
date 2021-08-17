package be.kuleuven.howlongtobeat.cartridges

import android.content.Context
import java.io.InputStream

/**
 * Reads cartridges.csv export from https://gbhwdb.gekkio.fi/cartridges/
 * Headers: type,name,title,slug,url,contributor,code,...
 * E.g.: DMG-MQE-2,"Super Mario Land 2 - 6 Golden Coins (USA, Europe) (Rev 2)",Entry #1,creeps-1,https://gbhwdb.gekkio.fi/cartridges/DMG-MQE-2/creeps-1.html,creeps,DMG-MQ-USA-1,
 */
class CartridgesRepository(csvStream: InputStream) {

    companion object {
        fun fromAsset(context: Context): CartridgesRepository =
            CartridgesRepository(context.assets.open("cartridges.csv"))
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

    fun find(code: String?): Cartridge {
        if(code == null) return Cartridge.UNKNOWN_CART
        val possiblyFound = cartridges.filter {
            it.code == code || it.code.contains(code) || code.contains(it.code)
        }.firstOrNull()

        return possiblyFound ?: Cartridge.UNKNOWN_CART
    }

}
