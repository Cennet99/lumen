package com.cennetnadir.lumen.feature.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private lateinit var deck: Deck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deck = arguments?.getParcelable("deck")!!
        binding.textViewDeckName.text = deck.name

        // Implement the learning functionality here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
