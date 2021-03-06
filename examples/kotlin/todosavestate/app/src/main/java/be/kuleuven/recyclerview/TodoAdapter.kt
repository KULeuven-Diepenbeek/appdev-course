package be.kuleuven.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.kuleuven.recyclerview.model.Todo

class TodoAdapter(val items: List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    // this creates the needed ViewHolder class that links our layout XML to our viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        // don't forget to set attachToRoot to false, otherwise it will crash!
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // bind the data to our items: set the todo text view text and checked state accordingly
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