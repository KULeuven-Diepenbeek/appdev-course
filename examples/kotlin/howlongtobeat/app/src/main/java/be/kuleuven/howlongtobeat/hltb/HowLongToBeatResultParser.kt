package be.kuleuven.howlongtobeat.hltb

object HowLongToBeatResultParser {

    private val titleMatcher = """<a class=".+" title="(.+)" href=""".toRegex()
    private val hourMatcher = """<div class=".+">(.+) Hours""".toRegex()

    fun parse(html: String): List<Game> {
        val result = arrayListOf<Game>()
        val rows = html.split("\n")
        for(i in 0..rows.size - 1) {
            val matched = titleMatcher.find(rows[i])
            if(matched != null) {
                val (title) = matched.destructured
                val hour = parseHoursFromRow(i, rows)

                result.add(Game(title, hour))
            }
        }

        return result
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