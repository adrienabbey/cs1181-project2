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
    - FIXME: Card backs and arrow images are my own creation.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
    private static ImageIcon cardBack0; // card back's image and various rotations
    private static ImageIcon cardBack1; // FIXME: can I do this better?
    private static ImageIcon cardBack2;
    private static ImageIcon cardBack3;
    private static ImageIcon upArrow;
    private static ImageIcon downArrow;
    private static ImageIcon rightArrow;
    private static ImageIcon leftArrow;
    private static ImageIcon turnImage;

    public static void main(String[] args) {

        // FIXME: Test code:

        // Create a new game:
        game = new Game(deckType, numDecks, new HumanPlayer("Dead Meat",
                tokens),
                new ComputerPlayer("HAL", tokens), new ComputerPlayer("SHODAN", tokens),
                new ComputerPlayer("GLaDOS", tokens));

        // Start a new game:
        game.newRound();

        // Create the main window:
        new NinetyNine();
    }

    /* Window Constructor */
    private NinetyNine() {
        // Create the main window:
        JFrame gameWindow = new JFrame("Ninety Nine");

        // FIXME: Load the game images:
        try {
            // FIXME: Changing these to the same image:
            cardBack0 = new ImageIcon(ImageIO.read(new File("./cardbacks/disappointment0.png")));
            cardBack1 = new ImageIcon(ImageIO.read(new File("./cardbacks/disappointment0.png")));
            cardBack2 = new ImageIcon(ImageIO.read(new File("./cardbacks/disappointment0.png")));
            cardBack3 = new ImageIcon(ImageIO.read(new File("./cardbacks/disappointment0.png")));
            upArrow = new ImageIcon(ImageIO.read(new File("./cardbacks/upArrow.png")));
            downArrow = new ImageIcon(ImageIO.read(new File("./cardbacks/downArrow.png")));
            rightArrow = new ImageIcon(ImageIO.read(new File("./cardbacks/rightArrow.png")));
            leftArrow = new ImageIcon(ImageIO.read(new File("./cardbacks/leftArrow.png")));
            turnImage = new ImageIcon();

        } catch (IOException e) {
            // TODO Auto-generated catch block
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

        // Give panels borders:
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add panels to the main panel:
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(c1panel, BorderLayout.WEST);
        mainPanel.add(c2panel, BorderLayout.NORTH);
        mainPanel.add(c3panel, BorderLayout.EAST);

        // FIXME: Source for image syntax: https://stackoverflow.com/a/8334086
        // For simplicity's sake, I'm just going to use JLabel ImageIcons for card
        // images. Future implementations might implement animations, but that's a
        // future problem for future me.

        // FIXME: TEST: Try adding card images to the player's pane:

        // TODO: This might do better as a method, could be optimized.

        // For each of the player's hand, load the appropriate image file:
        // FIXME: Is there a better way to do this?
        p1c1Image = game.getPlayer(0).getHand().get(0).getImage();
        p1c2Image = game.getPlayer(0).getHand().get(1).getImage();
        p1c3Image = game.getPlayer(0).getHand().get(2).getImage();

        // Create objects for the player's panel:
        JLabel playerName = new JLabel(game.getPlayer(0).getName() + "'s hand:");
        JLabel p1card1 = new JLabel(p1c1Image);
        JLabel p1card2 = new JLabel(p1c2Image);
        JLabel p1card3 = new JLabel(p1c3Image);
        JLabel playerTokens = new JLabel("Tokens: " + game.getPlayer(0).getTokens());

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

        // TODO: Can I turn each computer layout into a reusable class object?

        // Create the computer player's UI objects:
        JLabel c1name = new JLabel(game.getPlayer(1).getName());
        JLabel c2name = new JLabel(game.getPlayer(2).getName());
        JLabel c3name = new JLabel(game.getPlayer(3).getName());
        JLabel c1tokens = new JLabel("Tokens: " + game.getPlayer(1).getTokens());
        JLabel c2tokens = new JLabel("Tokens: " + game.getPlayer(2).getTokens());
        JLabel c3tokens = new JLabel("Tokens: " + game.getPlayer(3).getTokens());
        JLabel c1card1 = new JLabel(cardBack1);
        JLabel c1card2 = new JLabel(cardBack1);
        JLabel c1card3 = new JLabel(cardBack1);
        JLabel c2card1 = new JLabel(cardBack2);
        JLabel c2card2 = new JLabel(cardBack2);
        JLabel c2card3 = new JLabel(cardBack2);
        JLabel c3card1 = new JLabel(cardBack3);
        JLabel c3card2 = new JLabel(cardBack3);
        JLabel c3card3 = new JLabel(cardBack3);

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

        // Configure subpanel layouts:
        // c1hand.setLayout(new BoxLayout(c1hand, BoxLayout.Y_AXIS));
        // c3hand.setLayout(new BoxLayout(c3hand, BoxLayout.Y_AXIS));

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

        // FIXME: Set the turnIndicator to prevent compiler complaints:
        // turnImage = downArrow;

        // Determine which turn indicator image to use:
        switch (game.whoseTurn()) {
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
            default:
                System.err.println("ERROR: Invalid turn indicator.  This shouldn't happen.");
                break;
        }

        // Create the table UI objects:
        JLabel scoreLabel = new JLabel("Score: " + game.getScore());
        JLabel drawPileLabel = new JLabel(cardBack0);
        JLabel potLabel = new JLabel("Pot: " + game.getPot());
        JLabel discardPileLabel = new JLabel(game.getDiscardImage());
        JLabel turnIndicator = new JLabel(turnImage);
        // TODO: Properly update the display of the discard pile!
        // TODO: Add a turn indicator, update as needed!

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
        // gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
