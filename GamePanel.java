import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

// Main game logic
class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{
    Timer timer;
    boolean []keys;
    Font comicFnt=null;
    Image back; 
    ArrayList<Ball> ballList;
    Capsule movecapsule;
    ArrayList<Block> blocks;
    ArrayList<Capsule> capsules;
    ArrayList<Integer> capsulePos;
    ArrayList<String> powers;
    ArrayList<Laser> bulletsList;
    Player player1;
    LoseLife life;
    static String screen = "intro";
    Image intro, backround;
    Image gameover;
    
    
    
    public GamePanel(){
        setPreferredSize(new Dimension(800, 600));
        comicFnt = new Font("Comic Sans MS", Font.PLAIN, 32);
        intro = Util.loadImage("intro.png");
        backround = Util.loadImage("backround.png");
        gameover = Util.loadImage("gameover.jpg");
        player1 = new Player(335, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        ballList = new ArrayList<Ball>();
        blocks = new ArrayList<Block>();
        capsules = new ArrayList<Capsule>();
        capsulePos = new ArrayList<Integer>();
        powers = new ArrayList<String>();
        bulletsList = new ArrayList<Laser>();
        life = new LoseLife();
        
      
        keys = new boolean[1000];
        timer = new Timer(15, this);
        timer.start();
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
    }
    
    public void move(){
        LoseLife.findBalls(ballList, blocks, powers);
        if (player1.move(keys, capsules, powers, bulletsList) || blocks.size() == 0){
            nextlevel();
        }
        Laser.shoot(blocks, powers, player1, bulletsList); 
        for (int j=0; j<capsules.size(); j++){ 
            capsules.get(j).move(ballList, player1, capsules, bulletsList, blocks);
        }
        for (int i=0; i<bulletsList.size(); i++){
            bulletsList.get(i).move(blocks, bulletsList);
        }
        for (int i=0; i<ballList.size(); i++){
            ballList.get(i).move(player1, blocks, powers, ballList);
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(screen == "level1" || screen == "level2" || screen == "level3"){
            if (life.getLives() == 0){
                screen = "gameOver";
            }
            else if (ballList.size() == 0){ // only add ball if u have no balls left but still have lives
                ballList.add(new Ball(player1.getX()+35, player1.getY()-10, powers));
            }
            for (int i=0; i<ballList.size(); i++){
                if (!ballList.get(i).getMoving() && keys[KeyEvent.VK_SPACE]) {           // only move on start when press space          
                    ballList.get(i).startMove();
                }
            }
            

            move();  
        }  
    
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        keys[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e){}
    
    @Override
    public void mouseClicked(MouseEvent e){}
    
    @Override
    public void mouseEntered(MouseEvent e){}
    
    @Override
    public void mouseExited(MouseEvent e){}
    
    @Override
    public void mousePressed(MouseEvent e){
        if (screen == "intro"){
            life.setLives(3);
            life.setScore(0);
            blocks.removeAll(blocks);
            capsules.removeAll(capsules);
            powers.removeAll(powers);
            bulletsList.removeAll(bulletsList);
            for(int row=0; row<7; row++) {
                for (int column = 0; column<8; column++){
                    blocks.add(new Block(51 + column*87, 50 + row*40, 87, 40));
                }
            } 
            
            for(int row=0; row<7; row++) {
                for (int column = 0; column<8; column++){
                    if (Util.randint(0,6)==1){       
                        capsules.add(new Capsule(51 + column*87, 50 + row*40,0));
                    }
                }
            } 
            screen = "level1";
        }
        
        if (screen == "level1"|| screen == "level2" || screen == "level3") {
            debugReset();
        }
        if (screen == "gameOver"){
            screen = "intro";
        }
        if (screen == "Win"){
            screen = "intro";
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e){}
    
    @Override
    public void paint(Graphics g){
        
        if(screen == "level1" || screen == "level2" || screen == "level3"){
            g.drawImage(backround,0,0,null);
            player1.draw(g);
            life.draw(g);
            for (Ball ball : ballList) {
                ball.draw(g);
            }
            for (Capsule c : capsules){
                c.draw(g);
            }
            for (Block b : blocks){
                b.draw(g);
            }           
            for (Laser l : bulletsList){
                l.draw(g);
            }   
        }
        
        if(screen == "intro"){
            g.drawImage(intro,0,0,null);
        }
        
        if (screen == "gameOver"){
            ballList.add(new Ball(player1.getX()+35, player1.getY()-10, powers));
            g.drawImage(gameover,0,0,null);
        }
        if (screen == "Win"){
            g.setColor(Color.BLACK);
            g.setFont(comicFnt);
            g.fillRect(0, 0, 800, 600);
            g.setColor(Color.WHITE);
            g.drawString("You Win! \n CLICK TO RESTART \n Score: " + life.getScore(), 50, 350);
        }
        
    }
    public void checklevel(){
        int intersectwarp = life.warpIntersect(player1); 
        if (blocks.size() == 0 || intersectwarp == 1){

            if (screen == "level1"){
                screen = "level2";
            }
            else if (screen == "level2"){
                screen = "level3";

            }
            else if (screen == "level3"){
                screen = "Win";
            }
        } 
    }
    // check level
    public void nextlevel(){  // level builder
        checklevel();
        if (screen == "level1"){
            ballList.removeAll(ballList);
            blocks.removeAll(blocks);
            capsules.removeAll(capsules);
            powers.removeAll(powers);
            for(int row=0; row<7; row++) {
                for (int column = 0; column<8; column++){
                    blocks.add(new Block(51 + column*87, 50 + row*40, 87, 40));
                }
            } 
            
            for(int row=0; row<7; row++) {
                for (int column = 0; column<8; column++){
                    if (Util.randint(0,6)==1){       
                        capsules.add(new Capsule(51 + column*87, 50 + row*40,0));
                    }
                }
            }
        }
        if (screen == "level2"){
            ballList.removeAll(ballList);
            blocks.removeAll(blocks);
            capsules.removeAll(capsules);
            powers.removeAll(powers);
            for(int row=0; row<9; row++) {
                for (int column = 0; column<8; column++){
                    blocks.add(new Block(51 + column*87, 50 + row*40, 87, 40));
                }
            } 
            
            for(int row=0; row<9; row++) {
                for (int column = 0; column<8; column++){
                    if (Util.randint(0,6)==1){       
                        capsules.add(new Capsule(51 + column*87, 50 + row*40,0));
                    }
                }
            }
        }
        if (screen == "level3"){
            ballList.removeAll(ballList);
            blocks.removeAll(blocks);
            capsules.removeAll(capsules);
            powers.removeAll(powers);
            for(int row=0; row<11; row++) {
                for (int column = 0; column<8; column++){
                    blocks.add(new Block(51 + column*87, 50 + row*40, 87, 40));
                }
            } 
            
            for(int row=0; row<11; row++) {
                for (int column = 0; column<8; column++){
                    if (Util.randint(0,6)==1){       
                        capsules.add(new Capsule(51 + column*87, 50 + row*40,0));
                    }
                }
            }
        }
    }
    
    
    public static void main(String[] arguments) {
        new Arkanoid();  
    }
    
    private void debugReset() { // reset the game to level 1 to find bugs more easily
        life.setScore(0);
        blocks.removeAll(blocks);
        capsules.removeAll(capsules);
        powers.removeAll(powers);
        bulletsList.removeAll(bulletsList);
        ballList.removeAll(ballList);

        for(int row=0; row<7; row++) {
            for (int column = 0; column<8; column++){
                blocks.add(new Block(51 + column*87, 50 + row*40, 87, 40));
            }
        } 
        
        for(int row=0; row<7; row++) {
            for (int column = 0; column<8; column++){
                if (Util.randint(0,6)==1){       
                    capsules.add(new Capsule(51 + column*87, 50 + row*40,0));
                }
            }
        } 
    }
}