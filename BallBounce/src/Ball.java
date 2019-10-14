import java.awt.*;

public class Ball {

    private int x = 400;
    private int y = 25;
    private int radius = 20;
    private double dx = 0;
    private double dy = 0;
    private double xFriction = .9;
    private double gravity = 15;
    private double loss = 1;
    private double dt = .2;
    private  double gameDy = -75;
    private int agility = 3;
    private int maxSpeed = 20;
    private boolean gameOver = false;

    public Ball() {

    }

    public int getX() {
        return x;
    }

    public double getGameDy() {
        return gameDy;
    }

    public void setGameDy(double gameDy) {
        this.gameDy = gameDy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getRadius() {
        return radius;
    }

    public double getGravity() {
        return gravity;
    }

    public Ball(int i, int j) {
        x = i;
        y = j;
    }

    public void moveRight(){
        if (dx + agility < maxSpeed){
            dx += agility;
        }
    }

    public void moveLeft() {
        if (dx - agility > -maxSpeed) {
            dx -= agility;
        }
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void update(Main startingPoint){
        if (x + dx > startingPoint.getWidth() - radius - 1){
            x = startingPoint.getWidth() - radius - 1;
            dx = -dx;
        }
        else if(x + dx < 0 + radius){
            x = 0 + radius;
            dx = -dx;
        }
        else{
            x += dx;
        }

        if (y == startingPoint.getHeight() - radius - 1){
            dx *= xFriction;
            if(Math.abs(dx) < .8){
                dx = 0;
            }
        }

        if(y - 200 > startingPoint.getHeight() - radius - 1){
            gameOver = true;
        }
        else{
            //velocity formula
            dy += gravity * dt;
            //position formula
            y += dy * dt + .5 * gravity * dt *dt;
        }
    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);

    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public boolean getGameOver() {
        return gameOver;
    }
}
