/*
Adrien Abbey, CS-1181L-07, Feb. 25, 2022
Project 2: GUI-based Game
Visual Studio Code, Windows 10, Eclipse Temurin JDK/JRE 11

Design Requirements:
1. A game window implemented as a JFrame
2. Must provide instructions to the player
3. Must have at least two different visual elements (buttons, labels, text fields, etc.)
4. Must have user interaction which causes visual elements to change appearance
5. Should have some sort of score or goal, which should be displayed
6. Designed using good object-orientated coding principles

Ninety-Nine (card game):
TODO: This is based on the rules my family played with.  
TODO: I need to either clarify that or rename the card game.

How to win:
    - Each player starts with 3 tokens
    - Each player takes turns playing cards to a common discard pile
    - Each card played adds it's value to current round's score, up to 99
    - The goal of each player is to avoid losing
    - A player loses a round if the score goes over 99 on their turn
    - Upon losing, the player discards a token into the pot
    - If a player loses a round and has no tokens to throw into the pot, they can no longer play
    - Rounds keep going until there is only one player remaining
    - The last remaining player takes home the pot
Rules:
    - Select a random player to start.  This person is the dealer for the first round.
    - Shuffle a standard card deck, then deal 3 cards to each player, clockwise rotation
    - Dealer plays the first card on the deck, face up to a common discard pile (counting as their turn)
    - This card is the starting value for the round, whatever that might be.
    - Next player plays a card, if they can, then draws a card.
    - Keep playing until someone loses.
Special Cards, Card Values:
    - Kings: sets the score to 99, regardless of what it was
    - 10s: subtract 10 from the current score
    - 9s: hold the current score (aka zero points)
    - 4s: reverse back to the previous player (reversing rotation, order of player turns)
    - Aces count as 1 point
    - All others: numbered cards count as their face value, Qs and Js count as 10 points.

Basic Design:
    - 4 players (1 human, 3 AI)
    - Start with a simple UI
    - NinetyNine class (view), which has main class, handles UI
    - Game class (model), which handles game logic
    - Cards class, which creates a deck, shuffles it
    - Player class, which tracks each player's cards
    - HumanPlayer class (controller), which handles user interfacing
    - ComputerPlayer class (controller), which controls computer players

Considerations:
    - TODO: Single deck or two?  What happens if cards run out?

Attributions:
    - Playing card graphics (public domain) are from: 
        https://code.google.com/archive/p/vector-playing-cards/
*/

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class NinetyNine {

    /* Variables */
    private static int tokens = 3; // number of tokens players start with
    private static String deckType = "standard52"; // type of deck the game is played with
    private static int numDecks = 1; // how many decks to shuffle together
    public static Game game; // FIXME: Should this be public?
    private static ImageIcon p1c1Image; // human player's card images
    private static ImageIcon p1c2Image;
    private static ImageIcon p1c3Image;

    public static void main(String[] args) {

        // FIXME: Test code:

        // Create a new game:
        game = new Game(deckType, numDecks, new HumanPlayer("Dead Meat",
                tokens),
                new ComputerPlayer("HAL", tokens), new ComputerPlayer("SHODAN", tokens),
                new ComputerPlayer("GLaDOS", tokens));

        // Print the current game's player names and their tokens:
        System.out.println(game);

        // Three round test:
        for (int i = 0; i < 3; i++) {
            // Start a new round:
            game.newRound();

            // Print out the player's hands:
            game.printHands();

            // Print out the current deck sizes:
            game.printDeckSizes();
        }

        // Create the main window:
        new NinetyNine();
    }

    /* Window Constructor */
    private NinetyNine() {
        // Create the main window:
        JFrame gameWindow = new JFrame("Ninety Nine");

        // NOTE: I'm using BorderLayout for the main panel, as referenced here:
        // https://www.javatpoint.com/java-layout-manager

        // Create the game panels:
        JPanel mainPanel = new JPanel(); // Main window panel
        JPanel tablePanel = new JPanel(); // Center panel, has decks, game status, etc.
        JPanel humanPlayer = new JPanel(); // South panel, has player's hand, etc.
        JPanel aiPlayer1 = new JPanel(); // West panel, computer player 1
        JPanel aiPlayer2 = new JPanel(); // North panel, computer player 2
        JPanel aiPlayer3 = new JPanel(); // East panel, computer player 3

        // Add subpanels to the main panel:
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(humanPlayer, BorderLayout.SOUTH);
        mainPanel.add(aiPlayer1, BorderLayout.WEST);
        mainPanel.add(aiPlayer2, BorderLayout.NORTH);
        mainPanel.add(aiPlayer3, BorderLayout.EAST);

        // Using Card Images: https://stackoverflow.com/a/8334086
        // For simplicity's sake, I'm just going to use JLabel ImageIcons for card
        // images. Future implementations might implement animations, but that's a
        // future problem for future me.

        // FIXME: TEST: Try adding card images to the player's pane:

        // TODO: This might do better as a method, could be optimized.

        // For each of the player's hand, load the appropriate image file:
        // FIXME: Is there a better way to do this?
        p1c1Image = game.getPlayer(0).getHand().get(0).getImage(0);
        p1c2Image = game.getPlayer(0).getHand().get(1).getImage(1);
        p1c3Image = game.getPlayer(0).getHand().get(2).getImage(2);

        // Create objects for the player's panel:
        JLabel playerName = new JLabel(game.getPlayer(0).getName() + "'s hand:");
        JLabel p1card1 = new JLabel(p1c1Image);
        JLabel p1card2 = new JLabel(p1c2Image);
        JLabel p1card3 = new JLabel(p1c3Image);
        JLabel playerTokens = new JLabel("Tokens: " + game.getPlayer(0).getTokens());

        // Add those objects to the player's panel:
        humanPlayer.add(playerName);
        humanPlayer.add(p1card1);
        humanPlayer.add(p1card2);
        humanPlayer.add(p1card3);
        humanPlayer.add(playerTokens);

        // Add the main panel to the main window:
        gameWindow.add(mainPanel);

        // Setup the main window:
        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
