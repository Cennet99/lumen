package com.cennetnadir.lumen.core.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlashcardRepositoryImpl @Inject constructor() : FlashcardRepository {

    private val flashcards = mutableListOf<Flashcard>()
    private val decks = mutableListOf<Deck>()

    override fun addFlashcard(flashcard: Flashcard) {
        flashcards.add(flashcard)
    }

    override fun getFlashcards(): List<Flashcard> {
        return flashcards
    }

    override fun createDeck(name: String): Deck {
        val deck = Deck(decks.size.toString(), name)
        decks.add(deck)
        return deck
    }

    override fun getDecks(): List<Deck> {
        return decks
    }
}
