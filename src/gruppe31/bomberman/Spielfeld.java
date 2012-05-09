package gruppe31.bomberman;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Spielfeld {
	
	public Spielfeld(){
	JFrame frame= new JFrame("Bomberman");
	JPanel pa= new JPanel();
	JPanel pa2= new JPanel();
	ImageIcon icon2 = new ImageIcon(this.getClass().getResource("bricks2.jpg"));	
	frame.add(pa);
	pa.setLayout(new BorderLayout());
	pa.add(new JButton(""),BorderLayout.NORTH);
	pa.add(new JButton(""), BorderLayout.EAST);
	pa.add(new JButton(""), BorderLayout.WEST);
	pa.add(new JButton(""),BorderLayout.SOUTH);
	pa2.setLayout(new GridLayout(20, 20));

    for(int y = 0; y < 20; y++) {
      for(int x = 0; x < 20; x++) {
       JLabel l = new JLabel();
       Random generator = new Random();
       int random1 = generator.nextInt(20);
       int random2 = generator.nextInt(20);
       if ((x == random1) || (y == random1) || (x==random1+1) ||(y == random1+1)){
       l.setIcon(icon2);
       }
       else if ((x ==random2) || (y == random2)||(x ==random2 +1)||(y==random2 +1)){
       l.setIcon(icon2);
       }
       pa2.add(l);
       
      }
    }
    pa.add(pa2,BorderLayout.CENTER);
	frame.setSize(300,300);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
 
		


	public static void main(String[] args) {
		Spielfeld speilfeld = new Spielfeld();
		
    }

	}

