package com.cennetnadir.lumen.feature.browse

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.FragmentBrowseBinding
import com.cennetnadir.lumen.feature.library.DeckAdapter
import com.google.firebase.firestore.FirebaseFirestore

class BrowseFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: DeckAdapter
    private var allDecks: List<Deck> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewBrowseDecks.layoutManager = LinearLayoutManager(requireContext())

        firestore.collection("decks")
            .get()
            .addOnSuccessListener { result ->
                allDecks = result.toObjects(Deck::class.java)
                adapter = DeckAdapter(allDecks.toMutableList(), this::onEditClick, this::onLearnClick, this::onDeleteClick)
                binding.recyclerViewBrowseDecks.adapter = adapter
            }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterDecks(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterDecks(query: String) {
        val filteredDecks = if (query.isEmpty()) {
            allDecks
        } else {
            allDecks.filter { it.name.contains(query, ignoreCase = true) }
        }
        adapter.updateDecks(filteredDecks)
    }

    private fun onEditClick(deck: Deck) {
        val bundle = Bundle().apply {
            putParcelable("deck", deck)
        }
        findNavController().navigate(R.id.action_browseFragment_to_editDeckFragment, bundle)
    }

    private fun onLearnClick(deck: Deck) {
        val bundle = Bundle().apply {
            putParcelable("deck", deck)
        }
        findNavController().navigate(R.id.action_browseFragment_to_navigation_learn, bundle)
    }

    private fun onDeleteClick(deck: Deck) {
        firestore.collection("decks").document(deck.id)
            .delete()
            .addOnSuccessListener {
                // Refresh the list after deletion
                adapter.removeDeck(deck)
            }
            .addOnFailureListener { e ->
                // Handle the failure if needed
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
