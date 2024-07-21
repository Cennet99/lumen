package com.cennetnadir.lumen.core.data

import com.google.firebase.firestore.IgnoreExtraProperties
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Flashcard(
    val id: String = "",
    val question: String = "",
    val answer: String = ""
) : Parcelable
