import java.awt.*;
import java.util.Random;

public class Item {

    private int x, y, dx, radius;
    private Main startingPoint;
    private boolean generate = false;

    public Item(int x){
        this.x = x;
        Random r = new Random();
        y = r.nextInt(400) + radius;
        radius = 10;
        dx = -2;

    }

    public void update(Main startingPoint, Ball ball){
        x += dx;
        this.startingPoint = startingPoint;
        collisionCheck(ball);
        if(x < 0 - radius){
            generate = true;
        }
    }

    private void collisionCheck(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballR = ball.getRadius();

        int a = x - ballX;
        int b = y - ballY;
        int crash = radius + ballR;
        double c = Math.sqrt((double) (a * a) + (double) (b * b));
        if(c < crash){
            performAction(ball);
            generate = true;
        }
    }

    public void performAction(Ball ball) {
    }

    public void paint(Graphics g){
        //g.setColor(Color.RED);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getDx() {
        return dx;
    }

    public int getRadius() {
        return radius;
    }

    public int getX() {
        return x;
    }

    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }
}
