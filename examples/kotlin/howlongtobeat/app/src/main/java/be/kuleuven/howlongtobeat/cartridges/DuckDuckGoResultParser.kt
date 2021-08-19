package be.kuleuven.howlongtobeat.cartridges

object DuckDuckGoResultParser {

    private val resultMatcher = """<a rel=".+" class="result__a" href=".+">(.+)</a>""".toRegex()
    private val specialCharsToRemove = listOf(
        "|",
        ".",
        "-",
        "/",
        ",",
        "!",
        "Get information and compare prices of",
        "for Game Boy",
        "Release Information",
        "Nintendo",
        "Game Boy Advance",
        "Game Boy color",
        "Game Boy",
        "GameBoy",
        "Game ",
        "GBC",
        "GBA",
        "VGDb",
        "ebay"
    )

    fun parse(html: String, fromCode: String): Cartridge? {
        // There are bound to be multiple results. Just fetch the first one as an educated guess
        val matched = resultMatcher.find(html) ?: return null

        var match = matched.groupValues[1]
            .lowercase()
            .replace(fromCode.lowercase(), "")

        specialCharsToRemove.forEach {
            match = match.replace(it.lowercase(), "")
        }
        return Cartridge("Unknown", match.trim(), fromCode)
    }
}