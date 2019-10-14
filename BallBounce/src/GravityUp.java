import java.awt.*;

public class GravityUp extends Item {

    public GravityUp(int x) {
        super(x);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.CYAN);
        super.paint(g);
    }

    @Override
    public void performAction(Ball ball) {
        ball.setGravity(ball.getGravity() + 3);
    }
}
