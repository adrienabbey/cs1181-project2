// Adrien Abbey, CS-1181L-07, Mar. 7, 2022
// Instructions class for Project 2

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Instructions {
    public Instructions() {
        // Creates a window giving the player instructions on how to play Ninety Nine:

        // Create a new JFrame (window):
        JFrame instructionFrame = new JFrame("Instructions");

        // Create a main panel to hold UI objects:
        JPanel instructionPanel = new JPanel();

        // Set the main panel layout:
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));

        // Load instructions from a text file:
        // https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java
        String instructionString = "";
        try {
            // Load the text file to a FileReader, which then uses a BufferedReader to pull
            // lines efficiently:
            FileReader instructionReader = new FileReader("Instructions.txt");
            BufferedReader instrBR = new BufferedReader(instructionReader);

            String line;
            // While there are file contents to pull:
            while ((line = instrBR.readLine()) != null) {
                instructionString += line + "\n";
            }

            instrBR.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a text area to hold all the instructions:
        JTextArea instructionText = new JTextArea(instructionString);
        instructionText.setWrapStyleWord(true);
        instructionText.setLineWrap(true);
        // https://stackoverflow.com/a/8792905
        instructionText.setBorder(BorderFactory.createCompoundBorder(instructionText.getBorder(),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        // FIXME: Set the appropriate size! Make it uneditable. Load the instructions
        // from a text file?

        // Create a button to close the instruction window:
        // JButton okayButton = new JButton("Okay"); // FIXME: Make this close the
        // window.

        // Add the components to where they belong:
        instructionPanel.add(instructionText);
        // instructionPanel.add(okayButton);
        instructionFrame.add(instructionPanel);

        // Adjust the window settings:
        // instructionFrame.pack();
        instructionFrame.setSize(400, 400);
        instructionFrame.setVisible(true);
        instructionFrame.setLocationRelativeTo(null);
        instructionFrame.setResizable(false);
        // instructionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
