import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.R
import com.example.to_doapp.Todo

class TodoAdapter(
    private var todos: List<Todo>,
    private val onItemClick: (Todo) -> Unit,  // Lambda for item clicks
    private val onCheckboxChanged: (Todo, Boolean) -> Unit  // Lambda for checkbox changes
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.HomeDisplay)
        private val checkbox: CheckBox = itemView.findViewById(R.id.Isdone)

        init {
            // Set click listener for the entire item view
            itemView.setOnClickListener {
                onItemClick(todos[adapterPosition])
            }

            // Set up checkbox change listener
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckboxChanged(todos[adapterPosition], isChecked)
            }
        }

        fun bind(todo: Todo) {
            textView.text = todo.text
            checkbox.isChecked = todo.isDone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int = todos.size

    fun updateTodos(newTodos: List<Todo>) {
        todos = newTodos
        notifyDataSetChanged()
    }
}
