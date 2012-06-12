package gruppe31.bomberman;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

/**
 * @author gruppe 31-Bomberman
 * Diese Klass Spiel erweitert JFrame und implementiert 2 Interface KeyListener und ActionListener
 *
 */
public class Spiel extends JFrame implements KeyListener, ActionListener {

	public boolean[][] isBreakableBrickCell = new boolean[20][20];
	public boolean[][] isUnbreakableBrickCell = new boolean[20][20];
	public JLabel[][] cell = new JLabel[20][20];
	int playerPositionX = 0;
	int playerPositionY = 0;
	List bombList = new ArrayList();
	boolean isBombTimerStarted = false;
	long lastBombPlacedTime = 0;
	int exitPositionX = 0;
	int exitPositionY = 0;
	
	// Bilder
	ImageIcon playerIcon = new ImageIcon(this.getClass().getResource("player.jpg"));
	ImageIcon player2Icon = new ImageIcon(this.getClass().getResource("player2.jpg"));
	ImageIcon bombIcon = new ImageIcon(this.getClass().getResource("bomb.jpg"));
	ImageIcon playerAndBombIcon = new ImageIcon(this.getClass().getResource("playerAndBomb.jpg"));
	ImageIcon exitIcon = new ImageIcon(this.getClass().getResource("exit.jpeg"));
	ImageIcon breakableBrickIcon = new ImageIcon(this.getClass().getResource("breakableWall.jpg"));
	ImageIcon unbreakableBrickIcon = new ImageIcon(this.getClass().getResource("unbreakableWall.jpg"));
	//boolean isBombInPlayground = false;
	Timer bombTimer;

	Spiel spiel = null;

	JPanel spielStatusPanel = new JPanel();
	//Default playerMode und gameLevel ist Single Player und 1
	JLabel playerMode = new JLabel("Single Player");
	JLabel gameLevel = new JLabel("1");
	
	public Spiel(){
	}

	/**
	 * Diese konstruktor initializiert neue Spielfeld mit ihre komponente wie Menu, Layout, JLabel, uw.
	 * In einer JFrame wird eine Panel mit BorderLayout eingefügt.In der Nord Teile der Flowlayout
	 * wird eine Panel mit der Rechts gestellte FlowLayout und 4 JLabel für Spieler Modus und Spielstatus.
	 * In mittlere Teil von BorderLayout , wird die 20x20 GridLayout eingefügt. Diese Zellen werden auch 
	 * als in 20 x 20 boolean und JLabel gespeichert.zwei Zufallzahlen werden generiert , die zerstörbare 
	 * und die unzerstörbare Wände werden in der zugehörigen Zellen eingefügt.(0,0) (0,1) (1,0) (1,1) (2,0) (2,1) 
	 * mit Absicht freigelassen,damit der 1.Speiler in position (0,0) beginnen kann.
	 * Falls playermode multiple Player ist, erscheint 2.Spieler in (19,0)....
	 * PlaceExitDoor methode wird aufgerufen.
	 * 
	 */
	public void initaliseSpiel() {
		createMenu();
		addKeyListener(this);
		JPanel pa= new JPanel();
		JPanel pa2= new JPanel();
		this.add(pa);
		// BorderLayout 
		pa.setLayout(new BorderLayout());
		// Panel mit der Rechts gestellte FlowLayout und JLabel
		spielStatusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 25));
		spielStatusPanel.add(new JLabel("Player Mode:"));
		spielStatusPanel.add(playerMode);
		spielStatusPanel.add(new JLabel("Level:"));
		spielStatusPanel.add(gameLevel);
		spielStatusPanel.setVisible(true);
		pa.add(spielStatusPanel, BorderLayout.NORTH);
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
		// Falls playermode multiple Player ist, erscheint 2.Spieler in 19,0
		cell[0][0].setIcon(playerIcon);
		if (playerMode.getText().equals("Multiple Player")) {
			cell[19][0].setIcon(player2Icon);
		}
		placeExitDoor();
		this.setFocusable(true);
	}
	/**
	 * generiert MenuBar ,in dem ein Menu mit Menu Einträge für Spieler Modus(single/multi-Player)
	 * und Spielstatus(restart / Exit) eingefügt wird. 
	 * implementiert auch ActionListener für Menueinträge
	 */
	public void createMenu() {
		// menuBar wird  erzeugt
		JMenuBar menuBar = new JMenuBar();
		// menu wird erzeugt und in menuBar eingefügt
		JMenu menu = new JMenu("Bomberman");
		menuBar.add(menu);

		 // Menüeinträge für einzelne und mehrfache(2) Spieler  
		JMenuItem singlePlayer = new JMenuItem("Single Player");
		singlePlayer.addActionListener(this);
		menu.add(singlePlayer);

		JMenuItem multiPlayer = new JMenuItem("Multi Player");
		multiPlayer.addActionListener(this);
		menu.add(multiPlayer);
		
		// Menüeinträge für Spiel wieder anzufangen und zu enden  
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(this);
		menu.add(restart);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);

		// menubar in frame eingesetzt
		this.setJMenuBar(menuBar);
	}
	/**
	 * generiert eine Zufallnummer und setzt die Ausgangstür innerhalb 15. - 19.Reihe und Spalten unten ein 
	 * (BreakableBrickIcon) zerstörbare Wand.
	 */
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
    
	/**
	 * rüft die Konstruktor das Spiel zu beginnen.
	 */
	// startGame methode wird definiert für das Spiel anzufangen
	public void startGame() {
		this.initaliseSpiel();
	}
	
   /**
    * überprüft ob der Spieler Position und AusgangstürPosition gleich ist( Reihen und spaltenweise des Arrays) 
    * Falls ja, wird die Nachricht "Game Over. Restarting..." mittels JOptionPanel ausgegeben und
    * neue Objekt wird erzeugt.
    */
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
	/**
	 *  überprüft ob die zerstörbare und unzerstörbare Wand vorhanden sind, und ob es die Spielfeld Grenze ist.
	 *  Falls nein, bewegt der Spieler Position ein Zell nach links, rechts, oben oder unten,
	 *  jenach die Tastatur-Pfeil gedrückt wird.Hier wird die aktuelle Hintergrundbild als null gesetzt.
	 *  Falls SPACEBAR gedrückt wird, wird die Hintergrund  als playerAndBombIcon geändert und Timer gestartet. 
	 *  
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_KP_LEFT) {
			System.out.println("left key");
			System.out.println("Current Player Position: " + playerPositionX + "," + playerPositionY);
			// linken Seite Bewegung
			/* Position der Spieler zu linken Seite ändern und aktuelle Hintergrundbild als null setzen	
             * mit Voraussetzung: keine Mauer, nicht die Grenze von Spielfeld.*/ 
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
			//Rechten Seite Bewegung
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
			// Spieler-Bewegung nach oben
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
			// Spieler-Bewegung nach unten
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
		} else if (keyCode == KeyEvent.VK_SPACE) {
			System.out.println("space bar");
			System.out.println("Bomb Position: " + playerPositionX + "," + playerPositionY);
			// Bomben anlegen- die Hintergrundbild als playerAndBombIcon geändert
			cell[playerPositionX][playerPositionY].setIcon(playerAndBombIcon);
			BombPosition bombPosition = new BombPosition();
			bombPosition.setPositionX(playerPositionX);
			bombPosition.setPositionY(playerPositionY);
			bombList.add(bombPosition);
			lastBombPlacedTime = new java.util.Date().getTime();
			//if (!isBombTimerStarted) {
				//isBombTimerStarted = true;
				bombTimer = new Timer(3000, this);
				bombTimer.restart();
			//}
		}
		exitDoorAction();
		
	}
    /**
     * Hier wird jenach die Menueinträge, die die Benutzer wählt, wird die Spieler Modus und Spiel Status geändert.
     * Default ist als Single Player eingesetzt.Wenn man Multiplayer klickt, erscheint dann 2.Spieler in 
     * [19][0]...
     * Wenn man Restart klickt, wird ein neue Objekt erzeugt und initialiseSpiel() methode aufgerufen.
     * Wenn man Exit klickt, wird das Spiel beendet...
     */
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		// Reaktionen für Menueinträge :'Single Player', 'Multiple Player', 'Restart' und 'Exit'
		if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Single Player")) {
			playerMode.setText("Single Player");
			cell[19][0].setIcon(null);
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Multi Player")) {
			playerMode.setText("Multiple Player");
			cell[19][0].setIcon(player2Icon);
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Restart")) {
			this.setVisible(false);
			spiel = new Spiel();
			spiel.initaliseSpiel();
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Exit")) {
			System.exit(0);
		} else {
			System.out.println(" Inside action Performed in the Timer");
			explodeBomb();
			isBombTimerStarted = false;
			//bombList = new ArrayList();
			//bombPositionX = 0;
			//bombPositionY = 0;
			//bombTimer.stop();
		}
	}
   /**
    * Hier wird die 9 Umgebung Zellen (reihen und spaltenweise)
    * von aktuelle Position entleert,mit der Methode ClearCellIfBreakable(), die nur die zerstörbare Wände entleert.   
    */
	public void explodeBomb() {
		if (bombList != null && bombList.size() <= 1 && new Date().getTime() - lastBombPlacedTime < 3000) {
			bombTimer.restart();
		} else if (bombList != null && bombList.size() > 0) {
			BombPosition bombPosition = (BombPosition) bombList.get(0);
			int bombPositionX = bombPosition.getPositionX();
			int bombPositionY = bombPosition.getPositionY();
			bombList.remove(0);
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
		//bombTimer.restart();
	}

	/**
	 * Diese Methode startet die spiel wieder wenn spieler in Bomb position trifft;
	 * entleert die Zell, wenn die ein zerstörbares Wand Icon hat.
	 * @param x : Reihe der Array
	 * @param y : Spalten der Array
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
	 * Diese Methode überprüft, ob der Icon ,playerIcon / playerAndBombIcon ist und gibt die Nachricht 
	 * "Player Dead.Game Over.Restarting..." aus. Eine neue Objekt wird erzeugt und die Methode initialiseSpiel()
	 * wird aufgerufen, damit ein neues Spiel beginnt.
	 * @param x Reihe der JLabelarray
	 * @param y Spalte der JLabelarray
	 */
	public void restartIfPlayerOnBombField(int x, int y) {
		// informiert dass der Speiler tot ist und wird das Spiel neue gestartet.
		if (playerIcon.equals(cell[x][y].getIcon()) || playerAndBombIcon.equals(cell[x][y].getIcon())) {
			JOptionPane.showMessageDialog(this,"Player Dead. Game Over. Restarting...");
			this.setVisible(false);
			spiel = new Spiel();
			spiel.initaliseSpiel();
		}
	}

	/**
	 * Diese Methode überprüft, ob der boolean isBreakableBrickCell und isUnbreakableBrickCell 
	 * in x,y false ist 
	 * @param x Reihe der Booleanarray
	 * @param y Spalten der Booleanarray
	 * @return true wenn
	 */
	public boolean isCellFreeFromBricks(int x, int y) {
		//überprüft ob der Zell frei ist und gibt true züruck, sonst wird false züruckgegeben.
		if (!isBreakableBrickCell[x][y] && !isUnbreakableBrickCell[x][y]) {
			return true;
		}
		return false;
	}
    /**
     * es passiert nichts, wenn Tastatur freigegeben wird,
     * 
     */
	public void keyReleased(KeyEvent e) {
	}
	/**
	 * es passiert nichts, wenn man versucht, etwas mit Tastatur zu tippen
	 */
	public void keyTyped(KeyEvent arg0) {
	}
	
    /** 
     * Hauptprogramm. Objekt erzeugt und 'startGame' methode wird aufgerufen.
     *
     * @param args 
     */

	public static void main(String[] args) {
		Spiel x = new Spiel();
		// Spiel ist mit startGame methode gestartet
		x.startGame();
	}
}



