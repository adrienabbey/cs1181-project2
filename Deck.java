// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Deck.class for Project 2

// Note: This class may be useful for future projects, and therefore a 
// generic, reusable class for playing cards.

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {
    /* Fields */
    // None, this is an ArrayList of Cards.

    /* Constructors */

    public Deck() {
        // Construct an empty deck, such as a player starting with an empty hand:
        this.clear();
    }

    public Deck(String type, int count) {
        // Construct a deck of cards:

        // For now, there's only one type of deck: standard52.
        // The deck count refers to how many decks are shuffed together.

        // For a standard 52-card deck:
        if (type.equals("standard52")) {
            // Start adding cards to the deck:

            // For each deck:
            for (int i = 0; i < count; i++) {
                // There are 13 ranks in a standard deck:
                for (Rank r : Rank.values()) {
                    // There are 4 suits in a standard deck:
                    for (Suit s : Suit.values()) {
                        this.add(new Card(r, s));
                    }
                }
            }

            // Note: New decks are not necessarily shuffled.
        }
    }

    /* Methods */

    public boolean playCard(int cardIndex, Deck discardDeck) {
        // Play the card from this deck (using the given index value) to the specified
        // deck:

        // Verify that the specified card exists:
        try {
            this.get(cardIndex);
        } catch (IndexOutOfBoundsException e) {
            // Card does not exist, return false to indicate failure:
            return false;
        }

        Card playCard = this.get(cardIndex);
        this.remove(cardIndex);
        discardDeck.add(playCard);
        // Return true to indicate success:
        return true;
    }

    public void emptyInto(Deck other) {
        // Move all the cards in this deck into the given deck:

        // Add each card in this deck to the other deck:
        for (Card card : this) {
            other.add(card);
        }

        // Remove each card in the other deck from this deck:
        for (Card card : other) {
            this.remove(card);
        }
    }

    public void shuffle() {
        // Shuffle the deck:
        Collections.shuffle(this);
    }

    @Override
    public String toString() {
        // Returns a really, really long string of every card in the deck.
        String r = "";

        // For every card in the deck:
        for (Card c : this) {
            r += (c.toString() + ", ");
        }

        return r;
    }
}
