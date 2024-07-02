import java.awt.*;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Capsule {
    private int capy, capx, capvy;
    private final String powerUp;
    private Image image;
    private static final String[] capsules = {"Catch","Duplicate", "Enlarge", "Laser","Player","Slow", "Warp"};
    
    public Capsule (int x, int y, int vy){
        this.capx=x;
        this.capy=y;
        this.capvy = vy;
        this.powerUp = capsules[Util.randint(0, capsules.length - 1)];
        this.image = Util.loadImage("Capsules/" + powerUp + ".png");
    }
    
    
    public void move (ArrayList<Ball> ball, Player player1, ArrayList<Capsule> capsule, ArrayList<Laser> bulletsList, ArrayList<Block> blocks){
        // IF BULLET COLLIDES WITH CAPSULE
        capy+=capvy;
        Rectangle capsuleRec = getRect();
        for (int i=0; i<=bulletsList.size()-1; i++){         
            Rectangle BulletRec = bulletsList.get(i).getRect();
            if(BulletRec.intersects(capsuleRec)){                   
                capvy=5;
            }
        }
        // IF BALL COLLIDES WITH CAPSULE
        for (int i=0; i<=ball.size()-1; i++){
            Rectangle ballRec = ball.get(i).getRect();
            if (ballRec.intersects(capsuleRec)){
                capvy=5;
            }
        }
    }
    
    public String getPowerUp(){
        return powerUp;
    }
    
    public void draw(Graphics g){
        g.drawImage(this.image, capx, capy, null);
    }
    
    public Rectangle getRect(){
        return new Rectangle(capx, capy, 87, 40);
    }
    
}