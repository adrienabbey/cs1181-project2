// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Deck.class for Project 2

// Note: This class may be useful for future projects.  I'm aiming to keep 
// this class both generic and reusable.

// Note: I'm pulling Rank and Suit enums from their relevant classes.  This 
// allows the enums to be used by other classes.

public class Card {

    // Fields:
    private final Suit suit;
    private final Rank rank;

    // Constructor:
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Methods:

    public int getRankValue() {
        // Return the value of the card's rank:
        return rank.value;
    }

    public String getRankString() {
        // Return the card's rank:
        return rank.label;
    }

    public String getSuitString() {
        // Return the card's suit:
        return suit.label;
    }

    @Override
    public String toString() {
        // Return the name of the card as a string:
        return (rank.label + " of " + suit.label);
    }

}
