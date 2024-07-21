package com.cennetnadir.lumen.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cennetnadir.lumen.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    // View binding instance to access layout views
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // Inflate the layout for this fragment using view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout and obtain an instance of the binding class
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // Return the root view of the bound layout
        return binding.root
    }

    // Clear the binding when the view is destroyed to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
