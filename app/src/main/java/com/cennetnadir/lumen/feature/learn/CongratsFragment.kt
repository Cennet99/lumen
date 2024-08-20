package com.cennetnadir.lumen.feature.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cennetnadir.lumen.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cennetnadir.lumen.databinding.FragmentCongratsBinding

class CongratsFragment : Fragment() {

    private var _binding: FragmentCongratsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCongratsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReturnToLibrary.setOnClickListener {
            findNavController().navigate(R.id.action_congratsFragment_to_navigation_library)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
