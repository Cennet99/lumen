import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.core.data.DeckRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DeckRepositoryImpl : DeckRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val decksCollection = firestore.collection("decks")

    override suspend fun getDeckById(deckId: String): Deck? {
        return try {
            val document = decksCollection.document(deckId).get().await()
            document.toObject(Deck::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateDeck(deck: Deck) {
        decksCollection.document(deck.id).set(deck).await()
    }

    override suspend fun getAllDecks(): List<Deck> {
        return try {
            val snapshot = decksCollection.get().await()
            snapshot.toObjects(Deck::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addDeck(deck: Deck) {
        decksCollection.document(deck.id).set(deck).await()
    }
}
