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

class Login : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the shared preferences
        val sharedPreferences = requireContext().getSharedPreferences("myPref", MODE_PRIVATE)

        // Find views by their IDs
        val etEmail: EditText = view.findViewById(R.id.LogEmail)
        val etPassword: EditText = view.findViewById(R.id.LogPass)
        val btnLogin: Button = view.findViewById(R.id.LoginBtn)
        val toSignUp: TextView = view.findViewById(R.id.ToSignin)

        // Set the click listener for the Login button
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Check if the entered email and password match the stored credentials
            val storedEmail = sharedPreferences.getString("email", "")
            val storedPassword = sharedPreferences.getString("password", "")

            if (email == storedEmail && password == storedPassword) {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                val sharedPreferences = requireContext().getSharedPreferences("myPref", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()
                // Navigate to HomeFragment after successful login
                (activity as MainActivity).navigateToFragment(Home())
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the click listener for the Redirect to Sign Up text
        toSignUp.setOnClickListener {
            (activity as MainActivity).navigateToFragment(SignUp())
        }
    }
}
