---
title: 4. Complex layouting - Views
---

## RecyclerViews

See also: [Android dev guide: create dynamic lists with RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview).

Now that we know what [activities](/android/activities) are, how to re-use components using [fragments](/android/fragments), and how to interact between these systems using [intents](/android/intents), we almost know all the basics of Android app development. Remember the Mac Mail App layout, introduced in the fragment chapter?

![](/img/mail.jpg "Left: the master screen with navigation. Right: the detail: zoomed in on a single mail.")

The left-hand side displays **a list** users can select: their mails. The right-hand side shows the detail view of a single mail. We know these sections have to be divided into _fragments_ to support big and small resolutions. But we do not yet know how to display a list of things. That's where a _RecyclerView_ comes in. From the Android docs:

> RecyclerView makes it easy to efficiently display large sets of data. You supply the data and define how each item looks, and the RecyclerView library dynamically creates the elements when they're needed.<br/>
As the name implies, RecyclerView recycles those individual elements. When an item scrolls off the screen, RecyclerView doesn't destroy its view. Instead, RecyclerView reuses the view for new items that have scrolled onscreen. This reuse vastly improves performance, improving your app's responsiveness and reducing power consumption.

This means we'll design how a single item looks like, supply the data, and the RecyclerView will duplicate these and fill in all the details such as (infinite) scroll handling. We'll need a couple of things to do that:

1. A `RecyclerView` instance, that is just like any other view, a part of your layout. 
2. A `ViewHolder` that represents an individual object, and gets binded to the RecyclerView's data. 
3. An `Adapter` that defines and associates with the data with the ViewHolder. 

The recycler view is included via the `androidx.recyclerview:recyclerview:1.2.1` dependency.

Let's try to make a simple TODO app to show how these three things are intertwined. 

![](/img/todoapp.jpg "A showcase of a simple RecyclerView: the TODO app. See examples in source!")

### 1. Design your layouts

In your activity XML file, we will add three objects: a `RecyclerView`, which will represent the list, an `EditText` view and a `Button`, which can be used to add new TODO items to the list. Remember their IDs. The recyclerview might look like this (constraints omitted for brevity):

```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/rvwTodo"
    android:layout_width="match_parent"
    android:layout_height="0dp" />
```

Besides the `activity_main.xml` file, we'll need a second XML file to design the layout of a **single item** in the list. This will get repeated and/or recycled by the `RecyclerView` class. All we need to do is to add a `TextView` and a `Checkbox`---just like in the screenshot above. The root layout can be a `ConstraintLayout`, just like any other activity. Give the root a `android:layout_height` value of only `100dp`, otherwise every item will be the size of the entire screen. 

### 2. Create your model and adapter

Next, we'll create a class that extends from `RecyclerView.Adapter`, and an inner class that represents the view holder. These classes drive our recycler view: they (1) couple the layout XML onto the view, and (2) manage the creation of new holders and binding. 

Three methods have to be overrided: item counting (easy, just redirect to the list size), view holder binding (pry out an item and set view values accordingly), and lastly the creation of the viewholder:

```kt
class TodoAdapter(val items: List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
state accordingly
        val currentTodoItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtTodoTitle).text = currentTodoItem.title
            findViewById<CheckBox>(R.id.chkTodoDone).isChecked = currentTodoItem.isDone
        }
    }

    override fun getItemCount(): Int = items.size
}
```

The model, the `Todo` class, is very simple, holding only a string and a boolean:

```kt
data class Todo(val title: String, val isDone: Boolean)
```

### 3. Initialize your adapter in the main activity 

For demonstrative purposes, create a `sampleTodoItems` array list with a few sample Todo item values. Then, we can create an instance of `TodoAdapter` in `onCreate()`:

```kt
var adapter = TodoAdapter(sampleTodoItems)
binding.rvwTodo.adapter = adapter
binding.rvwTodo.layoutManager = LinearLayoutManager(this)
```

We use view binding (see [activities](/android/activities)) to get hold of the recyclerview, called `rvwTodo`. The layout manager [must be set](https://developer.android.com/guide/topics/ui/layout/recyclerview#plan-your-layout) to arrange our items in the view accordingly. There are three possibilities, see [the docs](https://developer.android.com/guide/topics/ui/layout/recyclerview#plan-your-layout) for more information.

Lastly, let's set a click listener on the add button. This simply adds something to the list. However, the adapter should be notified of a data change in order for the view to update:

```kt
binding.btnAddTodo.setOnClickListener {
    val newTodoTitle = binding.edtTodo.text.toString()
    sampleTodoItems.add(Todo(newTodoTitle, false))
    adapter.notifyItemInserted(sampleTodoItems.size - 1)
}
```

A simpler alternative would be to call `adapter.notifyDatasetChanged()`---however, this is much more inefficient as it updates _all elements_ in the list, not only the last added one. 

To see it all in action, build `examples/kotlin/recyclerview` from the course repository. 


## About Responsive Design

TODO geen px maar sd


## RecyclerView

TODO