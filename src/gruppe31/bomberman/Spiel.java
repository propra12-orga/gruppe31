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

	public boolean[][] isBreakableBrickCell = new boolean[20][20];
	public boolean[][] isUnbreakableBrickCell = new boolean[20][20];
	public JLabel[][] cell = new JLabel[20][20];
	int playerPositionX = 0;
	int playerPositionY = 0;
	int bombPositionX = 0;
	int bombPositionY = 0;
	int exitPositionX = 0;
	int exitPositionY = 0;
	
    // Bilder
	ImageIcon playerIcon = new ImageIcon(this.getClass().getResource("player.jpg"));
	ImageIcon bombIcon = new ImageIcon(this.getClass().getResource("bomb.jpg"));
	ImageIcon playerAndBombIcon = new ImageIcon(this.getClass().getResource("playerAndBomb.jpg"));
	ImageIcon exitIcon = new ImageIcon(this.getClass().getResource("exit.jpeg"));
	ImageIcon breakableBrickIcon = new ImageIcon(this.getClass().getResource("breakableWall.jpg"));
	ImageIcon unbreakableBrickIcon = new ImageIcon(this.getClass().getResource("unbreakableWall.jpg"));
	boolean isBombInPlayground = false;
	Timer bombTimer;

	Spiel spiel = null;

	public Spiel(){
	}

	public void initaliseSpiel() {
		createMenu();
		addKeyListener(this);
		JPanel pa= new JPanel();
		JPanel pa2= new JPanel();
		this.add(pa);
		pa.setLayout(new BorderLayout());
		pa.add(new JButton(""),BorderLayout.NORTH);
		pa.add(new JButton(""), BorderLayout.EAST);
		pa.add(new JButton(""), BorderLayout.WEST);
		pa.add(new JButton(""),BorderLayout.SOUTH);
		pa2.setLayout(new GridLayout(20, 20));
		
		// zwei Zufallzahlen werden generiert , die zerstörbare und die unzerstörbare Wände werden eingefügt

		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				JLabel l = new JLabel();
				cell[i][j] = l;
				Random generator = new Random();
				int random1 = generator.nextInt(20);
				int random2 = generator.nextInt(20);
				if ((j == random1) || (i == random1) || (j==random1+1) ||(i == random1+1)){
					l.setIcon(breakableBrickIcon);
					isBreakableBrickCell[i][j] = true;
				}
				else if ((j ==random2) || (i == random2)||(j ==random2 +1)||(i==random2 +1)){
					l.setIcon(unbreakableBrickIcon);
					isUnbreakableBrickCell[i][j] = true;
				}
				pa2.add(l);

			}
		}
		// In mittlere Teil von BorderLayout , wird die 20x20 GridLayout eingefügt
		pa.add(pa2,BorderLayout.CENTER);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// (0,0) (0,1) (1,0) (1,1) (2,0) (2,1) mit Absicht freigelassen 
		cell[0][0].setIcon(null);
		cell[0][1].setIcon(null);
		cell[1][0].setIcon(null);
		cell[1][1].setIcon(null);
		cell[2][0].setIcon(null);
		cell[2][1].setIcon(null);
		isBreakableBrickCell[0][0] = false;
		isBreakableBrickCell[0][1] = false;
		isBreakableBrickCell[1][0] = false;
		isBreakableBrickCell[1][1] = false;
		isBreakableBrickCell[2][0] = false;
		isBreakableBrickCell[2][1] = false;

		//Position der Spieler in 0,0 zu beginnen
		cell[0][0].setIcon(playerIcon);
		placeExitDoor();
		this.setFocusable(true);
	}
	public void createMenu() {
		// menuBar wird  erzeugt
		JMenuBar menuBar = new JMenuBar();
		// menu wird wird erzeugt und in menuBar eingefügt
		JMenu menu = new JMenu("Bomberman");
		menuBar.add(menu);

		// 2 menuitem wird erzeugt mit ActionListener in menu eingefügt  
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(this);
		menu.add(restart);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);

		// menubar in frame eingesetzt
		this.setJMenuBar(menuBar);
	}
	public void placeExitDoor() {
		// Eine Zufallnummer generiert und die Ausgangtür innerhalb 15.- 19. -reihe und -Spalten unter 
		// eine zerstörbare Wand versteckt
		
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
		cell[row][column].setIcon(breakableBrickIcon);
		isBreakableBrickCell[row][column] = true;
	}
    // startGame methode wird definiert für das Spiel anzufangen
	public void startGame() {
		this.initaliseSpiel();
	}

	
	public static void main(String[] args) {
		Spiel x = new Spiel();
		// Spiel ist mit startGame methode gestartet
		x.startGame();
	}
	public void exitDoorAction() {
		System.out.println("ExitDoor X: " + exitPositionX + " ExitDoor Y: " + exitPositionY);
		//Falls die Spieler position gleich wie die Ausgangstür Position, wird das Spiel gestartet 
		if (playerPositionX == exitPositionX && playerPositionY == exitPositionY) {
			System.out.println("Player exits via door.");
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
			if (playerPositionY >= 1 && isCellFreeFromBricks(playerPositionX, playerPositionY - 1)) {
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
			if (playerPositionY <= 18 && isCellFreeFromBricks(playerPositionX, playerPositionY + 1)) {
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
			if (playerPositionX >= 1 && isCellFreeFromBricks(playerPositionX - 1, playerPositionY)) {
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
			if (playerPositionX <= 18 && isCellFreeFromBricks(playerPositionX + 1, playerPositionY)) {
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
			spiel = new Spiel();
			spiel.initaliseSpiel();
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

	
	public void explodeBomb() {
		//Reihe x-1
		if (bombPositionX - 1 >= 0) {
			if (bombPositionY - 1 >= 0) {
				clearCellIfBreakable(bombPositionX - 1, bombPositionY -1);
			}
			clearCellIfBreakable(bombPositionX - 1, bombPositionY);
			if (bombPositionY + 1 <= 19) {
				clearCellIfBreakable(bombPositionX - 1, bombPositionY + 1);
			}
		}

		//Reihe x
		if (bombPositionY - 1 >= 0) {
			clearCellIfBreakable(bombPositionX, bombPositionY - 1);
		}
		clearCellIfBreakable(bombPositionX, bombPositionY);
		if (bombPositionY + 1 <= 19) {
			clearCellIfBreakable(bombPositionX, bombPositionY + 1);
		}

		//Reihe x-1
		if (bombPositionX + 1 <= 19) {
			if (bombPositionY - 1 >= 0) {
				clearCellIfBreakable(bombPositionX + 1, bombPositionY - 1);
			}
			clearCellIfBreakable(bombPositionX + 1, bombPositionY);
			if (bombPositionY + 1 <= 19) {
				clearCellIfBreakable(bombPositionX + 1, bombPositionY + 1);
			}
		}
	}

	/**
	 * clear the cell x,y of bricks (if it is breakable brick).
	 * @param x
	 * @param y
	 */
	public void clearCellIfBreakable(int x, int y) {
		restartIfPlayerOnBombField(x, y);
		//wenn es ein Ausgangstür position ist
		if (x == exitPositionX && y == exitPositionY) {
			if (exitIcon.equals(cell[x][x].getIcon())) {
				return;
			} else {
				cell[x][y].setIcon(exitIcon);
				isBreakableBrickCell[x][y] = false;
				return;
			}
		}
		//die Zell entleeren,nur wann die ein zerstörbares Wand hat
		if (!isUnbreakableBrickCell[x][y]) {
			cell[x][y].setIcon(null);
			isBreakableBrickCell[x][y] = false;
		}
	}
	/**
	 * Restart the game if player is in the Cell x, y when the bomb explodes.
	 * @param x
	 * @param y
	 */
	public void restartIfPlayerOnBombField(int x, int y) {
		if (playerIcon.equals(cell[x][y].getIcon()) || playerAndBombIcon.equals(cell[x][y].getIcon())) {
			JOptionPane.showMessageDialog(this,"Player Dead. Game Over. Restarting...");
			this.setVisible(false);
			spiel = new Spiel();
			spiel.initaliseSpiel();
		}
	}

	/**
	 * Check if a cell is free of bricks
	 * @param x
	 * @param y
	 * @return true, if the cell is free from Brick,
	 *         false, otherwise
	 */
	public boolean isCellFreeFromBricks(int x, int y) {
		if (!isBreakableBrickCell[x][y] && !isUnbreakableBrickCell[x][y]) {
			return true;
		}
		return false;
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
}

