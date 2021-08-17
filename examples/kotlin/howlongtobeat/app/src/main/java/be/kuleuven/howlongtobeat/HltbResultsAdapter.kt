package be.kuleuven.howlongtobeat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult

class HltbResultsAdapter(private val items: List<HowLongToBeatResult>) : RecyclerView.Adapter<HltbResultsAdapter.HltbResultsViewHolder>() {

    inner class HltbResultsViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HltbResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hltbresult, parent, false)
        return HltbResultsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HltbResultsViewHolder, position: Int) {
        val currentResult = items[position]
        holder.itemView.apply {
            val txtHltbItemResult = findViewById<TextView>(R.id.txtHltbItemResult)
            txtHltbItemResult.text = currentResult.title
        }
    }

    override fun getItemCount(): Int = items.size
}