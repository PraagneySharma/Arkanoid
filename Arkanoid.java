
import javax.swing.*;

public class Arkanoid extends JFrame{
    GamePanel game= new GamePanel();
    
    public Arkanoid() {
        super("Arkanoid!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(game);
        pack();  
        setVisible(true);
    }    
    public static void main(String[] arguments) {
        new Arkanoid();  
    }
}
