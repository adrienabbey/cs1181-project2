// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Rank enum class for Project 2

// Note: I'm attempting to keep this generic, for possible future reuse.
// This class currently assumes the use of a standard 52-card deck.

// I'm attempting to attach values to enums, as per here:
// https://www.baeldung.com/java-enum-values
// https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

public enum Rank {
    ACE("Ace", 1, "ace"),
    TWO("Two", 2, "2"),
    THREE("Three", 3, "3"),
    FOUR("Four", 4, "4"),
    FIVE("Five", 5, "5"),
    SIX("Six", 6, "6"),
    SEVEN("Seven", 7, "7"),
    EIGHT("Eight", 8, "8"),
    NINE("Nine", 9, "9"),
    TEN("Ten", 10, "10"),
    JACK("Jack", 11, "jack"),
    QUEEN("Queen", 12, "queen"),
    KING("King", 13, "king");

    /* Enum Fields */
    public final String label;
    public final int value;
    public final String filename;

    /* Enum Constructor */
    private Rank(String label, int value, String filename) {
        this.label = label;
        this.value = value;
        this.filename = filename;
    }
}
