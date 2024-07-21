package com.cennetnadir.lumen.feature.browse

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.FragmentBrowseBinding
import com.google.firebase.firestore.FirebaseFirestore

class BrowseFragment : Fragment() {

    // View binding instance for accessing layout views
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    // Firebase Firestore instance for database operations
    private lateinit var firestore: FirebaseFirestore
    // Adapter for displaying the list of decks
    private lateinit var adapter: DeckAdapterBrowse
    // List to hold all decks fetched from Firestore
    private var allDecks: List<Deck> = listOf()

    // Inflates the layout for this fragment using view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    // Called after the view has been created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView with a linear layout manager and the adapter
        binding.recyclerViewBrowseDecks.layoutManager = LinearLayoutManager(requireContext())
        adapter = DeckAdapterBrowse(mutableListOf(), this::onLearnClick)
        binding.recyclerViewBrowseDecks.adapter = adapter

        // Fetch decks from Firestore
        fetchDecks()

        // Set up the search bar to filter decks as the user types
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterDecks(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Fetches decks from Firestore and updates the adapter
    private fun fetchDecks() {
        firestore.collection("decks")
            .get()
            .addOnSuccessListener { result ->
                // Convert Firestore documents to Deck objects and update the adapter
                allDecks = result.toObjects(Deck::class.java)
                adapter.updateDecks(allDecks)
            }
            .addOnFailureListener { e ->
                // Show an error message if the fetch operation fails
                Toast.makeText(requireContext(), "Error fetching decks: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Filters the list of decks based on the search query
    private fun filterDecks(query: String) {
        val filteredDecks = if (query.isEmpty()) {
            allDecks
        } else {
            allDecks.filter { it.name.contains(query, ignoreCase = true) }
        }
        adapter.updateDecks(filteredDecks)
    }

    // Navigates to the learning screen when a deck is selected
    private fun onLearnClick(deck: Deck) {
        val bundle = Bundle().apply {
            putParcelable("deck", deck)
        }
        findNavController().navigate(R.id.action_browseFragment_to_navigation_learn, bundle)
    }

    // Called when the view is destroyed to clean up resources
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
