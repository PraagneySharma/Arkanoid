/**
 * @(#)Player.java
 *
 *
 * @author 
 * @version 1.00 2024/4/5
 */
import java.awt.*;
import java.util.ArrayList;

public class LoseLife {
    private static int lives;
    private static int score;
    private static Font comicFnt = new Font("Comic Sans MS", Font.BOLD, 30);

    public LoseLife() {
        lives=3;

    }

    public static void findBalls(ArrayList<Ball> ball, ArrayList<Block> blocks, ArrayList<String> powers){
        // IF BALL HITS BLOCK THEN ADD SCORE
        for (int i=0; i<blocks.size(); i++){
            for (int j=0; j<ball.size(); j++){
                Rectangle ballRec = ball.get(j).getRect();       
                Rectangle blockRec = blocks.get(i).getRect(); 
                if(ballRec.intersects(blockRec)){
                    score+=100;
                }
            }
        }
        // IF BALL HITS BOTTOM THEN REMOVE BALL & IF NO BALLS ON SCREEN REMOVE A LIFE
        for (int i=0; i<=ball.size()-1; i++){
            if (ball.get(i).getRect().y > 600){
                ball.remove(i);
                if (ball.size() == 0){
                    lives--;
                }
            }

        }
        if (powers.contains("Player")){
            lives++;
            powers.remove("Player");
        }
    }
    public int warpIntersect(Player p1){
        // IF U INTERSECT WITH WARP TOOL
        Rectangle warpRect = p1.getWarpRect();
        Rectangle playerRect = p1.getRect();
        if (warpRect.intersects(playerRect)){
            return 1;
        }
        return 0;
    }

    public int getLives(){
        return lives;
    }
    public void setLives(int l){
        lives = l;
    }
    public int getScore(){
        return score;
    }
    public void setScore(int s){
        score = s;
    }




    public void draw(Graphics g){
        g.setFont(comicFnt);
        g.setColor(Color.WHITE);
        g.drawString(""+score, 550,50);
        g.drawString(""+lives, 50,50);


    }   
}
