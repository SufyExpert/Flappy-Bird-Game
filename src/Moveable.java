import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;

abstract public class Moveable extends JPanel implements Coordinates, Runnable {

    static int score = 0;

    static volatile boolean shouldTerminate = false;

    public abstract void run();

    abstract void start();

    abstract void move();
}

class BirdMovement extends Moveable {

    static GameObjects bird;
    private boolean move = false;
    private int moveCounter = 0; // Counter to control upward movement

    public BirdMovement() {
        setLayout(null);
        bird = new FlappyBird(birdWidth, birdHeight);
        bird.setBounds(bird_x, bird_y, birdWidth, birdHeight); // Set bounds for the bird
        setBounds(0, 0, windowWidth, windowHeight); // Set bounds for the bird
        setOpaque(false);
        add(bird);
    }

    public void move() {
        move = true;
    }

    @Override
    public void run() {
        while (!shouldTerminate) {
            try {
                // Sleep for 3 milliseconds
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Change the position of the bird
            if (move && moveCounter < 100 && bird.getY() > 0) {
                bird.setLocation(bird_x, bird.getY() - 1);
                setOpaque(false);
                moveCounter++;
            } else if (!move && (bird.getY() + birdHeight < windowHeight)) {
                bird.setLocation(bird_x, bird.getY() + 1);
                setOpaque(false);
                moveCounter = 0; // Reset the counter when not moving up
                move = false;
            } else
                move = false;

        }
    }

    void start() {
        //birdMovement.start();
        Thread thread = new Thread(this);
        thread.start();
    }

}

class PipesMovement extends Moveable {

    private GameObjects topPipe;
    private GameObjects bottomPipe;
    private static final Object lock = new Object();
    private static boolean isCovered = false;
    Random random = new Random();
    private int randomY = random.nextInt(2 * (windowHeight / 6   )) - (windowHeight / 6);
    private int pipeStarting = windowWidth - pipeDistance;
    static JLabel scoreLabel;

    public PipesMovement() {
        setLayout(null);
        topPipe = new TopPipe(pipeWidth, pipeHeight);
        topPipe.setBounds(pipe_x, topPipe_y, pipeWidth, pipeHeight);
        add(topPipe);
        bottomPipe = new BottomPipe(pipeWidth, pipeHeight);
        bottomPipe.setBounds(pipe_x, bottomPipe_y, pipeWidth, pipeHeight);
        add(bottomPipe);
        setBounds(0, 0, windowWidth, windowHeight); // Set bounds to cover the entire panel
        setOpaque(false);

        if (scoreLabel == null) {
            scoreLabel = new JLabel("Your Score: " + score);
            scoreLabel.setFont(scoreLabel.getFont().deriveFont(Font.PLAIN, 30)); // Set font size to 30
            scoreLabel.setForeground(Color.WHITE); // Set color to white
            scoreLabel.setBounds(10, 10, 210, 30);
            add(scoreLabel);
        }
    }

    public void move() {
        Thread moveThread = new Thread(() -> {
            for (int location = pipe_x; location > 0 - pipeWidth && !shouldTerminate; location--) {

                if (location == pipeStarting) {
                    synchronized (lock) {
                        isCovered = true;
                        lock.notify(); // Notify waiting thread to resume
                    }
                }

                topPipe.setLocation(location, topPipe_y + randomY);
                bottomPipe.setLocation(location, bottomPipe_y + randomY);

                // logic for Calculating Score
                if (bird_x == (location + pipeWidth)) {
                    score++;
                }

                scoreLabel.setText("Your Score: " + score); // Update with the new score

                if (bird_x + birdWidth - 15 >= location && bird_x <= location + pipeWidth) {
                    boolean hitsTopPipe = BirdMovement.bird.getY() <= topPipe_y + randomY + pipeHeight;
                    boolean hitsBottomPipe = BirdMovement.bird.getY() + birdHeight - 10 >= bottomPipe_y + randomY;
                    if (hitsTopPipe || hitsBottomPipe) {
                            shouldTerminate = true;
                            GamePanel.mainFrame.setVisible(false);
                        new EndingScreen();
                    }
                }
                else if(BirdMovement.bird.getY() == 0 || BirdMovement.bird.getY() + birdHeight + 35 == windowHeight){
                    shouldTerminate = true;
                    GamePanel.mainFrame.setVisible(false);
                    new EndingScreen();
                }

                try {
                    Thread.sleep(5); // Speed of the Pipes.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Reset the pipes to start from the right again
            randomY = random.nextInt(2 * (windowHeight * 7 / 24)) - (windowHeight * 7 / 24);
            topPipe.setBounds(pipe_x, topPipe_y + randomY, pipeWidth, pipeHeight);
            bottomPipe.setBounds(pipe_x, bottomPipe_y + randomY, pipeWidth, pipeHeight);
        });
        moveThread.start();

    }

    @Override
    public void run() {
        ArrayList<Moveable> pipeMoves = new ArrayList<>();

        for (int i = 0; !shouldTerminate ; i++) {
            pipeMoves.add(new PipesMovement());
            add(pipeMoves.get(i));
            pipeMoves.get(i).setOpaque(false);
            pipeMoves.get(i).move();
            setOpaque(false);

            synchronized (lock) {
                while (!isCovered) {
                    try {
                        lock.wait(); // Wait until notified
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isCovered = false; // Reset the flag for the next background
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}

class BackgroundMovement extends Moveable {
    private GameObjects background;
    private static final int noOfBackground = 4; // Fixed number of background objects
    private static int backgroundStarting = 0;
    private static final Object lock = new Object();
    private static boolean isCovered = false;

    public BackgroundMovement() {
        setLayout(null);
        background = new Background(windowWidth, windowHeight);
        background.setBounds(windowWidth, 0, windowWidth, windowHeight); // Start from the right of the screen
        setBounds(0, 0, windowWidth, windowHeight);
        setOpaque(false);
        add(background);
    }

    @Override
    public void move() {
        Thread moveThread = new Thread(() -> {
            for (int location = backgroundStarting; location >= -windowWidth && !shouldTerminate; location--) {

                background.setLocation(location, 0);

                if (location == 20) {
                    synchronized (lock) {
                        if (backgroundStarting != 0){
                            isCovered = true;
                            lock.notify(); // Notify waiting thread to resume
                        }
                    }
                }

                try {
                    Thread.sleep(10); // Speed of the background movement
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Reset the background to start from the right again
            background.setBounds(windowWidth, 0, windowWidth, windowHeight);
        });
        moveThread.start();
    }

    @Override
    public void run() {
        BackgroundMovement[] backgroundMovement = new BackgroundMovement[noOfBackground];
        for (int i = 0, j = 0; !shouldTerminate ; ++j, i = j % noOfBackground) {

            if (j == 0){
                BackgroundMovement backgroundMove = new BackgroundMovement();
                add(backgroundMove);
                backgroundStarting = 10;
                backgroundMove.move();
            }

            backgroundMovement[i] = new BackgroundMovement();
            add(backgroundMovement[i]);
            backgroundStarting = background_x;
            backgroundMovement[i].move();

            synchronized (lock) {
                while (!isCovered) {
                    try {
                        lock.wait(); // Wait until notified
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isCovered = false; // Reset the flag for the next background
            }
        }
    }

    @Override
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
