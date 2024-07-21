package com.cennetnadir.lumen.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cennetnadir.lumen.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    // View binding instance to access layout views
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the login button
        binding.buttonLogin.setOnClickListener {
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the register button
        binding.buttonRegister.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
