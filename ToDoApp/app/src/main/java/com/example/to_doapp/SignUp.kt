package com.example.to_doapp

import Home
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignUp : Fragment(R.layout.fragment_sign_up) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the shared preferences and editor
        val sharedPreferences = requireContext().getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Find views by their IDs
        val etName: EditText = view.findViewById(R.id.EditName)
        val etEmail: EditText = view.findViewById(R.id.EditEmail)
        val etAge: EditText = view.findViewById(R.id.EditAge)
        val etPassword: EditText = view.findViewById(R.id.EditPass)
        val btnSignUp: Button = view.findViewById(R.id.SignUpbtn)
        val ToLoginIn: TextView = view.findViewById(R.id.ToLoginIn)

        // Set the click listener for the Sign Up button
        btnSignUp.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val age = etAge.text.toString()
            val password = etPassword.text.toString()


            if (name.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty() && password.isNotEmpty()) {
                // Save user data in shared preferences
                editor.putString("name", name)
                editor.putString("email", email)
                editor.putString("age", age)
                editor.putString("password", password)
                editor.putBoolean("isLoggedIn", true) // Optionally, set this to true to auto-login after sign-up
                editor.apply()

                Toast.makeText(requireContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show()

                // Navigate to HomeFragment after successful sign-up
                (activity as MainActivity).navigateToFragment(Home())
            } else {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        ToLoginIn.setOnClickListener {
            (activity as MainActivity).navigateToFragment(Login())
        }
    }
}
