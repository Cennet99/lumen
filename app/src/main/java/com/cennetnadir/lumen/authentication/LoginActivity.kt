package com.cennetnadir.lumen.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cennetnadir.lumen.main.MainActivity
import com.cennetnadir.lumen.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // View binding instance to access layout views
    private lateinit var binding: ActivityLoginBinding

    // Firebase Authentication instance for login operations
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Set click listener for the login button
        binding.buttonLogin.setOnClickListener {
            // Retrieve email and password from input fields
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Check if email and password fields are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Sign in with email and password using Firebase Authentication
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Login successful, navigate to MainActivity
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish() // Close the login activity
                        } else {
                            // Login failed, show error message
                            Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                // Show error message if email or password is empty
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_LONG).show()
            }
        }

        // Set click listener for the register text view
        binding.textViewRegister.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
