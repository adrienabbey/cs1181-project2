// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Computer Player class for Project 2, extends Player class

import java.util.ArrayList;

public class ComputerPlayer extends Player {
    /* Constructor */
    public ComputerPlayer(String name, int tokens) {
        super(name, tokens);
    }

    /* Methods */
    public void playTurn() {
        // Makes this computer player play their turn.
        // Returns true if there's a playable card, false if not.

        // Steps:
        // 1: Get the current score.
        // 2: If possible, play the largest playable non-special card in their hand.
        // 3: If not, play a special card if they have one.
        // 4: If the player has no playable cards, they lose.
        // 5: If a card was played, then draw a card.

        // TODO: it may be desirable to add some degree of randomness here. Otherwise
        // the computer players become predictable.

        // Get the current score:
        int score = NinetyNine.game.getScore();

        // Sort the player's current hand:
        this.getHand().sort();

        // Track the playable cards:
        ArrayList<Card> playable = new ArrayList<Card>();

        // Check to see if any cards are playable:
        for (Card card : this.getHand()) {
            // If the card is playable:
            if (card.getScoreValue() + score <= 99) {
                // Then add that card to the playable list:
                playable.add(card);
            } else if (card.getRankValue() == 99) {
                playable.add(card);
            }
        }

        // If there's at least one playable card:
        if (playable.size() > 0) {
            // Play the first playable card:
            Card playCard = playable.get(0);
            this.getHand().playCard(this.getHand().indexOf(playCard), NinetyNine.game.getDiscardDeck());

            // Draw a card:
            this.drawCard(NinetyNine.game.getDrawDeck());

            // Print out the card played:
            System.out.println(this.getName() + " played " + playCard);
        } else {
            // No playable card, just play the first card in the hand:
            this.getHand().playCard(0, NinetyNine.game.getDiscardDeck());

            // FIXME: Don't draw a card. This may have UI implications to fix later.
        }
    }

    public boolean isWaiting() {
        // The PC is never waiting for input:
        return false;
    }

    public void playCard(int playCard) {
        // This method should never do anything for a computer player.
    }
}
