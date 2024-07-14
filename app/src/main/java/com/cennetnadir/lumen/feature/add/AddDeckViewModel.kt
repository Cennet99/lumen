package com.cennetnadir.lumen.feature.add

import androidx.lifecycle.ViewModel
import com.cennetnadir.lumen.core.data.Deck
import com.google.firebase.firestore.FirebaseFirestore

class AddDeckViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveDeck(deckName: String) {
        val deck = Deck(name = deckName)
        firestore.collection("decks")
            .add(deck)
            .addOnSuccessListener {
                // Erfolgreich gespeichert
            }
            .addOnFailureListener { e ->
                // Fehler beim Speichern
            }
    }
}
