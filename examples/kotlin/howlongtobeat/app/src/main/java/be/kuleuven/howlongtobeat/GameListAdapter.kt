package be.kuleuven.howlongtobeat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import be.kuleuven.howlongtobeat.model.Game

class GameListAdapter(val items: List<Game>) : RecyclerView.Adapter<GameListAdapter.GameListViewHolder>() {

    private lateinit var parentFragment: GameListFragment

    inner class GameListViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        parentFragment = parent.findFragment()
        return GameListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val game = items[position]
        holder.itemView.apply {
            setOnLongClickListener {
                parentFragment.selectGame(game)
                true
            }

            val chkFinished = findViewById<CheckBox>(R.id.chkGameFinished)
            findViewById<TextView>(R.id.txtTodoTitle).text = game.title

            chkFinished.isChecked = game.finished
            chkFinished.setOnClickListener {
                game.finished = chkFinished.isChecked
            }
        }
    }

    override fun getItemCount(): Int = items.size
}