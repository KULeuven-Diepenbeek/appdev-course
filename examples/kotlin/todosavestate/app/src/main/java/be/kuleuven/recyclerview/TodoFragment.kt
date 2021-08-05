package be.kuleuven.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.recyclerview.databinding.FragmentTodolistBinding
import be.kuleuven.recyclerview.model.Todo
import be.kuleuven.recyclerview.model.TodoRepository
import be.kuleuven.recyclerview.model.net.TodoInternetRepository

class TodoFragment : Fragment(R.layout.fragment_todolist) {

    private val todoList = arrayListOf<Todo>()
    private lateinit var todoRepository: TodoRepository

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

        // --- Switch here between data storage implementations to fiddle with them!
        //todoRepository = TodoFileRepository(requireContext())
        //todoRepository = TodoRoomRepository(main.applicationContext)
        todoRepository = TodoInternetRepository(requireContext()) {
            todoList.clear()
            todoList.addAll(it)
            adapter.notifyDataSetChanged()
        }
        // ---

        adapter = TodoAdapter(todoList)
        binding.rvwTodo.adapter = adapter
        // If we don't supply a layout manager, the recyclerview will not be displayed
        // there are three options here: a simple LinearLayoutManager (1-dimensional), a GridLayoutManager (2D) or a StaggeredGridLayoutManager
        binding.rvwTodo.layoutManager = LinearLayoutManager(this.context)

        binding.btnAddTodo.setOnClickListener(this::addNewTotoItem)
        restoreTodoListFromPreviousSession()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        todoRepository.save(todoList)
    }

    private fun restoreTodoListFromPreviousSession() {
        todoList.addAll(todoRepository.load())
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