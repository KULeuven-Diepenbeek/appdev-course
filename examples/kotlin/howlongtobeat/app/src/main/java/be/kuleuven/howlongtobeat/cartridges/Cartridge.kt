package be.kuleuven.howlongtobeat.cartridges

data class Cartridge(val type: String, val name: String, val code: String) {

    companion object {
        val bracketRe = """\(.+\)""".toRegex()
        val UNKNOWN_CART = Cartridge("", "UNKNOWN CART", "DMG-???")

        fun isValid(code: String): Boolean =  code.startsWith("DMG-") || code.startsWith("CGB-")
    }

    val title = bracketRe.replace(name, "").replace("-", "").trim()
}