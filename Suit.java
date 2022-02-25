// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Suit enum class for Project 2

// I'm attempting to attach values to enums, as per here:
// https://www.baeldung.com/java-enum-values
// https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

public enum Suit {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    SPADES("Spades"),
    CLUBS("Clubs");

    // Enum fields:
    public final String label;

    // Enum constructor:
    private Suit(String label) {
        this.label = label;
    }
}
