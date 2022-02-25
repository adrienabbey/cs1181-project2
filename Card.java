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

    public String getRankString() {
        // Return a formated string of this card's rank.

        // Capitalize the first letter, but lowercase the rest:
        // Source: https://attacomsian.com/blog/capitalize-first-letter-of-string-java
        String r = ("" + rank);
        r = r.substring(0, 1).toUpperCase() + r.substring(1).toLowerCase();

        return r;
    }

    public String getSuitString() {
        // Return a formated string of this card's suit.

        // Capitalize the first letter, but lowercase the rest:
        String s = ("" + suit);
        s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        return s;
    }

    @Override
    public String toString() {
        // The rank and suit enums are all caps, so I need to fix that.

        // Create strings to hold the card's rank and suit:
        String r = ("" + rank);
        String s = ("" + suit);

        // Capitalize the first letter, but lowercase the rest:
        r = r.substring(0, 1).toUpperCase() + r.toLowerCase().substring(1);
        s = s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1);

        return (r + " of " + s);
    }

}
