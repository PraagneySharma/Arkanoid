/**
 * @(#)Player.java
 *
 *
 * @author 
 * @version 1.00 2024/4/5
 */
import java.awt.*;
import java.util.ArrayList;

public class Player {
    private int x, y, leftKey, rightKey, width, height, framecounter, warpcounter, warpwidth, warpheight;
    private String powerUp;
    private Image image, warpimage;
    private int bulletDelay;

    public Player(int xx, int left, int right) {     
        x = xx;
        y = 500;
        leftKey = left;
        rightKey = right;
        this.image = Util.loadImage("VausSpacecraft.png");
        this.warpimage = Util.loadImage(null);
        String powerUp = "no";
        width = 80;
        height =20;
        warpwidth = 25;
        warpheight = 50;
        framecounter = 0;
        bulletDelay = 0;
        
        
    }
    
    
    public boolean move(boolean []keys, ArrayList<Capsule> capsules, ArrayList<String> powers, ArrayList<Laser> bulletsList){
        powerUpOn(capsules, powers, bulletsList);
        if(keys[leftKey] && y >= 10){
            x -= 10;
        }
        if(keys[rightKey] && y <= 510){
            x += 10;
        }
        if(x<=25){
            x=25;
        }
        if(x+width>=775){
            x=775-width;
        }
        // SEEING WHAT POWERUPS THE PLAYER HAS
        for (int i=0; i<=powers.size()-1; i++){
            if (powers.get(i)=="Enlarge"){
                image = Util.loadImage("VausSpacecraftLarge.png");
                if (framecounter<78){
                    width = 160;
                    framecounter++;
                }
                else{
                    ogSpaceCraft(powers);
                }
            }
        }
            
        if (powers.contains("Warp")){
            warpimage = Util.loadImage("Warp.png");
            Rectangle warpRect = getWarpRect();
            if (warpcounter<78){                 
                warpcounter++;
                if (warpRect.intersects(getRect())){
                    return true;
                }
            }
            else{
                warpcounter = 0;
                warpimage = Util.loadImage(null);
                powers.remove("Warp");
            }
        }
        
        return false;
 
    }

    public void powerUpOn(ArrayList<Capsule> capsule, ArrayList<String> powers, ArrayList<Laser> bulletsList){
        // checking which powerup the capsule has when the player intersects with it
        Rectangle playerRect = getRect();
        for (int i=0; i<=capsule.size()-1; i++){
            Rectangle capRect = capsule.get(i).getRect();
            if(playerRect.intersects(capRect)){
                powerUp = capsule.get(i).getPowerUp();
                powers.add(powerUp);
                capsule.remove(i);
            }
        }
    }
    // back to original spacecraft
    public void ogSpaceCraft(ArrayList<String> powers){
        for (int i=0; i<=powers.size()-1; i++){
            powers.remove(i);
        }
        framecounter = 0;
        image = Util.loadImage("VausSpacecraft.png"); 
        width = 80;         
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public int getBulletDelay(){
        return bulletDelay;
    }
    public void setBulletDelay(int delay){
        bulletDelay = delay;
    }
 
    public void draw(Graphics g){
        g.drawImage(image,x,y,null);
        g.drawImage(warpimage,GamePanel.WIDTH+770,475,null);
    }
    
    public Rectangle getRect(){
        return new Rectangle(x, y, width, height);
    }
    public Rectangle getWarpRect(){
        return new Rectangle(GamePanel.WIDTH+770, 475, warpwidth, warpheight);
    }
    
}