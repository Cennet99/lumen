package com.cennetnadir.lumen.feature.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.FragmentLibraryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LibraryFragment : Fragment() {

    // View binding instance to access layout views
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    // Firebase Firestore instance for database operations
    private lateinit var firestore: FirebaseFirestore
    // Adapter for displaying the list of decks
    private lateinit var adapter: DeckAdapter
    // Firebase Authentication instance for user authentication
    private lateinit var auth: FirebaseAuth

    // Inflates the layout for this fragment using view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    // Called after the view has been created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewDecks.layoutManager = LinearLayoutManager(requireContext())
        adapter = DeckAdapter(mutableListOf(), this::onEditClick, this::onLearnClick, this::onDeleteClick)
        binding.recyclerViewDecks.adapter = adapter

        // Fetch decks for the current user
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fetchDecks(currentUser.uid)
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    // Fetch decks from Firestore for the given user ID
    private fun fetchDecks(userId: String) {
        firestore.collection("decks")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val decks = result.toObjects(Deck::class.java)
                adapter.updateDecks(decks)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error fetching decks: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Navigate to edit deck screen
    private fun onEditClick(deck: Deck) {
        val bundle = Bundle().apply {
            putParcelable("deck", deck)
        }
        findNavController().navigate(R.id.action_libraryFragment_to_editDeckFragment, bundle)
    }

    // Navigate to learn deck screen
    private fun onLearnClick(deck: Deck) {
        val bundle = Bundle().apply {
            putParcelable("deck", deck)
        }
        findNavController().navigate(R.id.action_libraryFragment_to_navigation_learn, bundle)
    }

    // Delete a deck from Firestore
    private fun onDeleteClick(deck: Deck) {
        firestore.collection("decks").document(deck.id)
            .delete()
            .addOnSuccessListener {
                adapter.removeDeck(deck)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error deleting deck: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Clear the binding when the view is destroyed to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
