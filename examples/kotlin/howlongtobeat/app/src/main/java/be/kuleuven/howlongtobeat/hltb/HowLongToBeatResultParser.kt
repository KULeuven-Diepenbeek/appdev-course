package be.kuleuven.howlongtobeat.hltb

object HowLongToBeatResultParser {

    private val titleMatcher = """<a class=".+" title="(.+)" href=""".toRegex()
    private val hourMatcher = """<div class=".+">(.+) Hours""".toRegex()
    private val boxArtMatcher = """<img alt=".+" src="(.+)"""".toRegex()

    fun parse(html: String): List<HowLongToBeatResult> {
        val result = arrayListOf<HowLongToBeatResult>()
        val rows = html.split("\n")
        for(i in 0..rows.size - 1) {
            val matched = titleMatcher.find(rows[i])
            if(matched != null) {
                val (title) = matched.destructured
                val hour = parseHoursFromRow(i, rows)
                val boxart = parseBoxArtFromRow(i, rows)

                result.add(HowLongToBeatResult(title, hour, boxart))
            }
        }

        return result
    }

    private fun parseBoxArtFromRow(row: Int, rows: List<String>): String {
        // three rows up, there should be an image tag with the box art
        if(row - 3 >= 0) {
            val matchedBoxArt = boxArtMatcher.find(rows[row - 3])
            if(matchedBoxArt != null) {
                return HLTBClient.DOMAIN + matchedBoxArt.groupValues[1]
            }
        }
        return ""
    }

    private fun parseHoursFromRow(row: Int, rows: List<String>): Double {
        var hour = -1.0
        // two rows down, there should be a <div class="search_list_tidbit center time_100">6&#189; Hours </div>
        if (row + 3 <= rows.size) {
            val matchedHour = hourMatcher.find(rows[row + 3])
            if (matchedHour != null) {
                hour = matchedHour.groupValues[1].replace("&#189;", ".5").toDouble()
            }
        }
        return hour
    }
}