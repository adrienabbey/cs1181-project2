// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Rank enum class for Project 2

// I'm attempting to attach values to enums, as per here:
// https://www.baeldung.com/java-enum-values
// https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

public enum Rank {
    ACE("Ace", 1),
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 11),
    QUEEN("Queen", 12),
    KING("King", 13);

    // Enum fields:
    public final String label;
    public final int value;

    // Enum constructor:
    private Rank(String label, int value) {
        this.label = label;
        this.value = value;
    }
}
