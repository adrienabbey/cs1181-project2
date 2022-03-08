// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Human Player class for Project 2, extends Player class

public class HumanPlayer extends Player {
    /* Fields */
    boolean waiting; // Tracks whether the game is waiting for input from the player.
    int playCardIndex; // Tracks what card the player selected.

    /* Constructor */
    public HumanPlayer(String name, int tokens) {
        super(name, tokens);
    }

    /* Methods */
    synchronized public void playTurn() {

        // Set to waiting for input:
        waiting = true;

        // This method waits for a valid input when it's the player's turn.
        // I discovered I could use wait() and notify() methods to do this.
        // https://www.geeksforgeeks.org/wait-method-in-java-with-examples/

        // Wait to be notified:
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(this.getName() + " played " + this.getHand().get(playCardIndex));
        System.out.println();

        // Play the selected card:
        this.getHand().playCard(playCardIndex, NinetyNine.game.getDiscardDeck());

        // Draw a new card:
        this.drawCard(NinetyNine.game.getDrawDeck());
    }

    synchronized public void playCard(int cardIndex) {
        // Sets the played card index and stops waiting:
        this.playCardIndex = cardIndex;
        waiting = false;
        notify();
    }

    public boolean isWaiting() {
        // Return waiting status:
        return waiting;
    }
}
