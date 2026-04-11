import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OwnersForm extends JFrame implements Coordinates {
    private JLabel titleLabel;
    private JPanel panel;
    private JLabel ownerLabel1;
    private JLabel ownerLabel2;
    private JLabel ownerLabel3;
    private JButton goBackButton;
    private Image backgroundImage;

    public OwnersForm() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("src/Background.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize components
        titleLabel = new JLabel("        Developers List");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.PLAIN, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.DARK_GRAY);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add margins

        // Create owner labels
        ownerLabel1 = new JLabel("Sufyan Ahmad            ( FA23-BCS-161 )");
        ownerLabel1.setFont(ownerLabel1.getFont().deriveFont(Font.PLAIN, 30));
        ownerLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        ownerLabel2 = new JLabel("Waqas ul Hassan       ( FA23-BCS-167 )");
        ownerLabel2.setFont(ownerLabel2.getFont().deriveFont(Font.PLAIN, 30));
        ownerLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        ownerLabel3 = new JLabel("Muhammad Ramzan  ( FA23-BCS-126 )");
        ownerLabel3.setFont(ownerLabel3.getFont().deriveFont(Font.PLAIN, 30));
        ownerLabel3.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel ownersPanel = new JPanel(new GridLayout(3, 1)); // Create a panel with GridLayout
        ownersPanel.setOpaque(false); // Set panel to be transparent
        ownersPanel.add(ownerLabel1);
        ownersPanel.add(ownerLabel2);
        ownersPanel.add(ownerLabel3);

        // Create "Go Back" button
        goBackButton = new JButton("Go Back");
        goBackButton.setFont(goBackButton.getFont().deriveFont(Font.PLAIN, 24));
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainMenu();
            }
        });

        // Frame settings
        setTitle("Owners Form");
        setSize(windowWidth, windowHeight); // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Add components to panel
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(ownersPanel, BorderLayout.CENTER); // Add the panel with GridLayout
        panel.add(goBackButton, BorderLayout.SOUTH);

        // Set content pane and make frame visible
        setContentPane(panel);
        setVisible(true);
    }
}
