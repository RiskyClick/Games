import java.awt.*;

public class GravityDown extends Item {

    public GravityDown(int x) {
        super(x);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.ORANGE);
        super.paint(g);
    }

    @Override
    public void performAction(Ball ball) {
        if(ball.getGravity() > 3) {
            ball.setGravity(ball.getGravity() - 3);
            if(ball.getGravity() < 3){
                ball.setGravity(3);
            }
        }
    }
}
