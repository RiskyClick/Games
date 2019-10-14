import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Random;

public class Main extends Applet implements Runnable, KeyListener, MouseMotionListener, MouseListener {

    private Image theImage;
    private Graphics theGraphic;

    private int score = 0;
    Ball redBall;

    Platform platform[] = new Platform[7];
    Item Item[] = new Item[3];

    double backgroundX = 0;
    double backgroundDx = .3;

    URL url;
    Image background;

    int levelCheck = 0;
    boolean gameOver = false;

    boolean mouse = false;

    public void init() {

        setSize(800, 600);

        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseListener(this);

        try {
            url = getDocumentBase();
        } catch (Exception e) {
            System.out.println(e);
        }

        background = getImage(url, "Images/test.png");
        new Pngs(this);

        Pngs.exlpode.loop();

    }

    public void start() {
        //refers to the run method
        redBall = new Ball();
        for (int i = 0; i < platform.length; i++) {
            Random r = new Random();
            platform[i] = new Platform(i * 120, 300);
        }

        for (int i = 0; i < Item.length; i++) {
            Random r = new Random();
            switch (r.nextInt(5)) {
                case 0:
                    Item[i] = new GravityUp(getWidth() + 2000 * i);
                    break;
                case 1:
                    Item[i] = new GravityDown(getWidth() + 2000 * i);
                    break;
                case 2:
                    Item[i] = new AgilityDown(getWidth() + 2000 * i);
                    break;
                case 3:
                    Item[i] = new AgilityUp(getWidth() + 2000 * i);
                    break;
                case 4:
                    Item[i] = new Score(getWidth() + 2000 * i, this);
                    break;
            }
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {

            gameOver = redBall.getGameOver();

            if (levelCheck > 1000) {
                Pngs.level++;
                levelCheck = 0;
            }

            levelCheck++;

            if (backgroundX > getWidth() * -1) {
                backgroundX -= backgroundDx;
            } else {
                backgroundX = 0;
            }

            Random r = new Random();

            if (!gameOver) {
                score++;
            }

            for (int i = 0; i < Item.length; i++) {
                if (Item[i].isGenerate()) {
                    Item[i] = null;
                    switch (r.nextInt(5)) {
                        case 0:
                            Item[i] = new GravityUp(getWidth() + 10 * r.nextInt(500));
                            break;
                        case 1:
                            Item[i] = new GravityDown(getWidth() + 10 * r.nextInt(500));
                            break;
                        case 2:
                            Item[i] = new AgilityDown(getWidth() + 10 * r.nextInt(500));
                            break;
                        case 3:
                            Item[i] = new AgilityUp(getWidth() + 10 * r.nextInt(500));
                            break;
                        case 4:
                            Item[i] = new Score(getWidth() + 10 * r.nextInt(500), this);
                            break;
                    }
                    Item[i].setGenerate(false);
                }
            }

            redBall.update(this);

            for (int i = 0; i < platform.length; i++) {
                platform[i].update(this, redBall);
            }

            for (int i = 0; i < Item.length; i++) {
                Item[i].update(this, redBall);
            }

            repaint();

            try {
                //Frame Rate
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void destroy() {

    }

    public void stop() {

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void update(Graphics graphics) {
        if (theImage == null) {
            //Get the size THIS applet and set the size to that
            theImage = createImage(this.getSize().width, this.getSize().height);
            theGraphic = theImage.getGraphics();
        }
        theGraphic.setColor(getBackground());
        theGraphic.fillRect(0, 0, this.getSize().width, this.getSize().height);

        theGraphic.setColor(getForeground());
        paint(theGraphic);

        graphics.drawImage(theImage, 0, 0, this);
    }

    public void paint(Graphics g) {

        // g.setColor(Color.LIGHT_GRAY);
        // g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(background, (int) backgroundX, 0, this);
        g.drawImage(background, (int) backgroundX + getWidth(), 0, this);

        redBall.paint(g);
        String s = Integer.toString(score);

        Font font = new Font("Serif", Font.BOLD, 32);
        g.setFont(font);

        g.setColor(Color.BLACK);
        g.drawString(s, getWidth() - 152, 52);

        g.setColor(Color.GREEN);
        g.drawString(s, getWidth() - 150, 50);

        for (int i = 0; i < platform.length; i++) {
            platform[i].paint(g);
        }

        for (int i = 0; i < Item.length; i++) {
            Item[i].paint(g);
        }
        if (gameOver) {
            g.setColor(Color.MAGENTA);
            g.drawString("GAME OVER", 300, 300);
            //mouse check
            //g.drawRect(280, 320, 180, 40);
            if (mouse) {
                g.setColor(Color.RED);
                g.drawString("PLAY AGAIN", 300, 340);
            } else {
                g.setColor(Color.BLACK);
                g.drawString("PLAY AGAIN", 300, 340);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {

            case KeyEvent.VK_LEFT:
                redBall.moveLeft();
                break;

            case KeyEvent.VK_RIGHT:
                redBall.moveRight();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if(gameOver) {
            if (mouseEvent.getX() > 280 && mouseEvent.getX() < 460) {
                if (mouseEvent.getY() > 320 && mouseEvent.getY() < 360) {
                    mouse = true;
                }
            }
            if (mouseEvent.getX() < 280 || mouseEvent.getX() > 460) {
                mouse = false;

            }
            if (mouseEvent.getY() < 320 || mouseEvent.getY() > 360) {
                mouse = false;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //start new game
        if(mouse){
            redBall = null;
            redBall = new Ball();
            score = 0;
            Pngs.level = 1;
            for (int i = 0; i < platform.length; i++) {
                Random r = new Random();
                platform[i] = new Platform(i * 120, 300);
            }

            for (int i = 0; i < Item.length; i++) {
                Random r = new Random();
                switch (r.nextInt(5)) {
                    case 0:
                        Item[i] = new GravityUp(getWidth() + 2000 * i);
                        break;
                    case 1:
                        Item[i] = new GravityDown(getWidth() + 2000 * i);
                        break;
                    case 2:
                        Item[i] = new AgilityDown(getWidth() + 2000 * i);
                        break;
                    case 3:
                        Item[i] = new AgilityUp(getWidth() + 2000 * i);
                        break;
                    case 4:
                        Item[i] = new Score(getWidth() + 2000 * i, this);
                        break;
                }
            }
            mouse = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}

