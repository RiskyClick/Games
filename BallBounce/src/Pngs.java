import java.applet.AudioClip;
import java.awt.*;
import java.net.URL;

public class Pngs {

    static Image plat, ball;
    URL url;
    static Main startingPoint;
    static AudioClip exlpode;
    static  int level = 1;

    public Pngs(Main startingPoint){

        try{
            url = startingPoint.getDocumentBase();
        } catch (Exception e){
            System.out.println(e);
        }

        exlpode = startingPoint.getAudioClip(url, "Music/Explosion_small.wav");

        plat = startingPoint.getImage(url, "Images/test.png");
        this.startingPoint = startingPoint;
    }
}
