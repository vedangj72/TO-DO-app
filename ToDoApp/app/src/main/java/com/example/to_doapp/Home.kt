import TodoAdapter
import TodoDatabase
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.Login
import com.example.to_doapp.R
import com.example.to_doapp.TodoForm
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : Fragment(R.layout.fragment_home) {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TodoDatabase
        todoDatabase = TodoDatabase(requireContext())

        // Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        todoAdapter = TodoAdapter(emptyList(), { todo ->
            // Handle item clicks here if needed
        }, { todo, isChecked ->
            if (isChecked) {
                deleteTodoAndReload(todo.id)
            }
        })
        recyclerView.adapter = todoAdapter

        // Load todos from the database
        loadTodos()

        // Set up FloatingActionButton click listener to add new todos
        val addTodoButton: FloatingActionButton = view.findViewById(R.id.HomeTodoadd)
        addTodoButton.setOnClickListener {
            val addTodoFragment = TodoForm()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.Fragment_main, addTodoFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun loadTodos() {
        try {
            val todos = todoDatabase.getAllTodo()
            Log.d("HomeFragment", "Loaded todos: $todos")  // Log the todos to check
            todoAdapter.updateTodos(todos)
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error loading todos", e)
            Toast.makeText(requireContext(), "Failed to load todos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTodoAndReload(todoId: Int) {
        try {
            todoDatabase.deleteTodoById(todoId)
            loadTodos()
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error deleting todo", e)
            Toast.makeText(requireContext(), "Failed to delete todo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        val sharedPreferences = requireContext().getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        val loginFragment = Login()
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.Fragment_main, loginFragment)
            addToBackStack(null)
            commit()
        }
    }
}
