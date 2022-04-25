package be.kuleuven.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.recyclerview.databinding.ActivityMainBinding
import be.kuleuven.recyclerview.model.Items
import be.kuleuven.recyclerview.model.Todo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var sampleTodoItems = arrayListOf(
            Todo("Fix", false),
            Todo("Debug", true),
        )
        Items().sort(sampleTodoItems)

        var adapter = TodoAdapter(sampleTodoItems)
        binding.rvwTodo.adapter = adapter
        binding.rvwTodo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)

        binding.btnAddTodo.setOnClickListener {
            val newTodoTitle = binding.edtTodo.text.toString()
            sampleTodoItems.add(Todo(newTodoTitle, false))

            Items().sort(sampleTodoItems)
        }

    }
}