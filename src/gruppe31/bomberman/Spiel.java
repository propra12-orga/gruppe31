
package gruppe31.bomberman;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Spiel extends JFrame implements KeyListener {
	
	public boolean[][] isBrickCell = new boolean[20][20]; 
	public JLabel[][] cell = new JLabel[20][20];
	int playerPositionX = 0;
	int playerPositionY = 0;
	ImageIcon playerIcon = new ImageIcon(this.getClass().getResource("bomber.jpg"));
	
	public Spiel(){
	//JFrame frame= new JFrame("Bomberman");
	//frame.addKeyListener(this);
	addKeyListener(this);
	JPanel pa= new JPanel();
	JPanel pa2= new JPanel();
	ImageIcon icon2 = new ImageIcon(this.getClass().getResource("bricks2.jpg"));	
	//frame.add(pa);
	this.add(pa);
	pa.setLayout(new BorderLayout());
	pa.add(new JButton(""),BorderLayout.NORTH);
	pa.add(new JButton(""), BorderLayout.EAST);
	pa.add(new JButton(""), BorderLayout.WEST);
	pa.add(new JButton(""),BorderLayout.SOUTH);
	pa2.setLayout(new GridLayout(20, 20));

    for(int i = 0; i < 20; i++) {
      for(int j = 0; j < 20; j++) {
       JLabel l = new JLabel();
       cell[i][j] = l;
       Random generator = new Random();
       int random1 = generator.nextInt(20);
       int random2 = generator.nextInt(20);
       if ((j == random1) || (i == random1) || (j==random1+1) ||(i == random1+1)){
    	   l.setIcon(icon2);
    	   isBrickCell[i][j] = true;
       }
       else if ((j ==random2) || (i == random2)||(j ==random2 +1)||(i==random2 +1)){
    	   l.setIcon(icon2);
    	   isBrickCell[i][j] = true;
       }
       pa2.add(l);
       
      }
    }
    pa.add(pa2,BorderLayout.CENTER);
//	frame.setSize(300,300);
//	frame.setVisible(true);
//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(300,300);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	
	//Keep (0,0) (0,1) (1,0) (1,1) (2,0) (2,1) free of bricks 
	cell[0][0].setIcon(null);
	cell[0][1].setIcon(null);
	cell[1][0].setIcon(null);
	cell[1][1].setIcon(null);
	cell[2][0].setIcon(null);
	cell[2][1].setIcon(null);
	isBrickCell[0][0] = false;
	isBrickCell[0][1] = false;
	isBrickCell[1][0] = false;
	isBrickCell[1][1] = false;
	isBrickCell[2][0] = false;
	isBrickCell[2][1] = false;
	
	//Position the player in 0,0
	cell[0][0].setIcon(playerIcon);
	this.setFocusable(true);
	
	}
 
		
	public static void main(String[] args) {
		Spiel spiel = new Spiel();

    }


	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_KP_LEFT) {
			System.out.println("left key");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionY >= 1 && !isBrickCell[playerPositionX][playerPositionY - 1]) {
				cell[playerPositionX][playerPositionY].setIcon(null);
				playerPositionY--;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				cell[playerPositionX][playerPositionY].setIcon(playerIcon);
			}
		} else if ((keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_KP_RIGHT)) {
			System.out.println("right key");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionY <= 18 && !isBrickCell[playerPositionX][playerPositionY + 1]) {
				cell[playerPositionX][playerPositionY].setIcon(null);
				playerPositionY++;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				cell[playerPositionX][playerPositionY].setIcon(playerIcon);
			}

		}else if ((keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_KP_UP)) {
			System.out.println("up key");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionX >= 1 && !isBrickCell[playerPositionX - 1][playerPositionY]) {
				cell[playerPositionX][playerPositionY].setIcon(null);
				playerPositionX--;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				cell[playerPositionX][playerPositionY].setIcon(playerIcon);
			}

		}else if ((keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_KP_DOWN)) {
			System.out.println("down");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionX <= 18 && !isBrickCell[playerPositionX + 1][playerPositionY]) {
				cell[playerPositionX][playerPositionY].setIcon(null);
				playerPositionX++;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				cell[playerPositionX][playerPositionY].setIcon(playerIcon);
			}
		} 	
	                 	// TODO Auto-generated method stub	
                //		System.out.println("keyPressed");	
	}
	
	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent arg0) {
         // TODO Auto-generated method stub
	}

	}

