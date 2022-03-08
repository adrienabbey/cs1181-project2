// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Player abstract class for Project 2

// This abstract class has HumanPlayer and ComputerPlayer subclasses.

public abstract class Player {

    /* Fields */

    private String name; // Each player has a name
    private Deck hand; // contains the cards in the player's hand
    private int tokens; // how many tokens the player has
    private boolean isPlaying; // whether the player is still in the game

    /* Constructor */

    public Player(String name, int tokens) {
        // Every player starts with an empty hand and a pile of tokens:
        hand = new Deck();
        this.tokens = tokens;
        this.name = name;
        isPlaying = true;
    }

    /* Methods */

    public abstract void playTurn(); // Humans and computers play very differently.

    // public abstract boolean isWaiting(); // FIXME: I shouldn't need this!

    // public abstract void playCard(int playCard); // FIXME: I shouldn't need this!

    public void drawCard(Deck deck) {
        // Draws a card from the given deck, moving it to the player's hand:
        Card card = deck.get(0);
        deck.remove(card);
        hand.add(card);
    }

    public boolean lostRound(Game game) {
        // The player lost the round. Return true if they can keep playing:
        // TODO: Reveal the player's hand if they lose.

        System.out.println(getName() + " lost the round.");

        if (tokens > 0) {
            new Notification("Round End", (getName() + " lost the round."));
            // If the player has a token, toss it in the pot:
            tokens--;
            game.addToPot();
            return true;
        } else {
            // If the player has no tokens left, they lose:
            System.out.println(getName() + " is out of the game.");
            new Notification("Player Out", (getName() + " is out of the game."));
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

    public boolean isPlaying() {
        // Getter: return whether the player is still in the game or not:
        return isPlaying;
    }

    public void isOut() {
        // Setter: the player ran out of tokens and is out of the game:
        isPlaying = false;
    }

    @Override
    public String toString() {
        // Return a string containing the player's name and current tokens:
        return (name + " has " + tokens + " tokens left.");
    }
}
