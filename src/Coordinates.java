public interface Coordinates {

    static int windowWidth = 1234;
    static int windowHeight = 684;

    static int birdWidth = 81;
    static int birdHeight = 57;

    static int bird_x = (int) (windowWidth / 8);
    static int bird_y = (int) ((windowHeight / 2) - (birdHeight / 2));

    int pipeWidth = 100;
    int pipeHeight = windowHeight;

    static int pipe_x = windowWidth + pipeWidth;
    static int topPipe_y = -(windowHeight * 2 / 3);
    static int bottomPipe_y = windowHeight * 2 / 3;
    static int pipeDistance = 200;

    static int background_x = windowWidth;

}