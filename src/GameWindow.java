import javax.swing.*;

class GameWindow extends JFrame implements Coordinates {
    public GameWindow() {

        setSize(windowWidth, windowHeight); // Set the size of the window
        setTitle("Flappy Bird"); // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false); // Prevent resizing
        setAlwaysOnTop(true); // Always keeps the screen on top

        Moveable.shouldTerminate = false;
        Moveable.score = 0;

        GamePanel gamePanel = new GamePanel(this);
        gamePanel.setBounds(0,0, windowWidth, windowHeight);
        setContentPane(gamePanel);

        setVisible(true); // Display the window

    }

    public void closeGameWindow(){
        dispose();
    }
}