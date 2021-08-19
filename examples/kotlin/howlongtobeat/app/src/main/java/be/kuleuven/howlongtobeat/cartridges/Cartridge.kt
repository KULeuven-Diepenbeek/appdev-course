package be.kuleuven.howlongtobeat.cartridges

data class Cartridge(val type: String, val name: String, val code: String) {

    companion object {
        val bracketRe = """\(.+\)""".toRegex()
        val UNKNOWN_CART = Cartridge("", "UNKNOWN CART", "DMG-???")
        val KNOWN_CART_PREFIXES = listOf("DMG", "CGB")

        fun isValid(code: String): Boolean =
            code != "" && !code.contains("\n") &&
                    KNOWN_CART_PREFIXES.any { code.startsWith("$it-") }
    }

    val title = bracketRe.replace(name, "").replace("-", "").trim()
}