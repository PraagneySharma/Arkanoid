/**
 * @(#)Ball.java
 *
 *
 * @author 
 * @version 1.00 2024/4/5
 */
import java.awt.*;
import java.util.ArrayList;

public class Ball {
    private int ballx, bally, vx, vy, lasercounter, slowcounter; 
    private boolean moving;
    public static final int XBOUNCE = 1, YBOUNCE = 2, CBOUNCE = 3;
    
    public Ball(int x, int y, ArrayList<String> powers) {
        ballx = x;
        bally = y;
        vx=6;
        vy=-6;
        lasercounter = 0;
        slowcounter = 0;
        moving=false;
        if (powers.contains("Duplicate")){
            vx = -5;
            moving = true;
        }
    }
    
    
    // CHANGING THE BALLS POSITION
    public void move(Player p1, ArrayList<Block> block, ArrayList<String> powers, ArrayList<Ball> ballList){
        // IF THE BALL IS NOT MOVING 
        if (!moving){
            ballx = p1.getRect().x + p1.getRect().width / 2 - getRect().width / 2;
            return;

        }
        Rectangle p1r = p1.getRect();
        
        // BOUNDARIES
        ballx += vx;
        bally += vy;
        Rectangle rec = getRect();
        
        if(bally < 25){
            vy *= -1;
        }
        if(ballx < 35){
            vx *= -1;
        }
        if(ballx >= 755){
            vx *= -1;
        }
        // THE ANGLE OF WHICH THE BALL BOUNCES OFF THE PADDLE
        if(rec.intersects(p1r)){
            if (bally>p1r.y+2){
                if (ballx<p1r.x+p1r.width/3){
                    vx = -6;
                }
                else if (ballx>p1r.x+p1r.width/3*2){
                    vx = 6;
                }
                else{
                    vx = 1;
                    vy = 6;
                }
                
                vy *= -1;
            }

            
        }       
    
        // CHECKING HOW THE BALL BOUNCES OFF THE BLOCKS
        for (int i=0; i<block.size(); i++){      
            if(rec.intersects(block.get(i).getRect())){
                int bounce = block.get(i).BlockHit(ballx,bally,vx,vy,block);
                if (bounce == CBOUNCE){
                    vx *= -1;
                    vy *= -1;
                }

                if (bounce == XBOUNCE){  
                    vx *= -1;                                    
                }

                if (bounce == YBOUNCE){
                    vy *= -1;
                }

                block.remove(i);       
            }
        }
        // SEEING WHAT POWERUP IS ACTIVATED
        for (int i=0; i<=powers.size()-1; i++){
            if (powers.get(i)=="Laser"){
                if (lasercounter<30){
                    lasercounter++;
                }
                else{
                    lasercounter = 0;
                    powers.remove(i);
                }
            }
        }
        if (powers.contains("Catch")){
            ballx = p1.getRect().x + p1.getRect().width / 2 - getRect().width / 2;
            bally = p1.getRect().y-10;
            moving = false;
            powers.remove("Catch");
        }
        if (powers.contains("Slow")){
            vx = vx>0?3:-3;
            vy = vy>0?3:-3;
            if (slowcounter<200){
                slowcounter++;
            }
            else{
                slowcounter = 0;
                vx = vx>0?6:-6;
                vy = vy>0?6:-6;
                powers.remove("Slow");
            }
        }
        if (powers.contains("Duplicate")){
            ballList.add(new Ball(ballx,bally, powers));
            powers.remove("Duplicate");
        }
    }

    

    public Rectangle getRect(){
        return new Rectangle(ballx, bally, 10, 10);
    }


    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(ballx,bally,10,10);
    }
    public boolean getMoving() {
        return moving;
    }

    public void startMove() {
        moving = true;
    }
    // get y 
    public int getY() {
        return bally;
    }
    // get x
    public int getX() {
        return ballx;
    }
    // get vx
    public int getVX() {
        return vx;
    }
    // get vy
    public int getVY() {
        return vy;
    }


}