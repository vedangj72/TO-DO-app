package com.example.to_doapp

import TodoDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class TodoForm : Fragment(R.layout.fragment_todo_form) {

    private lateinit var todoDatabase: TodoDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoDatabase = TodoDatabase(requireContext())

        val saveButton: Button = view.findViewById(R.id.SubmitBtn)
        saveButton.setOnClickListener {
            val todoText = view.findViewById<EditText>(R.id.TodoformeditText).text.toString()
            if (todoText.isNotEmpty()) {
                val newTodo = Todo(
                    id = 0,  // Assuming the ID is auto-generated
                    text = todoText,
                    isDone = false  // Set a default value for isDone
                )
                todoDatabase.insertTodo(newTodo)
                parentFragmentManager.popBackStack()  // Go back to the previous fragment
            }
        }
    }
}
