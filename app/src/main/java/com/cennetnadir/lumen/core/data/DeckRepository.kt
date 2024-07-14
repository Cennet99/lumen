package com.cennetnadir.lumen.core.data

import kotlinx.coroutines.tasks.await

interface DeckRepository {
    suspend fun getDeckById(deckId: String): Deck?
    suspend fun updateDeck(deck: Deck)
    suspend fun getAllDecks(): List<Deck>
    suspend fun addDeck(deck: Deck)
}

