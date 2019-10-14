import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Platform {

    int x, y, width, height;
    int dx;
    Image platform;
    URL url;
    float frame = 0;

    public  Platform(int x, int y){
        this.x = x;
        this.y = y;
        width = 120;
        height = 40;
        dx = -1;
        platform = Pngs.plat;
    }

    public void update(Main startingPoint, Ball ball){

        int tester = (int)(frame + .1);

        if(tester < 3){
            frame += .1;
        }else{
            frame = 0;
        }

        x += -(Pngs.level);
        collisionCheck(ball);

        if(x < 0 - width){
            Random r = new Random();
            y = startingPoint.getHeight() - 40 - r.nextInt(400);
            x = startingPoint.getWidth() + r.nextInt(300);
        }
    }

    private void collisionCheck(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int radius = ball.getRadius();

        if (ballY + radius > y && ballY + radius < y + height){
            if (ballX > x && ballX < x + width) {

                double newDy = ball.getGameDy();
                ball.setY(y - radius);
                ball.setDy(newDy);
            }
        }
    }

    public void paint(Graphics g){

        g.setColor(Color.BLUE);
        g.fillRect(x , y, width, height);

        g.drawImage(platform, x, y, Pngs.startingPoint);
        g.drawImage(platform, x, y, x + width, y + height,
                0, 40 * (int) frame ,120, 40*(int)frame + 40, Pngs.startingPoint);
    }
}
