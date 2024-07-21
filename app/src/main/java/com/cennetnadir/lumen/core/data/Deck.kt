package com.cennetnadir.lumen.core.data

import com.google.firebase.firestore.IgnoreExtraProperties
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Annotation to ignore extra properties from Firestore documents that are not in this class
@IgnoreExtraProperties
@Parcelize
data class Deck(
    val id: String = "", // Unique ID for the deck
    val userId: String = "", // ID of the user who owns the deck
    val name: String = "", // Name of the deck
    var flashcards: List<Flashcard> = emptyList() // List of flashcards in the deck
) : Parcelable // Parcelable to pass Deck objects between components
