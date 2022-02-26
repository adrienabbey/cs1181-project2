// Adrien Abbey, CS-1181L-07, Jan 25, 2022

// Game class for Project 2
// Game logic (model) for the card game NinetyNine

import java.util.ArrayList;
import java.util.Random;

public class Game {

    /* Fields */
    private int pot; // How many tokens are in the pot
    private ArrayList<Player> playerList = new ArrayList<>();
    private int pTurn; // Tracks which player's turn it is.
    private Random rng = new Random();
    private Deck gameDeck; // Game constructor will create a the deck with provided properties
    private Deck discardDeck;

    /* Constructor */
    public Game(String deckType, int deckCount, Player player1, Player player2, Player player3, Player player4) {
        // Start a new game with the given players:

        // Create a new deck to draw from:
        gameDeck = new Deck(deckType, deckCount);
        discardDeck = new Deck();

        // The game starts with an empty pot:
        pot = 0;

        // Add players to the player list:
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        // Randomly set which player's turn it is:
        pTurn = rng.nextInt(4); // returns 0 to 3
    }

    /* Methods */

    public void newRound() {
        // New round, new player's turn:
        pTurn++;

        // Return all the cards in each player's hand back to the game deck:
        for (Player p : playerList) {
            p.getHand().emptyInto(gameDeck);
        }

        // Return all the discarded cards to the game deck:
        discardDeck.emptyInto(gameDeck);

        // Reshuffle the deck:
        gameDeck.shuffle();

        // Deal a card to each player 3 times:
        for (int i = 0; i < 3; i++) {
            // For each player:
            for (Player p : playerList) {
                // Deal a card to the player:
                p.drawCard(gameDeck);
            }
        }
    }

    public boolean drawFromDeck(Player p) {
        // Draw a card from the deck and add it to the given player's hand, if possible:
        // Return true if successful, false there's no cards left in the deck.

        // If there's a card left in the deck to draw:
        if (gameDeck.size() > 0) {
            p.drawCard(gameDeck);
            return true;
        } else {
            return false;
        }
    }

    public void addToPot() {
        // Add a token to the pot:
        pot++;
    }

    public int getPot() {
        // Getter: return the number of tokens in the pot:
        return pot;
    }

    public Player whoseTurn() {
        // Getter: return the Player who is currently playing:
        // FIXME: Make sure this works properly:
        return playerList.get(4 % pTurn);
    }

    public void printHands() {
        // FIXME: TEST: Print out each player's hands:
        for (Player p : playerList) {
            System.out.println(p.getHand());
        }
    }

    public void printDeckSizes() {
        // FIXME: TEST: Print out the deck sizes:
        System.out.println("Game deck size: " + gameDeck.size());
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
