import java.awt.*;

public class AgilityUp extends Item {

    public AgilityUp(int x) {
        super(x);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        super.paint(g);
    }

    @Override
    public void performAction(Ball ball) {
        if(ball.getAgility() < 8) {
            ball.setAgility(ball.getAgility() + 1);
        }
    }
}