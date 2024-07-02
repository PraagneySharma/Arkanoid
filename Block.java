import java.awt.*;
import java.util.ArrayList;

public class Block {
    private int x,y, width, height;
    private Image image;
    private static final String[] colors = {"Blue","Green", "LightBlue", "Orange"};
    
    public Block(int x, int y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.image = Util.loadImage("ArkanoidWalls/" + colors[Util.randint(0, colors.length - 1)]+ "Wall.png");
    }
    
    public int BlockHit(int ballx, int bally, int ballvx, int ballvy, ArrayList<Block> blocks){
        Rectangle rec = getRect();
        int hit = 0;
        
        // IF IT HITS THE SIDE OF THE BLOCK IT SHOULD BOUNCE DEPENDING ON WHICH SIDE 
        if (rec.contains(new Point(ballx+ballvx, bally+ballvy)) && ballvx>0 && ballvy<0){ // Y BOUNCE 
            hit = 3;
        }
        if (rec.contains(new Point(ballx+ballvx, bally)) || rec.contains(new Point(ballx-ballvx, bally)) ){ // Y BOUNCE 
            hit = 2;
        }
        else if (rec.contains(new Point(ballx, bally+ballvy)) || rec.contains(new Point(ballx, bally-ballvy))){ // X BOUNCE
            hit = 1;
        }
        return hit;
        
    }
    //get x
    public int getX(){
        return x;
    }
    //get y
    public int getY(){
        return y;
    }
    //get width
    public int getWidth(){
        return width;
    }
    //get height
    public int getHeight(){
        return height;
    }
    
    public void draw(Graphics g){
        g.drawImage(this.image, x, y, null);
    }
    
    public Rectangle getRect(){
        return new Rectangle(x, y, width, height);
    }
    
}