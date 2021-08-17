package be.kuleuven.howlongtobeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.howlongtobeat.databinding.FragmentTodolistBinding
import be.kuleuven.howlongtobeat.hltb.Game
import be.kuleuven.howlongtobeat.hltb.HLTBClient
import be.kuleuven.howlongtobeat.model.Todo

class TodoFragment : Fragment(R.layout.fragment_todolist) {

    private val todoList = arrayListOf<Todo>()
    private lateinit var hltbClient: HLTBClient

    private lateinit var binding: FragmentTodolistBinding
    private lateinit var main: MainActivity
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodolistBinding.inflate(layoutInflater)
        main = activity as MainActivity


        adapter = TodoAdapter(todoList)
        binding.rvwTodo.adapter = adapter
        // If we don't supply a layout manager, the recyclerview will not be displayed
        // there are three options here: a simple LinearLayoutManager (1-dimensional), a GridLayoutManager (2D) or a StaggeredGridLayoutManager
        binding.rvwTodo.layoutManager = LinearLayoutManager(this.context)

        binding.btnAddTodo.setOnClickListener(this::addNewTotoItem)
        return binding.root
    }

    fun onHltbGamesRetrieved(games: List<Game>) {
        todoList.clear()
        todoList.addAll(games.map { Todo("${it.title} (${it.howlong})", false) })
        adapter.notifyDataSetChanged()
    }

    private fun addNewTotoItem(it: View) {
        val newTodoTitle = binding.edtTodo.text.toString()
        // this will not automatically updat the view!
        todoList.add(Todo(newTodoTitle, false))
        adapter.notifyItemInserted(todoList.size - 1)
        // adapter.notifyDatasetChanged() also works but will update EVERYTHING, which is not too efficient.
        binding.edtTodo.text.clear()
        binding.edtTodo.clearFocus()

        main.hideKeyboard(it)
    }

    fun clearAllItems() {
        todoList.clear()
        adapter.notifyDataSetChanged()
    }

    fun clearLatestItem() {
        if(todoList.size >= 1) {
            todoList.removeAt(todoList.size - 1)
            adapter.notifyItemRemoved(todoList.size - 1)
        }
    }

    fun resetItems() {
        todoList.clear()
        todoList.addAll(Todo.defaults())
        adapter.notifyDataSetChanged()
    }

}