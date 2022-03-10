// Adrien Abbey, CS-1181L-07, Feb. 25, 2022
// Deck.class for Project 2

// Note: This class may be useful for future projects.  I'm aiming to keep 
// this class both generic and reusable.

// Note: I'm pulling Rank and Suit enums from their relevant classes.  This 
// allows the enums to be used by other classes.

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card implements Comparable<Card> {

    /* Fields */

    private final Suit suit;
    private final Rank rank;

    /* Constructors */

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /* Methods */

    public int getRankValue() {
        // Return the value of the card's rank:
        return rank.value;
    }

    public String getRankString() {
        // Return the card's rank:
        return rank.label;
    }

    public String getSuitString() {
        // Return the card's suit:
        return suit.label;
    }

    public int getScoreValue() {
        // Returns the score value of the card.
        // NOTE: This is relevant only to the game of Ninety-Nine!
        // 10s return -10, 9s and 4s return 0, and Kings return 99.
        // All other cards return their point values.

        // If this card is a king:
        if (this.rank.value == 13) {
            // Return 99:
            return 99;
        } else if (this.rank.value == 10) {
            // If a 10, return -10:
            return -10;
        } else if (this.rank.value == 9 || this.rank.value == 4) {
            // If a 9 or a 4, return 0:
            return 0;
        } else {
            // For all other cards, return their rank value:
            return this.rank.value;
        }
    }

    public String getImageFilePath() {
        // Return the full path for the given card's image file:
        return ("images/" + rank.filename + suit.filename + ".png");
    }

    public ImageIcon getImage() {
        // Return the card's image, resized to 100 by 145 pixels:
        // Source: https://stackoverflow.com/a/18335435
        ImageIcon imageIcon = new ImageIcon();
        try {
            imageIcon = new ImageIcon(ImageIO.read(new File(this.getImageFilePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Resize the image:
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(100, 145, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);

        return imageIcon;
    }

    @Override
    public int compareTo(Card other) {
        // This comparable method override is intended to provide ComputerPlayers with a
        // way to sort their current hand. Cards are sorted based on the desirability to
        // discard, where high rank non-special cards are most desirable to discard,
        // while special cards should be kept as long as possible.

        // NOTE: This is specific to the card game Ninety-Nine!

        // Note: for sake of randomness and simplicity, all special cards have the same
        // compare values.

        // If this card is a special card:
        if (this.rank.value == 13 || this.rank.value == 10 || this.rank.value == 9 || this.rank.value == 4) {
            // If the other card is also special:
            if (other.rank.value == 13 || other.rank.value == 10 || other.rank.value == 9 || other.rank.value == 4) {
                // Then these cards have the same value:
                return 0;
            } else {
                // Otherwise, the other card is not special, and therefore more desirable to
                // discard:
                return 1;
            }
        } else {
            // If this card is not special, but the other card is special:
            if (other.rank.value == 13 || other.rank.value == 10 || other.rank.value == 9 || other.rank.value == 4) {
                // Then this non-special card is more desirable to discard than the other
                // special card:
                return -1;
            } else {
                // Otherwise, both cards are non-special, so compare their rank values:
                if (this.rank.value > other.rank.value) {
                    // If this card has a higher rank value than the other card, then this card has
                    // a higher discard desirability:
                    return -1;
                } else if (this.rank.value < other.rank.value) {
                    // If this card has a lower rank value than the other card, then this card has a
                    // lower discard desirability:
                    return 1;
                } else {
                    // Otherwise both cards have the same value:
                    return 0;
                }
            }
        }
    }

    @Override
    public String toString() {
        // Return the name of the card as a string:
        return (rank.label + " of " + suit.label);
    }

}
