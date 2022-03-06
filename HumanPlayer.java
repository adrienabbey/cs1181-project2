// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Human Player class for Project 2, extends Player class

public class HumanPlayer extends Player {
    /* Fields */
    boolean waiting; // Tracks whether the game is waiting for input from the player.
    int playCard; // Tracks what card the player selected.

    /* Constructor */
    public HumanPlayer(String name, int tokens) {
        super(name, tokens);
    }

    /* Methods */
    public void playTurn() {
        // FIXME TEST: This needs to work right.

        // Set to waiting for input:
        waiting = true;

        // So long as the player hasn't played a card
        while (waiting) {
            // FIXME: Just wait for input. Is this okay?
        }

        // Play the selected card:
        this.playCard(playCard);

        // Update the score:
        NinetyNine.game.updateScore();
    }

    public void playCard(int cardIndex) {
        // Sets the played card index and stops waiting:
        this.playCard = cardIndex;
        waiting = false;
    }

    public boolean isWaiting() {
        // Return waiting status:
        return waiting;
    }
}
