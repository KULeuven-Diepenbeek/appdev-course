package be.kuleuven.howlongtobeat

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HltbResultsAdapter(private val items: List<HowLongToBeatResult>) : RecyclerView.Adapter<HltbResultsAdapter.HltbResultsViewHolder>() {

    inner class HltbResultsViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    private lateinit var parentFragment: HltbResultsFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HltbResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hltbresult, parent, false)
        parentFragment = parent.findFragment()
        return HltbResultsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HltbResultsViewHolder, position: Int) {
        val itm = items[position]

        holder.itemView.apply {
            setOnClickListener {
                parentFragment.addResultToGameLibrary(itm)
            }

            findViewById<TextView>(R.id.txtHltbItemResult).text = itm.toString()
            val boxArtView = findViewById<ImageView>(R.id.imgHltbItemResult)

            if(itm.hasBoxart()) {
                MainScope().launch{
                    var art: Bitmap? = null
                    withContext(Dispatchers.IO) {
                        art = itm.boxartUrl().downloadAsImage()
                    }
                    withContext(Dispatchers.Main) {
                        boxArtView.setImageBitmap(art)
                    }
                }
            } else {
                boxArtView.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int = items.size
}