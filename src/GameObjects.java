import java.awt.*;
import javax.swing.*;

public class GameObjects extends JPanel implements Coordinates {

    ImageIcon imageIcon;
    Image scaledImage;

    int width, height;
    int imageLength, imageWidth;

    public GameObjects(int imageWidth, int imageLength, ImageIcon imageIcon) {

        this.scaledImage = imageIcon.getImage().getScaledInstance(imageWidth, imageLength, Image.SCALE_DEFAULT);
        this.imageIcon = new ImageIcon(scaledImage);
        this.imageWidth = imageWidth;
        this.imageLength = imageLength;
        this.width = imageIcon.getIconWidth();
        this.height = imageIcon.getIconHeight();
        draw();

    }

    void draw() {
        JLabel label = new JLabel(imageIcon);
        label.setOpaque(false);
        setLayout(null);
        label.setLocation(0, 0);
        label.setSize(imageWidth, imageLength);

        add(label);
    }
}

class Background extends GameObjects {

    static ImageIcon backgroundImage = new ImageIcon("assets/Background.png");

    public Background(int imageWidth, int imageLength) {
        super(imageWidth, imageLength, backgroundImage);
        setBounds(0, 0, windowWidth, windowHeight);
    }

}

class FlappyBird extends GameObjects {

    static ImageIcon birdImage = new ImageIcon("assets/flappybird.png");
    //static ImageIcon birdImage = new ImageIcon("src/cropped_Waqas Ul Hasan.png");

    public FlappyBird(int imageWidth, int imageLength) {
        super(imageWidth, imageLength, birdImage);
        setOpaque(false);
        setBounds(bird_x, bird_y, birdWidth, birdHeight);
    }
}

class BottomPipe extends GameObjects {

    static ImageIcon bottomPipeImage = new ImageIcon("assets/bottompipe.png");

    public BottomPipe(int imageWidth, int imageLength) {
        super(imageWidth, imageLength, bottomPipeImage);
        setOpaque(false);
        setBounds(pipe_x, bottomPipe_y, pipeWidth, pipeHeight);
    }
}

class TopPipe extends GameObjects {
    static ImageIcon topPipeImage = new ImageIcon("assets/toppipe.png");

    public TopPipe(int imageWidth, int imageLength) {
        super(imageWidth, imageLength, topPipeImage);
        setOpaque(false);
        setBounds(pipe_x, topPipe_y, pipeWidth, pipeHeight);
    }
}