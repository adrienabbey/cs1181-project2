// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Suit enum class for Project 2

// Note: I'm attempting to keep this generic, for possible future reuse.
// This class currently assumes the use of a standard 52-card deck.

// I'm attempting to attach values to enums, as per here:
// https://www.baeldung.com/java-enum-values
// https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

public enum Suit {
    HEARTS("Hearts", "_of_hearts"),
    DIAMONDS("Diamonds", "_of_diamonds"),
    SPADES("Spades", "_of_spades"),
    CLUBS("Clubs", "_of_clubs");

    /* Enum Fields */
    public final String label;
    public final String filename; // contains the relevant partial file name for the given suit

    /* Enum Constructors */
    private Suit(String label, String filename) {
        this.label = label;
        this.filename = filename;
    }
}
