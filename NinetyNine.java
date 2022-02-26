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
*/

class NinetyNine {

    /* Variables */
    private static int tokens = 3; // number of tokens players start with
    private static String deckType = "standard52"; // type of deck the game is played with
    private static int numDecks = 1; // how many decks to shuffle together

    public static void main(String[] args) {
        // FIXME: Test code:

        // Create new players:
        Game game = new Game(deckType, numDecks, new HumanPlayer("Dead Meat", tokens),
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
    }
}
