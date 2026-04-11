import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class InfoForm extends JFrame implements Coordinates {
    static String playerName;
    static String studentID;

    private JLabel titleLabel;
    private JPanel panel;
    private JLabel playerInfoLabel;
    private JTextField nameField;
    private JTextField idField;
    private JButton submitButton;
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

    public InfoForm() {
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

        playerInfoLabel = new JLabel("                  Player Info");
        playerInfoLabel.setFont(playerInfoLabel.getFont().deriveFont(Font.PLAIN, 36));
        playerInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerInfoLabel.setForeground(Color.DARK_GRAY);

        nameField = new JTextField(10);
        idField = new JTextField(10);

        submitButton = new JButton("Submit");
        submitButton.setFont(submitButton.getFont().deriveFont(Font.PLAIN, 24));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameField.getText();
                studentID = idField.getText();
                if (isValidID(studentID)) {
                    setVisible(false);
                    new MainMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Invalid ID format! (Sample: FA00-BCS-000)");
                }
            }
        });

        // Frame settings
        setTitle("Player Info");
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

        gbc.gridy = 1;
        panel.add(new JLabel(), gbc); // Empty row for spacing

        gbc.gridy = 2;
        panel.add(new JLabel(), gbc); // Empty row for spacing

        gbc.gridy = 3;
        panel.add(new JLabel(), gbc); // Empty row for spacing

        gbc.gridy = 4;
        panel.add(playerInfoLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 5;
        panel.add(createLabel("Player Name:"), gbc);

        gbc.gridx = 1;
        panel.add(createSmallTextField(nameField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(createLabel("Student ID (Sample: FA00-BCS-000):"), gbc);

        gbc.gridx = 1;
        panel.add(createSmallTextField(idField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        // Set content pane and make frame visible
        setContentPane(panel);
        setVisible(true);
    }

    // Method to create a label with large font
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 24));
        return label;
    }

    // Method to create a text field with smaller font
    private JTextField createSmallTextField(JTextField textField) {
        textField.setFont(textField.getFont().deriveFont(Font.PLAIN, 16));
        return textField;
    }

    // Method to validate the student ID format
    private boolean isValidID(String id) {
        // Regular expression for the format: FA23-BCS-161
        String regex = "^[A-Za-z]{2}\\d{2}-[A-Za-z]{3}-\\d{3}$";
        return id.matches(regex);
    }
}
