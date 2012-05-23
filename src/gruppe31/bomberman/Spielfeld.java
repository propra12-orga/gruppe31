package gruppe31.bomberman;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Spielfeld extends JFrame implements KeyListener, ActionListener {

	public boolean[][] isBrickCell = new boolean[20][20]; 
	public JLabel[][] cell = new JLabel[20][20];
	int playerPositionX = 0;
	int playerPositionY = 0;
	int bombPositionX = 0;
	int bombPositionY = 0;

	ImageIcon playerIcon = new ImageIcon(this.getClass().getResource("player.jpg"));
	ImageIcon bombIcon = new ImageIcon(this.getClass().getResource("bomb.jpg"));
	ImageIcon playerAndBombIcon = new ImageIcon(this.getClass().getResource("playerAndBomb.jpg"));
	boolean isBombInPlayground = false;
	Timer bombTimer;


	public Spielfeld(){
		//JFrame frame= new JFrame("Bomberman");
		//frame.addKeyListener(this);
		addKeyListener(this);
		JPanel pa= new JPanel();
		JPanel pa2= new JPanel();
		ImageIcon icon2 = new ImageIcon(this.getClass().getResource("brick.jpg"));	
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
		Spielfeld spielfeld = new Spielfeld();
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_KP_LEFT) {
			System.out.println("left key");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionY >= 1 && !isBrickCell[playerPositionX][playerPositionY - 1]) {
				if (playerAndBombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(bombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(null);
				}
				playerPositionY--;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				if (bombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(playerAndBombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(playerIcon);
				}
			}
		} else if ((keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_KP_RIGHT)) {
			System.out.println("right key");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionY <= 18 && !isBrickCell[playerPositionX][playerPositionY + 1]) {
				if (playerAndBombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(bombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(null);
				}
				playerPositionY++;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				if (bombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(playerAndBombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(playerIcon);
				}
			}

		}else if ((keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_KP_UP)) {
			System.out.println("up key");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionX >= 1 && !isBrickCell[playerPositionX - 1][playerPositionY]) {
				if (playerAndBombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(bombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(null);
				}
				playerPositionX--;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				if (bombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(playerAndBombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(playerIcon);
				}
			}

		}else if ((keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_KP_DOWN)) {
			System.out.println("down");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			if (playerPositionX <= 18 && !isBrickCell[playerPositionX + 1][playerPositionY]) {
				if (playerAndBombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(bombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(null);
				}
				playerPositionX++;
				System.out.println("New Player Position: " + playerPositionX + "," + playerPositionY);
				if (bombIcon.equals(cell[playerPositionX][playerPositionY].getIcon())) {
					cell[playerPositionX][playerPositionY].setIcon(playerAndBombIcon);
				} else {
					cell[playerPositionX][playerPositionY].setIcon(playerIcon);
				}
			}
		} else if (keyCode == KeyEvent.VK_SPACE && isBombInPlayground == false) {
			System.out.println("space bar");
			System.out.println("Bomb Position: " + playerPositionX + "," + playerPositionY);
			cell[playerPositionX][playerPositionY].setIcon(playerAndBombIcon);
			isBombInPlayground = true;
			bombPositionX = playerPositionX;
			bombPositionY = playerPositionY;
			bombTimer = new Timer(3000, this);
			bombTimer.start();
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(" Inside action Performed in the Timer");
		explodeBomb();
		isBombInPlayground = false;
		bombPositionX = 0;
		bombPositionY = 0;
		bombTimer.stop();
	}

	public void explodeBomb() {
		//(x-1,y-1),(x-1, y),(x-1, y+1) 
		//(x, y-1),(x,y),(x, y+1)
		//(x+1, y-1), (x+1, y),(x+1,y+1)

		//Reihe x-1
		if (bombPositionX - 1 >= 0) {
			if (bombPositionY - 1 >= 0) {
				cell[bombPositionX -1][bombPositionY -1].setIcon(null);
				isBrickCell[bombPositionX -1][bombPositionY -1] = false;
			}
			cell[bombPositionX -1][bombPositionY].setIcon(null);
			isBrickCell[bombPositionX -1][bombPositionY] = false;
			if (bombPositionY + 1 <= 19) {
				cell[bombPositionX -1][bombPositionY + 1].setIcon(null);
				isBrickCell[bombPositionX -1][bombPositionY + 1] = false;
			}
		}

		//Reihe x
		if (bombPositionY - 1 >= 0) {
			cell[bombPositionX][bombPositionY -1].setIcon(null);
			isBrickCell[bombPositionX][bombPositionY -1] = false;
		}
		cell[bombPositionX][bombPositionY].setIcon(null);
		isBrickCell[bombPositionX][bombPositionY] = false;
		if (bombPositionY + 1 <= 19) {
			cell[bombPositionX][bombPositionY + 1].setIcon(null);
			isBrickCell[bombPositionX][bombPositionY + 1] = false;
		}

		//Reihe x-1
		if (bombPositionX + 1 <= 19) {
			if (bombPositionY - 1 >= 0) {
				cell[bombPositionX + 1][bombPositionY -1].setIcon(null);
				isBrickCell[bombPositionX + 1][bombPositionY - 1] = false;
			}
			cell[bombPositionX + 1][bombPositionY].setIcon(null);
			isBrickCell[bombPositionX + 1][bombPositionY] = false;
			if (bombPositionY + 1 <= 19) {
				isBrickCell[bombPositionX + 1][bombPositionY + 1] = false;
				cell[bombPositionX + 1][bombPositionY + 1].setIcon(null);
			}
		}
	}



	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
}

