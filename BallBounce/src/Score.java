import java.awt.*;
import java.util.Random;

public class Score extends Item {

    private Main appletInfo;

    public Score(int x, Main appletInfo){
        super(x);
        this.appletInfo = appletInfo;
    }

    @Override
    public void performAction(Ball ball) {
        Random r = new Random();
        appletInfo.setScore(appletInfo.getScore() + 500 + r.nextInt(2000));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        super.paint(g);
    }
}
