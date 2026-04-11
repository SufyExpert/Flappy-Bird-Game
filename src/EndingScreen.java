import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
//import java.io.IOException;
import javax.imageio.ImageIO;
//import java.io.BufferedWriter;
//import java.io.FileWriter;

public class EndingScreen extends JFrame implements Coordinates {
    private JLabel finish;
    private JPanel panel;
    private JLabel scoreLabel;
    private JButton exitButton;
    private Image backgroundImage;

    // Custom JPanel to draw the image background
    private class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public EndingScreen() {
        saveData(); //save record to the file
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("assets/Background.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize custom panel and set layout
        panel = new CustomPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add margins

        // Frame settings
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Game End");

        // Create the JLabel for 'finish'
        finish = new JLabel("Game Over");
        finish.setFont(finish.getFont().deriveFont(Font.PLAIN, 100));
        finish.setAlignmentX(Component.CENTER_ALIGNMENT);
        finish.setForeground(Color.DARK_GRAY);

        // Create the JLabel for 'score'
        scoreLabel = new JLabel("Your Score: " + Moveable.score);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(Font.PLAIN, 50));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(Color.DARK_GRAY);

        // Create the exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(exitButton.getFont().deriveFont(Font.PLAIN, 30));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add components to panel
        panel.add(finish);
        panel.add(Box.createRigidArea(new Dimension(0, 50))); // Add some vertical space
        panel.add(scoreLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30))); // Add some vertical space
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some vertical space
        panel.add(exitButton);

        // Set content pane and make frame visible
        setContentPane(panel);
        setVisible(true);
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("assets/data.txt", true))) {
            // Append player data to the file in the format "name,ID,score"
            writer.write(InfoForm.playerName + "," + InfoForm.studentID + "," + Moveable.score);
            writer.newLine(); // Add a new line for the next entry
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
