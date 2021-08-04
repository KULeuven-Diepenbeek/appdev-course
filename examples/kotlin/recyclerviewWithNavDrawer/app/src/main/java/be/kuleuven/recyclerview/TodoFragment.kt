package be.kuleuven.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.recyclerview.databinding.FragmentTodolistBinding
import be.kuleuven.recyclerview.model.Todo

class TodoFragment : Fragment(R.layout.fragment_todolist) {

    private lateinit var binding: FragmentTodolistBinding
    private val todoList = sampleTodoList()
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

        binding.btnAddTodo.setOnClickListener {
            val newTodoTitle = binding.edtTodo.text.toString()
            // this will not automatically updat the view!
            todoList.add(Todo(newTodoTitle, false))
            adapter.notifyItemInserted(todoList.size - 1)
            // adapter.notifyDatasetChanged() also works but will update EVERYTHING, which is not too efficient.
            binding.edtTodo.text.clear()
            binding.edtTodo.clearFocus()

            main.hideKeyboard(it)
        }
        return binding.root
    }

    private fun sampleTodoList() = arrayListOf(
        Todo("Get graded", false),
        Todo("Pay attention", true),
        Todo("Get good at Android dev", false),
        Todo("Refactor Java projects", false)
    )

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
        todoList.addAll(sampleTodoList())
        adapter.notifyDataSetChanged()
    }

}