package com.cennetnadir.lumen.feature.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cennetnadir.lumen.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.FragmentLibraryBinding
import com.google.firebase.firestore.FirebaseFirestore

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: DeckAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewDecks.layoutManager = LinearLayoutManager(requireContext())

        firestore.collection("decks")
            .get()
            .addOnSuccessListener { result ->
                val decks = result.toObjects(Deck::class.java)
                adapter = DeckAdapter(decks, this::onEditClick, this::onLearnClick)
                binding.recyclerViewDecks.adapter = adapter
            }
    }

    private fun onEditClick(deck: Deck) {
        val bundle = Bundle().apply {
            putParcelable("deck", deck)
        }
        findNavController().navigate(R.id.action_libraryFragment_to_editDeckFragment, bundle)
    }

    private fun onLearnClick(deck: Deck) {
        val bundle = Bundle().apply {
            putParcelable("deck", deck)
        }
        findNavController().navigate(R.id.action_libraryFragment_to_navigation_learn, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
