package com.example.to_doapp

import Home
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check login status and navigate accordingly
        val sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
        val isLogged = sharedPreferences.getBoolean("isLoggedIn", false)
        if (savedInstanceState == null) {
            if (isLogged) {
                // User is logged in, navigate to Home fragment
                navigateToFragment(Home())
            } else {
                // User is not logged in, navigate to SignUp fragment
                navigateToFragment(SignUp())
            }
        }
    }
    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Fragment_main, fragment)
            addToBackStack(null) // Optional: Add to back stack to navigate back to previous fragment
            commit()
        }
    }
}
