// Adrien Abbey, CS-1181L-07, Jan 25, 2022

// Game class for Project 2
// Game logic (model) for the card game NinetyNine

import java.util.ArrayList;
import java.util.Random;

public class Game {

    /* Fields */
    private int pot; // How many tokens are in the pot
    private ArrayList<Player> playerList;
    private int pTurn; // Tracks which player's turn it is.
    private Random rng = new Random();

    /* Constructor */
    public Game(Player player1, Player player2, Player player3, Player player4) {
        // Start a new game with the given players:

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
        return playerList.get(pTurn);
    }

}
