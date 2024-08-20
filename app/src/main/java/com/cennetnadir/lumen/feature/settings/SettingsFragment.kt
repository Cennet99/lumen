package com.cennetnadir.lumen.feature.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cennetnadir.lumen.authentication.StartActivity
import com.cennetnadir.lumen.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    // View binding instance to access layout views
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // Firebase Authentication instance
    private lateinit var auth: FirebaseAuth

    // Inflate the layout for this fragment using view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout and obtain an instance of the binding class
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Return the root view of the bound layout
        return binding.root
    }

    // Set up listeners in onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Logout button click listener
        binding.buttonLogout.setOnClickListener {
            // Sign out the user
            auth.signOut()

            // Redirect to StartActivity
            val intent = Intent(requireContext(), StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Reset Password button click listener
        binding.buttonResetPassword.setOnClickListener {
            val userEmail = auth.currentUser?.email

            if (!userEmail.isNullOrEmpty()) {
                auth.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Password reset email sent", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "No email associated with the account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Clear the binding when the view is destroyed to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
