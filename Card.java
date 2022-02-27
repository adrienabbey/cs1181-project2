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

public class Card {

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

    public String getImageFilePath() {
        // Return the full path for the given card's image file:
        return ("./PNG-cards-1.3/" + rank.filename + suit.filename + ".png");
    }

    public ImageIcon getImage(int n) {
        // Return the card's image with the given dimensions:
        // Source: https://stackoverflow.com/a/18335435
        ImageIcon imageIcon = new ImageIcon();
        try {
            imageIcon = new ImageIcon(
                    ImageIO.read(new File(NinetyNine.game.getPlayer(0).getHand().get(n).getImageFilePath())));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Resize the image:
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(100, 145, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);

        return imageIcon;
    }

    @Override
    public String toString() {
        // Return the name of the card as a string:
        return (rank.label + " of " + suit.label);
    }

}
