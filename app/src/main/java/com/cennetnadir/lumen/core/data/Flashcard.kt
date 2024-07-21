package com.cennetnadir.lumen.core.data

import com.google.firebase.firestore.IgnoreExtraProperties
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Annotation to ignore extra properties from Firestore documents that are not in this class
@IgnoreExtraProperties
@Parcelize
data class Flashcard(
    val id: String = "", // Unique ID for the flashcard
    val question: String = "", // Question text
    val answer: String = "" // Answer text
) : Parcelable // Parcelable to pass Flashcard objects between components
