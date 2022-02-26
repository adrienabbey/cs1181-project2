// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Deck.class for Project 2

// Note: This class may be useful for future projects, and therefore a 
// generic, reusable class for playing cards.

import java.util.ArrayList;

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

    public Card draw() {
        // Draw a card from the deck, removing that card from the deck and returning it:
        Card r = this.get(0);
        this.remove(0);
        return r;
    }

    @Override
    public String toString() {
        // Returns a really long string of every card in the deck.
        String r = "";

        // For every card in the deck:
        for (Card c : this) {
            r += (c.toString() + ", ");
        }

        return r;
    }
}
