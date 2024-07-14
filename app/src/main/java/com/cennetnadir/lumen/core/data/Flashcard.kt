package com.cennetnadir.lumen.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flashcard(
    val id: String = "",
    val question: String = "",
    val answer: String = ""
) : Parcelable
