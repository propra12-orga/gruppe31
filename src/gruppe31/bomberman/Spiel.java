package gruppe31.bomberman;


import java.awt.BorderLayout;
import java.awt.Frame;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Spiel extends JFrame implements KeyListener, ActionListener {

	public boolean[][] isBrickCell = new boolean[20][20]; 
	public JLabel[][] cell = new JLabel[20][20];
	int playerPositionX = 0;
	int playerPositionY = 0;
	int bombPositionX = 0;
	int bombPositionY = 0;
	int exitPositionX = 0;
	int exitPositionY = 0;

	ImageIcon playerIcon = new ImageIcon(this.getClass().getResource("mario_small_1.jpg"));
	ImageIcon bombIcon = new ImageIcon(this.getClass().getResource("bomb.jpg"));
	ImageIcon playerAndBombIcon = new ImageIcon(this.getClass().getResource("mario_with_bomb1.jpg"));
	ImageIcon exitIcon = new ImageIcon(this.getClass().getResource("exit.jpeg"));
	boolean isBombInPlayground = false;
	Timer bombTimer;

	Spiel spiel = null;


	public Spiel(){

	}


	public void initaliseSpiel() {
		//JFrame frame= new JFrame("Bomberman");
		//frame.addKeyListener(this);
		createMenu();
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
		//this.setSize(300,300);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
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
		placeExitDoor();
		this.setFocusable(true);
	}
	public void createMenu() {
		// Create the menu bar
		JMenuBar menuBar = new JMenuBar();
		// Create a menu
		JMenu menu = new JMenu("Bomberman");
		menuBar.add(menu);

		// Create a menu item
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(this);
		menu.add(restart);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);

		// Install the menu bar in the frame
		this.setJMenuBar(menuBar);
	}
	public void placeExitDoor() {
		//Place the Exit Door somewhere between 15th to 19th row and 15th to 19th column.
		Random generator = new Random();
		int row = 0;
		int column = 0;
		row = generator.nextInt(20);
		while (row < 15) {
			row = generator.nextInt(20);
		}
		column = generator.nextInt(20);
		while (column < 15) {
			column = generator.nextInt(20);
		}
		exitPositionX = row;
		exitPositionY = column;
		cell[row][column].setIcon(exitIcon);
		isBrickCell[row][column] = false;
	}

	public void startGame() {
		this.initaliseSpiel();
	}

	public static void main(String[] args) {
		Spiel x = new Spiel();
		x.startGame();
	}
	public void exitDoorAction() {
		System.out.println("ExitDoor X: " + exitPositionX + " ExitDoor Y: " + exitPositionY);
		//if the curren player position equals Exit Door Position, Restart game.
		if (playerPositionX == exitPositionX && playerPositionY == exitPositionY) {
			System.out.println("Player exits via door.");
			//JDialog dialog = new JDialog(this, true);
			JOptionPane.showMessageDialog(this,"Game Over. Restarting...");
			this.setVisible(false);
			spiel = new Spiel();
			spiel.initaliseSpiel();
		}

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
		exitDoorAction();
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Restart")) {
			this.setVisible(false);
			spielfeld = new Spielfeld();
			spielfeld.initaliseSpielfeld();
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Exit")) {
			System.exit(0);
		} else {
			System.out.println(" Inside action Performed in the Timer");
			explodeBomb();
			isBombInPlayground = false;
			bombPositionX = 0;
			bombPositionY = 0;
			bombTimer.stop();
		}
	}

	public void isPlayerOnBomb(int x, int y) {
		if (playerIcon.equals(cell[x][y].getIcon()) || playerAndBombIcon.equals(cell[x][y].getIcon())) {
			JOptionPane.showMessageDialog(this,"Player Died. Game Over. Restarting...");
			this.setVisible(false);
			spielfeld = new Spielfeld();
			spielfeld.initaliseSpielfeld();
		}
		
	}
	public void explodeBomb() {
		//(x-1,y-1),(x-1, y),(x-1, y+1) 
		//(x, y-1),(x,y),(x, y+1)
		//(x+1, y-1), (x+1, y),(x+1,y+1)

		//Reihe x-1
		if (bombPositionX - 1 >= 0) {
			if (bombPositionY - 1 >= 0) {
				isPlayerOnBomb(bombPositionX -1, bombPositionY -1);
				cell[bombPositionX -1][bombPositionY -1].setIcon(null);
				isBrickCell[bombPositionX -1][bombPositionY -1] = false;
			}
			isPlayerOnBomb(bombPositionX -1, bombPositionY);
			cell[bombPositionX -1][bombPositionY].setIcon(null);
			isBrickCell[bombPositionX -1][bombPositionY] = false;
			if (bombPositionY + 1 <= 19) {
				isPlayerOnBomb(bombPositionX -1, bombPositionY + 1);
				cell[bombPositionX -1][bombPositionY + 1].setIcon(null);
				isBrickCell[bombPositionX -1][bombPositionY + 1] = false;
			}
		}

		//Reihe x
		if (bombPositionY - 1 >= 0) {
			isPlayerOnBomb(bombPositionX, bombPositionY -1);
			cell[bombPositionX][bombPositionY -1].setIcon(null);
			isBrickCell[bombPositionX][bombPositionY -1] = false;
		}
		isPlayerOnBomb(bombPositionX, bombPositionY);
		cell[bombPositionX][bombPositionY].setIcon(null);
		isBrickCell[bombPositionX][bombPositionY] = false;
		if (bombPositionY + 1 <= 19) {
			isPlayerOnBomb(bombPositionX, bombPositionY + 1);
			cell[bombPositionX][bombPositionY + 1].setIcon(null);
			isBrickCell[bombPositionX][bombPositionY + 1] = false;
		}

		//Reihe x-1
		if (bombPositionX + 1 <= 19) {
			if (bombPositionY - 1 >= 0) {
				isPlayerOnBomb(bombPositionX + 1, bombPositionY - 1);
				cell[bombPositionX + 1][bombPositionY -1].setIcon(null);
				isBrickCell[bombPositionX + 1][bombPositionY - 1] = false;
			}
			isPlayerOnBomb(bombPositionX + 1, bombPositionY);
			cell[bombPositionX + 1][bombPositionY].setIcon(null);
			isBrickCell[bombPositionX + 1][bombPositionY] = false;
			if (bombPositionY + 1 <= 19) {
				isPlayerOnBomb(bombPositionX + 1, bombPositionY + 1);
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



