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
    - Aces count as 1 or 11 points.  NOTE: I'm automatically selecting 11 when safe, 1 if not.
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

Attributions:
    - Playing card graphics (public domain) are from: 
        https://code.google.com/archive/p/vector-playing-cards/
    - Card back image is by David Bellot - Berkeley, CA, USA 08/12/2005 - David's web - 
        David's web, LGPL, https://commons.wikimedia.org/w/index.php?curid=498454
    - Arrow images are my own creation.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class NinetyNine {

    /* Fields */
    private static int tokens = 3; // number of tokens players start with
    private static String deckType = "standard52"; // type of deck the game is played with
    private static int numDecks = 2; // how many decks to shuffle together; I'm using 2 decks since it's not
                                     // impossible to burn through the entire draw deck in a single round
    public static Game game; // I'm making the game object public so that other classes can refer to it. In a
                             // normal card game, everyone playing has access to basic game information.

    private static HumanPlayer player = new HumanPlayer("Player", tokens);
    private static ComputerPlayer computer1 = new ComputerPlayer("Computer 1", tokens);
    private static ComputerPlayer computer2 = new ComputerPlayer("Computer 2", tokens);
    private static ComputerPlayer computer3 = new ComputerPlayer("Computer 3", tokens);

    // Note: I've made these image objects class fields in order to allow
    // manipulation by other methods within this class.
    private static ImageIcon pCard1Image; // human player's card images
    private static ImageIcon pCard2Image;
    private static ImageIcon pCard3Image;
    private static ImageIcon cardBack; // card back's image and various rotations
    private static ImageIcon blank; // blank image holder for empty hands
    private static ImageIcon upArrow; // Arrows for the turn indicator
    private static ImageIcon downArrow;
    private static ImageIcon rightArrow;
    private static ImageIcon leftArrow;
    private static ImageIcon turnImage;

    // These Swing objects are class fields as they need to be updated by the UI
    // update method in this class.
    private static JLabel discardPileLabel;
    private static JLabel scoreLabel;
    private static JLabel turnIndicator;
    private static JButton p1card1;
    private static JButton p1card2;
    private static JButton p1card3;
    private static JLabel playerTokens;
    private static JLabel c1tokens;
    private static JLabel c2tokens;
    private static JLabel c3tokens;
    private static JLabel potLabel;
    private static JLabel c1card1;
    private static JLabel c1card2;
    private static JLabel c1card3;
    private static JLabel c2card1;
    private static JLabel c2card2;
    private static JLabel c2card3;
    private static JLabel c3card1;
    private static JLabel c3card2;
    private static JLabel c3card3;

    public static void main(String[] args) {

        // Create a new game:
        game = new Game(deckType, numDecks, player,
                computer1, computer2,
                computer3);

        // Start the initial round of the game (necessarily before creating the window):
        game.newRound();

        // Create the main window:
        new NinetyNine();

        // Open the instruction window too:
        new Instructions();

        // Keep playing until only one player remains:
        while (true) {
            // Track how many players can play:
            int playersPlaying = 0;

            // Determine how many players can play:
            for (Player p : game.getPlayers()) {
                if (p.isPlaying()) {
                    playersPlaying++;
                }
            }

            // If there's more than one player playing, start the game loop:
            if (playersPlaying > 1) {
                // There's still at least two players playing, keep playing.

                // Start a new round before starting the game loop
                game.newRound();
                // Start the game loop:
                game.gameLoop();
            } else {
                // Only one player remains, the victor!
                Player winner = player; // Defaulting to human player, just to prevent compiler complaint about
                                        // uninitialized variable.
                for (Player p : game.getPlayers()) {
                    if (p.isPlaying()) {
                        // There's only 1 player playing, thus they will be the winner:
                        winner = p;
                    }
                }
                // Let the user know who won:
                System.out.println(winner.getName() + " is the winner!  Congrats!");
                new Notification("Game Over", (winner.getName() + " is the winner!  Congrats!"));
                break;
            }
        }
    }

    /* Window Constructor */
    private NinetyNine() {
        // Create the main window:
        JFrame gameWindow = new JFrame("Ninety Nine");

        // Try to load images:
        try {
            cardBack = new ImageIcon(ImageIO.read(new File("images/cardback.png")));
            blank = new ImageIcon(ImageIO.read(new File("images/blank.png")));
            upArrow = new ImageIcon(ImageIO.read(new File("images/upArrow.png")));
            downArrow = new ImageIcon(ImageIO.read(new File("images/downArrow.png")));
            rightArrow = new ImageIcon(ImageIO.read(new File("images/rightArrow.png")));
            leftArrow = new ImageIcon(ImageIO.read(new File("images/leftArrow.png")));
            turnImage = new ImageIcon(); // placeholder that updates to show which user is currently playing their turn
        } catch (IOException e) {
            e.printStackTrace();
        }

        // NOTE: I'm using BorderLayout for the main panel, as referenced here:
        // https://www.javatpoint.com/java-layout-manager

        // Create the game panels:
        JPanel mainPanel = new JPanel(); // Main window panel
        JPanel centerPanel = new JPanel(); // Center panel, has decks, game status, etc.
        JPanel playerPanel = new JPanel(); // South panel, has player's hand, etc.
        JPanel c1panel = new JPanel(); // West panel, computer player 1
        JPanel c2panel = new JPanel(); // North panel, computer player 2
        JPanel c3panel = new JPanel(); // East panel, computer player 3

        // Setup panel layouts:
        mainPanel.setLayout(new BorderLayout());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        c1panel.setLayout(new BoxLayout(c1panel, BoxLayout.Y_AXIS));
        c2panel.setLayout(new BoxLayout(c2panel, BoxLayout.Y_AXIS));
        c3panel.setLayout(new BoxLayout(c3panel, BoxLayout.Y_AXIS));

        // Set a border around the center panel for clarity:
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add panels to the main panel:
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(c1panel, BorderLayout.WEST);
        mainPanel.add(c2panel, BorderLayout.NORTH);
        mainPanel.add(c3panel, BorderLayout.EAST);

        // Source for image syntax: https://stackoverflow.com/a/8334086
        // For simplicity's sake, I'm just going to use JLabel ImageIcons for card
        // images.

        // Create objects for the player's panel:
        JLabel playerName = new JLabel(game.getPlayer(0).getName() + "'s hand:");
        p1card1 = new JButton(pCard1Image);
        p1card2 = new JButton(pCard2Image);
        p1card3 = new JButton(pCard3Image);
        playerTokens = new JLabel("Tokens: " + game.getPlayer(0).getTokens());

        // For each of the player's hand, load the appropriate image file:
        updatePlayerCards();

        // Add listeners to the player's cards:
        p1card1.addActionListener(e -> {
            // If the game is currently waiting for user input:
            // This prevents the user from commiting to playing a card before their turn.
            if (player.isWaiting()) {
                // Then their card selection is valid:
                player.playCard(0);
            }
        });
        p1card2.addActionListener(e -> {
            // If the game is currently waiting for user input:
            if (player.isWaiting()) {
                // Then their card selection is valid:
                player.playCard(1);
            }
        });
        p1card3.addActionListener(e -> {
            // If the game is currently waiting for user input:
            if (player.isWaiting()) {
                // Then their card selection is valid:
                player.playCard(2);
            }
        });

        // Create sub-panels for the player panel:
        JPanel pHeader = new JPanel();
        JPanel pCards = new JPanel();
        JPanel pFooter = new JPanel();

        // Add the player sub-panels:
        playerPanel.add(pHeader);
        playerPanel.add(pCards);
        playerPanel.add(pFooter);

        // Add those objects to the player's panel:
        pHeader.add(playerName);
        pCards.add(p1card1);
        pCards.add(p1card2);
        pCards.add(p1card3);
        pFooter.add(playerTokens);

        // Create the computer player's UI objects:
        JLabel c1name = new JLabel(game.getPlayer(1).getName());
        JLabel c2name = new JLabel(game.getPlayer(2).getName());
        JLabel c3name = new JLabel(game.getPlayer(3).getName());
        c1tokens = new JLabel("Tokens: " + game.getPlayer(1).getTokens());
        c2tokens = new JLabel("Tokens: " + game.getPlayer(2).getTokens());
        c3tokens = new JLabel("Tokens: " + game.getPlayer(3).getTokens());
        c1card1 = new JLabel(cardBack);
        c1card2 = new JLabel(cardBack);
        c1card3 = new JLabel(cardBack);
        c2card1 = new JLabel(cardBack);
        c2card2 = new JLabel(cardBack);
        c2card3 = new JLabel(cardBack);
        c3card1 = new JLabel(cardBack);
        c3card2 = new JLabel(cardBack);
        c3card3 = new JLabel(cardBack);

        // Create subpanels for each computer player:
        JPanel c1header = new JPanel();
        JPanel c2header = new JPanel();
        JPanel c3header = new JPanel();
        JPanel c1hand = new JPanel();
        JPanel c2hand = new JPanel();
        JPanel c3hand = new JPanel();
        JPanel c1footer = new JPanel();
        JPanel c2footer = new JPanel();
        JPanel c3footer = new JPanel();

        // Add the computer player's objects to their respective panels:
        c1header.add(c1name);
        c1hand.add(c1card1);
        c1hand.add(c1card2);
        c1hand.add(c1card3);
        c1footer.add(c1tokens);

        c2header.add(c2name);
        c2hand.add(c2card1);
        c2hand.add(c2card2);
        c2hand.add(c2card3);
        c2footer.add(c2tokens);

        c3header.add(c3name);
        c3hand.add(c3card1);
        c3hand.add(c3card2);
        c3hand.add(c3card3);
        c3footer.add(c3tokens);

        // Add the sub-panels to each player's field:
        c1panel.add(c1header);
        c1panel.add(c1hand);
        c1panel.add(c1footer);
        c2panel.add(c2header);
        c2panel.add(c2hand);
        c2panel.add(c2footer);
        c3panel.add(c3header);
        c3panel.add(c3hand);
        c3panel.add(c3footer);

        // Set the turn indicator as appropriate:
        switch (game.getPlayerIndex()) {
            case 0:
                turnImage = downArrow;
                break;
            case 1:
                turnImage = leftArrow;
                break;
            case 2:
                turnImage = upArrow;
                break;
            case 3:
                turnImage = rightArrow;
                break;
        }

        // Create the table UI objects:
        scoreLabel = new JLabel("Score: " + game.getScore());
        JLabel drawPileLabel = new JLabel(cardBack);
        potLabel = new JLabel("Pot: " + game.getPot());
        discardPileLabel = new JLabel(game.getDiscardImage());
        turnIndicator = new JLabel(turnImage);

        // Create sub-panels for the table panel:
        JPanel tHeader = new JPanel();
        JPanel tDecks = new JPanel();
        JPanel tFooter = new JPanel();

        // Add the table objects to the table's sub-panels:
        tHeader.add(scoreLabel);
        tDecks.add(discardPileLabel);
        tDecks.add(turnIndicator);
        tDecks.add(drawPileLabel);
        tFooter.add(potLabel);

        // Add the table sub-panels to the table panel:
        centerPanel.add(tHeader);
        centerPanel.add(tDecks);
        centerPanel.add(tFooter);

        // Add the main panel to the main window:
        gameWindow.add(mainPanel);

        // Setup the main window:
        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void updateUI() {
        // Update the UI with new images, scores, etc:
        // Player's cards, turn indicator, discard pile, tokens, etc.

        // Update the turn indicator and the player's card images:
        updateTurnIndicator();
        updatePlayerCards();

        // Update the discard pile card:
        discardPileLabel.setIcon(game.getDiscardImage());

        // Update the round score and pot size:
        scoreLabel.setText("Score: " + game.getScore());
        potLabel.setText("Pot: " + game.getPot());

        // Update each player's token count:
        // If a player is out, don't display tokens:
        if (player.isPlaying()) {
            playerTokens.setText("Tokens: " + player.getTokens());
        } else {
            playerTokens.setText("OUT");
        }

        if (computer1.isPlaying()) {
            c1tokens.setText("Tokens: " + computer1.getTokens());
        } else {
            c1tokens.setText("OUT");
            // Also hide their cards:
            c1card1.setIcon(blank);
            c1card2.setIcon(blank);
            c1card3.setIcon(blank);
        }

        if (computer2.isPlaying()) {
            c2tokens.setText("Tokens: " + computer2.getTokens());
        } else {
            c2tokens.setText("OUT");
            // Also hide their cards:
            c2card1.setIcon(blank);
            c2card2.setIcon(blank);
            c2card3.setIcon(blank);
        }

        if (computer3.isPlaying()) {
            c3tokens.setText("Tokens: " + computer3.getTokens());
        } else {
            c3tokens.setText("OUT");
            // Also hide their cards:
            c3card1.setIcon(blank);
            c3card2.setIcon(blank);
            c3card3.setIcon(blank);
        }

    }

    private static void updateTurnIndicator() {
        // Update the turn indicator icon.

        // Determine which turn indicator image to use:
        switch (game.getPlayerIndex()) {
            case 0:
                turnIndicator.setIcon(downArrow);
                break;
            case 1:
                turnIndicator.setIcon(leftArrow);
                break;
            case 2:
                turnIndicator.setIcon(upArrow);
                break;
            case 3:
                turnIndicator.setIcon(rightArrow);
                break;
        }
    }

    private static void updatePlayerCards() {
        // Update the images of the player's cards:
        // But only if the player is still in the game:
        if (player.isPlaying()) {
            p1card1.setIcon(player.getHand().get(0).getImage());
            p1card2.setIcon(player.getHand().get(1).getImage());
            p1card3.setIcon(player.getHand().get(2).getImage());
        } else {
            p1card1.setIcon(blank);
            p1card2.setIcon(blank);
            p1card3.setIcon(blank);
        }
    }
}
