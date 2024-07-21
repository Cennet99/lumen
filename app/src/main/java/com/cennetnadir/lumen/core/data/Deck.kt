package com.cennetnadir.lumen.core.data


import com.google.firebase.firestore.IgnoreExtraProperties
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Deck(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    var flashcards: List<Flashcard> = emptyList()
) : Parcelable
