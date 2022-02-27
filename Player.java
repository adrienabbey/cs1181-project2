import javax.swing.ImageIcon;

// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Player abstract class for Project 2

// This abstract class has HumanPlayer and ComputerPlayer subclasses.

public abstract class Player {

    /* Fields */

    private String name; // Each player has a name
    private Deck hand; // contains the cards in the player's hand
    private int tokens; // how many tokens the player has

    /* Constructor */

    public Player(String name, int tokens) {
        // Every player starts with an empty hand and a pile of tokens:
        hand = new Deck();
        this.tokens = tokens;
        this.name = name;
    }

    /* Methods */

    public void drawCard(Deck deck) {
        // Draws a card from the given deck, moving it to the player's hand:
        Card card = deck.get(0);
        deck.remove(card);
        hand.add(card);
    }

    public boolean lost(Game game) {
        // The player lost the round. Return true if they can keep playing:

        if (tokens > 0) {
            // If the player has a token, toss it in the pot:
            tokens--;
            game.addToPot();
            return true;
        } else {
            // If the player has no tokens left, they lose:
            return false;
        }
    }

    public Deck getHand() {
        // Getter: return the player's hand:
        return hand;
    }

    public int getTokens() {
        // Getter: return the player's token count:
        return tokens;
    }

    public String getName() {
        // Getter: return the player's name:
        return name;
    }

    @Override
    public String toString() {
        // Return a string containing the player's name and current tokens:
        return (name + " has " + tokens + " tokens left.");
    }
}
