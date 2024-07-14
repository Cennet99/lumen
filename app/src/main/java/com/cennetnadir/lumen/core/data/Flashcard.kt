package com.cennetnadir.lumen.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flashcard(
    val id: String = "", // Default value for Firebase deserialization
    val question: String = "",
    val answer: String = ""
) : Parcelable
