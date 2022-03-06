// Adrien Abbey, CS-1181L-07, Jan 25, 2022

// Game class for Project 2
// Game logic (model) for the card game NinetyNine

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Game {

    /* Fields */
    private Random rng = new Random();
    private ArrayList<Player> playerList = new ArrayList<>();
    private int pot; // How many tokens are in the pot
    private int playerTurn; // Tracks which player's turn it is.
    private int dealerTurn; // Trakc which player is the dealer this round.
    private int rScore; // Track the current round's value
    private boolean rotation; // if true, rotate clockwise, otherwise counter-clockwise
    private Deck drawDeck;
    private Deck discardDeck;

    /* Constructor */
    public Game(String deckType, int deckCount, Player player1, Player player2, Player player3, Player player4) {
        // Start a new game with the given values:

        // Create new card decks:
        drawDeck = new Deck(deckType, deckCount);
        discardDeck = new Deck();

        // The game starts with an empty pot:
        pot = 0;

        // The game starts with clockwise rotation:
        rotation = true;

        // Add players to the player list:
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        // Randomly set which player's turn it is:
        playerTurn = rng.nextInt(4); // returns 0 to 3
        dealerTurn = playerTurn; // The first player is both the dealer and the first to play.
    }

    /* Methods */

    public void newRound() {
        // New round, new player's turn to deal:
        dealerTurn++;

        // Print out the dealer info:
        System.out.println(playerList.get(this.getDealerTurn()).getName() + " is the dealer.");

        // The dealer is also the first to play:
        playerTurn = dealerTurn;

        // Reset the current score:
        rScore = 0;

        // Return all the cards in each player's hand back to the game deck:
        for (Player p : playerList) {
            p.getHand().emptyInto(drawDeck);
        }

        // Return all the discarded cards to the game deck:
        discardDeck.emptyInto(drawDeck);

        // Reshuffle the deck:
        drawDeck.shuffle();

        // Deal a card to each player 3 times:
        for (int i = 0; i < 3; i++) {
            // For each player:
            for (Player p : playerList) {
                // Deal a card to the player:
                p.drawCard(drawDeck);
            }
        }

        // FIXME TEST: Sort each player's hand by discard desirability and print:
        // for (Player p : playerList) {
        // p.getHand().sort();
        // System.out.println(p.getHand());
        // }

        // Deal a card to the discard pile:
        drawDeck.playCard(0, discardDeck);
        updateScore();
        System.out.println(
                playerList.get(getPlayersTurn()).getName() + " turned up " + discardDeck.get(0)
                        + " as the first card.");

        // Update the turn indicator:
        this.nextPlayer();

        // FIXME TESTCODE: Make the AI play:
        // playerList.get(1).playTurn();
        // playerList.get(2).playTurn();
        // playerList.get(3).playTurn();

        // FIXME TEST: Sort each player's hand by discard desirability and print:
        // for (Player p : playerList) {
        // p.getHand().sort();
        // System.out.println(p.getHand());
        // }

        // FIXME TEST: Print out all the cards in the discard pile:
        // System.out.println("Discard pile: " + discardDeck);
    }

    public void gameLoop() {
        // Gameplay loop, called by newRound method:

        // Keep playing until someone looses:
        while (true) {
            // Next player plays:
            playerList.get(getPlayersTurn()).playTurn();

            // Update the UI:
            NinetyNine.updateUI();

            // Check the score for a loss:
            if (rScore > 99) {
                // Someone lost:
                System.out.println(playerList.get(getPlayersTurn()) + " lost.");

                // That player tosses a token:
                playerList.get(getPlayersTurn()).lost(NinetyNine.game);

                // Update the UI;
                // NinetyNine.updateUI();

                // Round ends:
                break;
            } else {
                // Otherwise the game continues.

                // Update the score:
                NinetyNine.game.updateScore();

                // Next player's turn:
                nextPlayer();
            }

            // Next player plays:
            // if (playerList.get(getPlayersTurn()).playTurn()) {
            // // If true, they played a card successfully:

            // // Update the score:
            // NinetyNine.game.updateScore();
            // } else {
            // // FIXME: If false, the player lost:
            // playerList.get(getPlayersTurn()).lost(NinetyNine.game);
            // break;
            // }
        }
    }

    public boolean updateScore() {
        // Update the game's score using the given card. If the score is over 99, return
        // false, indicating the round is over.

        Card card = discardDeck.get(0);

        // If the card is special (13, 10, 9, or 4):
        if (card.getRankValue() == 13) {
            // If a king, set score to 99:
            rScore = 99;
        } else if (card.getRankValue() == 10) {
            // If a 10, subtract 10 from the score (negative values acceptable):
            rScore -= 10;
        } else if (card.getRankValue() == 9) {
            // If 9, hold (do nothing).
        } else if (card.getRankValue() == 4) {
            // If 4, reverse rotation:
            // TODO: Verify that this means the previous player goes next.
            if (rotation == true) {
                rotation = false;
            } else {
                rotation = true;
            }
        } else if (card.getRankValue() > 10) {
            // If the card isn't special and it's value is greater than 10, add 10 to the
            // score:
            rScore += 10;
        } else {
            // Otherwise, add the card's rank value to the score:
            rScore = rScore + card.getRankValue();
        }

        // If the score is 99 or less:
        if (rScore < 100) {
            // Then the game continues:
            return true;
        } else {
            // Otherwise, the round is over:
            return false;
        }
    }

    public boolean drawFromDeck(Player p) {
        // Draw a card from the deck and add it to the given player's hand, if possible:
        // Return true if successful, false there's no cards left in the deck.

        // If there's a card left in the deck to draw:
        if (drawDeck.size() > 0) {
            p.drawCard(drawDeck);
            return true;
        } else {
            return false;
        }
    }

    public void nextPlayer() {
        // Increments the turn indicator:
        if (rotation) {
            // If clockwise rotation (true), increment the turn indicator:
            playerTurn++;
        } else {
            // Otherwise, decrement the turn indicator:
            playerTurn--;
        }
    }

    public void addToPot() {
        // Add a token to the pot:
        pot++;
    }

    public Deck getDrawDeck() {
        // Returns the draw deck:
        return drawDeck;
    }

    public Deck getDiscardDeck() {
        // Returns the discard deck:
        return discardDeck;
    }

    public ImageIcon getDiscardImage() {
        // Return the image of the card that is on top of the discard pile:
        return discardDeck.get(0).getImage();
    }

    public int getScore() {
        // Return the round's current score:
        return rScore;
    }

    public int getPot() {
        // Getter: return the number of tokens in the pot:
        return pot;
    }

    public Player getPlayer(int n) {
        // Return Player n:
        return playerList.get(n);
    }

    public int getPlayersTurn() {
        // Getter: return the index of the player who is currently playing:
        // FIXME: Make sure this works properly:
        return (playerTurn % 4);
    }

    public int getDealerTurn() {
        // Return the index of the player who is currently dealing:
        return (dealerTurn % 4);
    }

    public void printHands() {
        // FIXME: TEST: Print out each player's hands:
        for (Player p : playerList) {
            System.out.println(p.getName() + "'s hand: " + p.getHand());
        }
    }

    public void printDeckSizes() {
        // FIXME: TEST: Print out the deck sizes:
        System.out.println("Game deck size: " + drawDeck.size());
        System.out.println("Discard pile size: " + discardDeck.size());
    }

    @Override
    public String toString() {
        // Return a String containing the current player names and their token counts:

        String r = "";

        for (Player p : playerList) {
            r += (p + "\n");
        }

        return r;
    }

}
