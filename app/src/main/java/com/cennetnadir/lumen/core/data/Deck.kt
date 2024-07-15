package com.cennetnadir.lumen.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deck(
    val id: String = "", // Default value for Firebase deserialization
    val name: String = "",
    var flashcards: List<Flashcard> = emptyList()
) : Parcelable
