package com.cennetnadir.lumen.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cennetnadir.lumen.main.MainActivity
import com.cennetnadir.lumen.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    // View binding instance to access layout views
    private lateinit var binding: ActivityRegisterBinding
    // Firebase Authentication instance for registration operations
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Set click listener for the register button
        binding.buttonRegister.setOnClickListener {
            // Retrieve email, password, and confirm password from input fields
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            // Check if email and passwords are not empty and passwords match
            if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                // Create a new user with email and password using Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registration successful, navigate to MainActivity
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Registration failed, show error message
                            Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                // Show error message if email or passwords are invalid
                Toast.makeText(this, "Please enter email and matching passwords", Toast.LENGTH_LONG).show()
            }
        }

        // Set click listener for the login text view
        binding.textViewLogin.setOnClickListener {
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
