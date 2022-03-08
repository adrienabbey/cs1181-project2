// Adrien Abbey, CS-1181L-07, Mar. 3, 2022
// Notification class for Project 2

// This class creates a notification window to inform the player of events.

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Notification {

    /* Constructor */
    public Notification(String title, String message) {
        // Create a new window with the given title and message:

        // Create a new JFrame (window):
        JFrame notifyWindow = new JFrame(title);

        // Create a JPanel to hold window contents:
        JPanel notifyPanel = new JPanel();

        // Create a label to display the notification message:
        JLabel notifyLabel = new JLabel(message);

        // Add components as appropriate:
        notifyPanel.add(notifyLabel);
        notifyWindow.add(notifyPanel);

        // Configure the window:
        notifyWindow.pack(); // Auto-size to contents
        notifyWindow.setVisible(true); // Make it visible
        notifyWindow.setLocationRelativeTo(null); // Center on screen
        notifyWindow.setResizable(false); // Not resizable
        notifyWindow.setAlwaysOnTop(true); // Always on top
    }
}
