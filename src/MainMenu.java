import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenu extends JFrame implements Coordinates {
    private JButton playButton;
    private JButton dataButton;
    private JButton aboutButton;
    private JButton exitButton;
    private JLabel titleLabel;
    private JPanel panel;
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

    public MainMenu() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("assets/Background.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize components
        titleLabel = new JLabel("Flappy  Bird");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.PLAIN, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.DARK_GRAY);

        panel = new CustomPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add margins

        playButton = new JButton("Play");
        dataButton = new JButton("Data");
        aboutButton = new JButton("Developers");
        exitButton = new JButton("Exit");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new GameWindow();
            }
        });

        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DataForm();
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new OwnersForm();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Frame settings
        setTitle("Main Menu");
        setSize(windowWidth, windowHeight);  // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Add components to panel with GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        for(int x = 1 ; x<10 ; x++) {
            gbc.gridy = x;
            panel.add(new JLabel(), gbc); // Empty row for spacing
        }

        gbc.gridy = 12;
        panel.add(playButton, gbc);

        gbc.gridy = 13;
        panel.add(new JLabel(), gbc);

        gbc.gridy = 14;
        panel.add(new JLabel(), gbc);

        gbc.gridy = 15;
        panel.add(dataButton, gbc);

        gbc.gridy = 16;
        panel.add(new JLabel(), gbc);

        gbc.gridy = 17;
        panel.add(new JLabel(), gbc);

        gbc.gridy = 18;
        panel.add(aboutButton, gbc);

        gbc.gridy = 19;
        panel.add(new JLabel(), gbc);

        gbc.gridy = 20;
        panel.add(new JLabel(), gbc);

        gbc.gridy = 21;
        panel.add(exitButton, gbc);

        // Set content pane and make frame visible
        setContentPane(panel);
        setVisible(true);
    }
}
