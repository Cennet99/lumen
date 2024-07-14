package com.cennetnadir.lumen.feature.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.FragmentLibraryBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: DeckAdapter
    private var deckList: MutableList<Deck> = mutableListOf()

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

        adapter = DeckAdapter(deckList)
        binding.recyclerViewDecks.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDecks.adapter = adapter

        fetchDecks()
    }

    private fun fetchDecks() {
        firestore.collection("decks")
            .get()
            .addOnSuccessListener { result: QuerySnapshot ->
                deckList.clear()
                deckList.addAll(result.toObjects())
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Handle error
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
