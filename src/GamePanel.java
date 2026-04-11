import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.lang.Thread;

public class GamePanel extends JPanel implements Coordinates {
    static JFrame mainFrame;

    public GamePanel(JFrame frame) {
        mainFrame = frame;

        setLayout(null);

        PipesMovement pipesMovement = new PipesMovement();
        add(pipesMovement);
        pipesMovement.start();

        BirdMovement birdMovement = new BirdMovement();
        add(birdMovement);
        birdMovement.start();

        BackgroundMovement backgroundMovement = new BackgroundMovement();
        add(backgroundMovement);
        backgroundMovement.start();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    birdMovement.move();
                }
            }
        });

//        // Create and start a timer to repaint the panel at regular intervals
//        Timer timer = new Timer(8, e -> repaint()); // roughly 60 FPS
//        timer.start();
    }
}
