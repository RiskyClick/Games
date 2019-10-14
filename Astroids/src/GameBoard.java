import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameBoard extends JFrame {

    public static int boardWidth = 1000;
    public static int boardHeight = 800;

    public static boolean keyHeld = false;

    public static int keyHeldCode;

    public static ArrayList<PhotonTorpedo> torpedos = new ArrayList<>();

    String thrustFile = "file:./src/thrust.au";
    String laserFile = "file:./src/laser.aiff";



    public static void main(String[] args) {
        new GameBoard();
    }

    public GameBoard() {

        this.setSize(boardWidth, boardHeight);
        this.setTitle("Java Ass");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent keyEvent) {

                    }

                    @Override
                    public void keyPressed(KeyEvent keyEvent) {

                        if (keyEvent.getKeyCode() == 87) {

                            System.out.println("Forward");
                            keyHeldCode = keyEvent.getKeyCode();
                            keyHeld = true;
                            playSoundEffect(thrustFile);

                        } else if (keyEvent.getKeyCode() == 83) {

                            System.out.println("Backwards");
                            keyHeldCode = keyEvent.getKeyCode();
                            keyHeld = true;

                        } else if (keyEvent.getKeyCode() == 68) {

                            System.out.println("Rotate Right");
                            keyHeldCode = keyEvent.getKeyCode();
                            keyHeld = true;

                        } else if (keyEvent.getKeyCode() == 65) {

                            System.out.println("Rotate Left");
                            keyHeldCode = keyEvent.getKeyCode();
                            keyHeld = true;

                        } else if(keyEvent.getKeyCode() == keyEvent.VK_SPACE) {

                            torpedos.add(new PhotonTorpedo(GameDrawingPanel.theShip.getShipNoseX(),
                            GameDrawingPanel.theShip.getShipNoseY(),
                            GameDrawingPanel.theShip.getRotationAngle()));
                            playSoundEffect(laserFile);


                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent keyEvent) {
                        keyHeld = false;
                    }
                });



        GameDrawingPanel gamePanel = new GameDrawingPanel();

        this.add(gamePanel, BorderLayout.CENTER);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

        //frame rate
        executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0L, 20L, TimeUnit.MILLISECONDS);

        this.setVisible(true);

    }

    public static void playSoundEffect(String soundToPlay){
        // Pointer towards the resource to play
        URL soundLocation;
        try {
            soundLocation = new URL(soundToPlay);
            // Stores a predefined audio clip
            Clip clip = null;
            // Convert audio data to different playable formats
            clip = AudioSystem.getClip();
            // Holds a stream of a definite length
            AudioInputStream inputStream;
            inputStream = AudioSystem.getAudioInputStream(soundLocation);
            // Make audio clip available for play
            clip.open( inputStream );
            // Define how many times to loop
            clip.loop(0);

            // Play the clip
            clip.start();
        }
                catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
                catch (UnsupportedAudioFileException | IOException e1) {
            e1.printStackTrace();
        }

                catch (LineUnavailableException e1) {
            e1.printStackTrace();
        }

    }

}

    class RepaintTheBoard implements Runnable{

        GameBoard theBoard;

        public  RepaintTheBoard(GameBoard theBoard){
            this.theBoard = theBoard;
        }

        @Override
        public void run(){
            theBoard.repaint();
        }

    }

    class GameDrawingPanel extends JComponent{

        public ArrayList<Rock> rocks = new ArrayList<>();

        int[] polyXArray = Rock.sPolyXArray;
        int[] polyYArray = Rock.sPolyYArray;

        static SpaceShip theShip = new SpaceShip();

        int width = GameBoard.boardWidth;
        int height = GameBoard.boardHeight;

        public GameDrawingPanel(){

            //create 50 rocks
            for(int i = 0; i < 5; i++){
                int randomStartXPos = (int) (Math.random() * (GameBoard.boardWidth - 40) + 1);
                int randomStartYPos = (int) (Math.random() * (GameBoard.boardHeight - 40) + 1);

                rocks.add(new Rock(Rock.getpolyXArray(randomStartXPos), Rock.getpolyYArray(randomStartYPos), 13, randomStartXPos, randomStartYPos));
                Rock.rocks = rocks; // NEW
            }
        }

        public void paint(Graphics g){
            Graphics2D graphicSettings = (Graphics2D)g;

            AffineTransform identity = new AffineTransform();

            graphicSettings.setColor(Color.BLACK);

            graphicSettings.fillRect(0, 0, getWidth(), getHeight());

            graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphicSettings.setPaint(Color.WHITE);

            for(Rock rock : rocks){
                if(rock.onScrean){
                    rock.move(theShip, GameBoard.torpedos);
                }
                graphicSettings.draw(rock);
            }

            if(GameBoard.keyHeld == true && GameBoard.keyHeldCode == 68){

                theShip.increaseRotationAngle();

            } else{

                if(GameBoard.keyHeld == true && GameBoard.keyHeldCode == 65){

                    theShip.decreaseRotationAngle();

                } else {
                    if(GameBoard.keyHeld == true && GameBoard.keyHeldCode == 87){

                        theShip.setMovingAngle(theShip.getRotationAngle());
                        theShip.increaseXVelocity(theShip.shipXMoveAngle(theShip.getMovingAngle()) * 0.1);
                        theShip.increaseYVelocity(theShip.shipYMoveAngle(theShip.getMovingAngle()) * 0.1);

                    }
                }
            }

            theShip.move();

            graphicSettings.setTransform(identity);

            graphicSettings.translate(theShip.getXCenter(), theShip.getYCenter());

            graphicSettings.rotate(Math.toRadians(theShip.getRotationAngle()));

            graphicSettings.draw(theShip);

            for(PhotonTorpedo torpedo : GameBoard.torpedos){
                torpedo.move();
                if(torpedo.onScreen){
                    graphicSettings.setTransform(identity);

                    graphicSettings.translate(torpedo.getXCenter(), torpedo.getYCenter());

                    graphicSettings.draw(torpedo);
                }
            }

        }

    }










































