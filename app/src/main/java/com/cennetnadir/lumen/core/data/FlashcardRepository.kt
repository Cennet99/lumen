// FlashcardRepository.kt
package com.cennetnadir.lumen.core.data

interface FlashcardRepository {
    fun addFlashcard(flashcard: Flashcard)
    fun getFlashcards(): List<Flashcard>
    fun createDeck(name: String): Deck
    fun getDecks(): List<Deck>
}
