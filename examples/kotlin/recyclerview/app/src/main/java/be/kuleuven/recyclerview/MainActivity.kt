package be.kuleuven.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.recyclerview.databinding.ActivityMainBinding
import be.kuleuven.recyclerview.model.Todo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sampleTodoItems = arrayListOf(
            Todo("Get graded", false),
            Todo("Pay attention", true),
            Todo("Get good at Android dev", false),
            Todo("Refactor Java projects", false)
        )

        var adapter = TodoAdapter(sampleTodoItems)
        binding.rvwTodo.adapter = adapter
        // If we don't supply a layout manager, the recyclerview will not be displayed
        // there are three options here: a simple LinearLayoutManager (1-dimensional), a GridLayoutManager (2D) or a StaggeredGridLayoutManager
        binding.rvwTodo.layoutManager = LinearLayoutManager(this)

        binding.btnAddTodo.setOnClickListener {
            val newTodoTitle = binding.edtTodo.text.toString()
            // this will not automatically updat the view!
            sampleTodoItems.add(Todo(newTodoTitle, false))
            adapter.notifyItemInserted(sampleTodoItems.size - 1)
            // adapter.notifyDatasetChanged() also works but will update EVERYTHING, which is not too efficient.
        }

    }
}