package be.kuleuven.howlongtobeat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.kuleuven.howlongtobeat.model.Game

class GameListAdapter(val items: List<Game>) : RecyclerView.Adapter<GameListAdapter.GameListViewHolder>() {

    inner class GameListViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val currentTodoItem = items[position]
        holder.itemView.apply {
            val checkBoxTodo = findViewById<CheckBox>(R.id.chkTodoDone)
            findViewById<TextView>(R.id.txtTodoTitle).text = currentTodoItem.title

            checkBoxTodo.isChecked = currentTodoItem.isDone
            checkBoxTodo.setOnClickListener {
                currentTodoItem.isDone = checkBoxTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int = items.size
}