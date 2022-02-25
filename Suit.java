// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Suit enum class for Project 2

// Note: I'm attempting to keep this generic, for possible future reuse.
// This class currently assumes the use of a standard 52-card deck.

// I'm attempting to attach values to enums, as per here:
// https://www.baeldung.com/java-enum-values
// https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

public enum Suit {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    SPADES("Spades"),
    CLUBS("Clubs");

    /* Enum Fields */
    public final String label;

    /* Enum Constructors */
    private Suit(String label) {
        this.label = label;
    }
}
