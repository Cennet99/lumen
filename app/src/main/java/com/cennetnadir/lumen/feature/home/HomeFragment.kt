package com.cennetnadir.lumen.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cennetnadir.lumen.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // View binding instance to access layout views
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Inflate the layout for this fragment using view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Clear the binding when the view is destroyed to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
