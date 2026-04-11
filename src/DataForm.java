import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DataForm extends JFrame implements Coordinates {
    private JLabel titleLabel;
    private JPanel panel;
    private JTable dataTable;
    private JScrollPane scrollPane;
    private JButton goBackButton;
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

    public DataForm() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("assets/Background.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize components
        titleLabel = new JLabel("           Score Board");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.PLAIN, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.DARK_GRAY);

        panel = new CustomPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Add margins

        // Read data from file and populate the table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Player Name");
        model.addColumn("Student ID");
        model.addColumn("Score");
        readDataFromFile("assets/data.txt", model);

        // Create table with model
        dataTable = new JTable(model);
        dataTable.setFont(dataTable.getFont().deriveFont(Font.PLAIN, 24)); // Set font size
        dataTable.setRowHeight(40); // Set row height

        // Create scroll pane and add table to it
        scrollPane = new JScrollPane(dataTable);

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
        setTitle("Data Form");
        setSize(windowWidth, windowHeight); // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Add components to panel
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(goBackButton, BorderLayout.SOUTH);

        // Set content pane and make frame visible
        setContentPane(panel);
        setVisible(true);
    }

    // Method to read data from file and populate the table model
    private void readDataFromFile(String filename, DefaultTableModel model) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Assuming comma-separated values
                model.addRow(parts);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
