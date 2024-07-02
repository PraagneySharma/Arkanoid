import java.awt.*;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Laser {
    private int y, x, vy, width, height;
    private final static int bulletH = 10;
    private final static int bulletW = 5;

    
    public Laser (int x, int y, int width, int height, int vy){       
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vy = vy;
        // image = Util.loadImage("bullet.png");
    }
    
    
    public static void shoot (ArrayList<Block> blocks, ArrayList<String> powers,  Player p1, ArrayList<Laser> BulletsList){
        // SHOOTING 2 BULLETS WHEN U HAVE LASER POWERUP 
        if (p1.getBulletDelay() == 0 && powers.contains("Laser")) {
            
            int vy = -5; 
            // ADD 2 BULLETS TO LIST
            BulletsList.add(new Laser(p1.getX() + (p1.getWidth() - 10), p1.getY() - bulletH, bulletW, bulletH, vy));
            BulletsList.add(new Laser(p1.getX() + 10, p1.getY() - bulletH, bulletW, bulletH, vy));
            p1.setBulletDelay(500);
        
        }
        // THE TIME BEFORE THE LASER SHOOTS AGAIN
        if (p1.getBulletDelay() > 0) {
            p1.setBulletDelay(p1.getBulletDelay() - 10);
        }    
    }

    public void move(ArrayList<Block> blocks, ArrayList<Laser> BulletsList){
        // IF THE BULLET HITS A BLOCK REMOVE THE BLOCK AND THE BULLET
        for (int i = 0; i < BulletsList.size(); i++) {
            for (int j = 0; j < blocks.size(); j++) {
                if (BulletsList.get(i).getRect().intersects(blocks.get(j).getRect())) {
                    blocks.remove(j);
                    BulletsList.remove(i);
                    break;
                }
            }
        }
        y += vy;

    }

    
    
    public void draw(Graphics g){
        // g.drawImage(this.image, x, y, null);
        g.setColor(Color.ORANGE);
        g.fillRect(x,y,width,height);

    }
    
    public Rectangle getRect(){
        return new Rectangle(x, y, width, height);
    }

    
}