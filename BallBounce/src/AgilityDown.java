import java.awt.*;

public class AgilityDown extends Item {

    public AgilityDown(int x) {
        super(x);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        super.paint(g);
    }

    @Override
    public void performAction(Ball ball) {
        if(ball.getAgility() >= 2) {
            ball.setAgility(ball.getAgility() - 1);
        }
    }
}