package gruppe31.bomberman;

//package gruppe31.bomberman;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
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
 * Diese Klasse Spiel erweitert JFrame und implementiert 2 Interface KeyListener und ActionListener
 *
 */
public class Spiel extends JFrame implements KeyListener, ActionListener, Serializable {

	JPanel topPanel = null;
	JPanel cellPanel = null;
	
	public boolean[][] isBreakableBrickCell = new boolean[20][20];
	public boolean[][] isUnbreakableBrickCell = new boolean[20][20];
	public JLabel[][] cell = new JLabel[20][20];
	public int player1PositionX = 0;
	public int player1PositionY = 0;
	public int player2PositionX = 19;
	public int player2PositionY = 0;
	public List bombList = new ArrayList();
	public boolean isBombTimerStarted = false;
	public boolean isMultiPlayerMode = false;
	public long lastBombPlacedTime = 0;
	public int exitPositionX = 0;
	public int exitPositionY = 0;
	
	// Bilder
	ImageIcon player1Icon = new ImageIcon(this.getClass().getResource("player1.png"));
	ImageIcon player2Icon = new ImageIcon(this.getClass().getResource("player2.png"));
	ImageIcon bombIcon = new ImageIcon(this.getClass().getResource("bomb.jpg"));
	ImageIcon player1AndBombIcon = new ImageIcon(this.getClass().getResource("player1AndBomb.jpg"));
	ImageIcon player2AndBombIcon = new ImageIcon(this.getClass().getResource("player2AndBomb.jpg"));
	ImageIcon exitIcon = new ImageIcon(this.getClass().getResource("exit.jpeg"));
	ImageIcon breakableBrickIcon = new ImageIcon(this.getClass().getResource("breakableWall.jpg"));
	ImageIcon unbreakableBrickIcon = new ImageIcon(this.getClass().getResource("unbreakableWall.jpg"));
	//boolean isBombInPlayground = false;
	Timer bombTimer;

	Spiel spiel = null;
	BombermanGameServer gameServer = null;
	BombermanGameClient gameClient = null;
	
	JPanel spielStatusPanel = new JPanel();
	JLabel playerMode = new JLabel("Single Player");
	JLabel gameLevelLabel = new JLabel("");
	/**
	 * 
	 * @param isMultiPlayerMode 
	 */
	public Spiel(boolean isMultiPlayerMode) {
		this.isMultiPlayerMode = isMultiPlayerMode;
	}

	/**
	 * Diese konstruktor initializiert neue Spielfeld mit seinen komponente wie Menu, Layout, JLabel, uw.
	 * In einer JFrame wird eine Panel mit BorderLayout eingefügt. In den Nord Teile der Flowlayout
	 * wird eine Panel mit der Rechts gestellte FlowLayout und 4 JLabel für Spieler Modus und Spielstatus.
	 * Im mittleren Teil von BorderLayout , wird die 20x20 GridLayout eingefügt. Diese Zellen werden auch 
	 * als in 20 x 20 boolean und JLabel gespeichert.zwei Zufallzahlen werden generiert , die zerstörbare 
	 * und die unzerstörbare Wände werden in der zugehörigen Zellen eingefügt.(0,0) (0,1) (1,0) (1,1) (2,0) (2,1) 
	 * mit Absicht freigelassen, damit der 1.Spieler in Position (0,0) beginnen kann.
	 * Falls playermode multiple Player ist, erscheint 2.Spieler in (19,0).
	 * PlaceExitDoor methode wird aufgerufen.
	 * 
	 */
	public void initaliseSpiel(boolean networkPlayer) {
		GameLevel reader = new GameLevel();
		String level = reader.readCurrentLevel();
		if (level != null) {
			GameLevel.currentLevel = Integer.parseInt(level);
			System.out.println("currentLevel: " + GameLevel.currentLevel);
		} else {
			GameLevel.currentLevel = 1;
			System.out.println("currentLevel else: " + GameLevel.currentLevel);
		}
		
		createMenu();
		addKeyListener(this);
		topPanel = new JPanel();
		cellPanel = new JPanel();
		this.add(topPanel);
		topPanel.setLayout(new BorderLayout());
		spielStatusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 25));
		spielStatusPanel.add(new JLabel("Player Mode:"));
		spielStatusPanel.add(playerMode);
		spielStatusPanel.add(new JLabel("Level:"));
		spielStatusPanel.add(gameLevelLabel);
		gameLevelLabel.setText(Integer.toString(GameLevel.currentLevel));
		spielStatusPanel.setVisible(true);
		topPanel.add(spielStatusPanel, BorderLayout.NORTH);
		topPanel.add(new JButton(""), BorderLayout.EAST);
		topPanel.add(new JButton(""), BorderLayout.WEST);
		topPanel.add(new JButton(""),BorderLayout.SOUTH);
		cellPanel.setLayout(new GridLayout(20, 20));
		
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
				cellPanel.add(l);

			}
		}
		// In mittlere Teil von BorderLayout , wird die 20x20 GridLayout eingefügt
		topPanel.add(cellPanel,BorderLayout.CENTER);
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
		isUnbreakableBrickCell[0][0] = false;
		isUnbreakableBrickCell[0][1] = false;
		isUnbreakableBrickCell[1][0] = false;
		isUnbreakableBrickCell[1][1] = false;
		isUnbreakableBrickCell[2][0] = false;
		isUnbreakableBrickCell[2][1] = false;
		
		//Position der Spieler in 0,0 zu beginnen
		cell[0][0].setIcon(player1Icon);
		if (isMultiPlayerMode) {
			// (0,0) (0,1) (1,0) (1,1) (2,0) (2,1) mit Absicht freigelassen 
			cell[19][0].setIcon(null);
			cell[19][1].setIcon(null);
			cell[18][0].setIcon(null);
			cell[18][1].setIcon(null);
			cell[17][0].setIcon(null);
			cell[17][1].setIcon(null);
			isBreakableBrickCell[19][0] = false;
			isBreakableBrickCell[19][1] = false;
			isBreakableBrickCell[18][0] = false;
			isBreakableBrickCell[18][1] = false;
			isBreakableBrickCell[17][0] = false;
			isBreakableBrickCell[17][1] = false;
			isUnbreakableBrickCell[19][0] = false;
			isUnbreakableBrickCell[19][1] = false;
			isUnbreakableBrickCell[18][0] = false;
			isUnbreakableBrickCell[18][1] = false;
			isUnbreakableBrickCell[17][0] = false;
			isUnbreakableBrickCell[17][1] = false;

			//Position der Spieler2 in 19,0 zu beginnen
			cell[19][0].setIcon(player2Icon);
			playerMode.setText("Multiple Player");
		}
		placeExitDoor();
		this.setFocusable(true);
		spiel = this;
		//if (networkPlayer) {
			//send the status to client.
			//gameServer.sendMessage(BombermanSocketMessage.createGameStatusMessage(this));
		//}
	}
	/**
	 * generiert MenuBar ,in dem ein Menu mit Menu Einträge für Spieler Modus(single/multi-Player)
	 * und Spielstatus(restart / Exit) eingefügt wird. 
	 * implementiert auch ActionListener für Menueinträge
	 */
	public void createMenu() {
		// menuBar wird  erzeugt
		JMenuBar menuBar = new JMenuBar();
		// menu wird wird erzeugt und in menuBar eingefügt
		JMenu menu = new JMenu("Bomberman");
		menuBar.add(menu);

		  
		JMenuItem singlePlayer = new JMenuItem("Single Player");
		singlePlayer.addActionListener(this);
		menu.add(singlePlayer);

		JMenuItem multiPlayer = new JMenuItem("Multi Player");
		multiPlayer.addActionListener(this);
		menu.add(multiPlayer);
		
		JMenuItem startServer = new JMenuItem("Start Server");
		startServer.addActionListener(this);
		menu.add(startServer);
		
		JMenuItem connectToServer = new JMenuItem("Connect To Server");
		connectToServer.addActionListener(this);
		menu.add(connectToServer);
		
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
    // startGame methode wird definiert für das Spiel anzufangen
	/**
	 * ruft den Konstruktor auf um das Spiel zu starten.
	 */
	public void startGame() {
		this.initaliseSpiel(false);
	}
	 /** 
     * Hauptprogramm. Objekt erzeugt und 'startGame' methode wird aufgerufen.
     *
     * @param args 
     */
	public static void main(String[] args) {
		GameLevel reader = new GameLevel();
		GameLevel.currentLevel = 1;
		Spiel x = new Spiel(false);
		// Spiel ist mit startGame methode gestartet
		x.startGame();
		reader.writeLevelIntoFile(GameLevel.currentLevel);
	}
	/**
	    * überprüft ob die Spieler Position und AusgangstürPosition gleich sind( Reihen und spaltenweise des Arrays) 
	    * Falls ja, wird die Nachricht "Game Over. Restarting..." mittels JOptionPanel ausgegeben und
	    * neue Objekt wird erzeugt.
	    */
	public void exitDoorAction() {
		System.out.println("ExitDoor X: " + exitPositionX + " ExitDoor Y: " + exitPositionY);
		if ((player1PositionX == exitPositionX && player1PositionY == exitPositionY)) {
			JOptionPane.showMessageDialog(this,"Player1 completed the Level " + GameLevel.currentLevel);
		} else if ((player2PositionX == exitPositionX && player2PositionY == exitPositionY)) {
			JOptionPane.showMessageDialog(this,"Player2 completed the Level " + GameLevel.currentLevel);
		}
		//Falls die Spieler position gleich wie die Ausgangstür Position, wird das Spiel gestartet 
		if ((player1PositionX == exitPositionX && player1PositionY == exitPositionY) 
				|| (player2PositionX == exitPositionX && player2PositionY == exitPositionY)) {
			System.out.println("Player exits via door.");
			this.setVisible(false);
			spiel = new Spiel(this.isMultiPlayerMode);
			//Increment the game level by 1
			GameLevel reader = new GameLevel();
			GameLevel.currentLevel = GameLevel.currentLevel + 1;
			reader.writeLevelIntoFile(GameLevel.currentLevel);
			spiel.initaliseSpiel(false);
		}

	}
	/**
	 *  überprüft ob die zerstörbare und unzerstörbare Wände vorhanden sind, und ob es die Spielfeld Grenze ist.
	 *  Falls nein, bewegt der Spieler die Richtung (nach oben, unten, rechts und links) .Hier wird das aktuelle Hintergrundbild 
	 * als null gesetzt.
	 *  Falls SPACEBAR gedrückt wird, wird der Hintergrund  als playerAndBombIcon geändert und Timer gestartet. 
	 *  
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			System.out.println("left key");
			System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
			if (player1PositionY >= 1 && isCellFreeFromBricks(player1PositionX, player1PositionY - 1)) {
				if (player1AndBombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(bombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(null);
				}
				player1PositionY--;
				System.out.println("New Player Position: " + player1PositionX + "," + player1PositionY);
				if (bombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(player1AndBombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(player1Icon);
				}
			}
			BombermanSocketMessage msg = BombermanSocketMessage.createGameStatusMessage(this);
			gameServer.sendMessage(msg);
					
		} else if ((keyCode == KeyEvent.VK_RIGHT)) {
			System.out.println("right key");
			System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
			if (player1PositionY <= 18 && isCellFreeFromBricks(player1PositionX, player1PositionY + 1)) {
				if (player1AndBombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(bombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(null);
				}
				player1PositionY++;
				System.out.println("New Player Position: " + player1PositionX + "," + player1PositionY);
				if (bombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(player1AndBombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(player1Icon);
				}
			}
			BombermanSocketMessage msg = BombermanSocketMessage.createGameStatusMessage(this);
			gameServer.sendMessage(msg);
		}else if ((keyCode == KeyEvent.VK_UP)) {
			System.out.println("up key");
			System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
			if (player1PositionX >= 1 && isCellFreeFromBricks(player1PositionX - 1, player1PositionY)) {
				if (player1AndBombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(bombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(null);
				}
				player1PositionX--;
				System.out.println("New Player Position: " + player1PositionX + "," + player1PositionY);
				if (bombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(player1AndBombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(player1Icon);
				}
			}
			BombermanSocketMessage msg = BombermanSocketMessage.createGameStatusMessage(this);
			gameServer.sendMessage(msg);
		}else if ((keyCode == KeyEvent.VK_DOWN)) {
			System.out.println("down");
			System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
			if (player1PositionX <= 18 && isCellFreeFromBricks(player1PositionX + 1, player1PositionY)) {
				if (player1AndBombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(bombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(null);
				}
				player1PositionX++;
				System.out.println("New Player Position: " + player1PositionX + "," + player1PositionY);
				if (bombIcon.equals(cell[player1PositionX][player1PositionY].getIcon())) {
					cell[player1PositionX][player1PositionY].setIcon(player1AndBombIcon);
				} else {
					cell[player1PositionX][player1PositionY].setIcon(player1Icon);
				}
			}
			BombermanSocketMessage msg = BombermanSocketMessage.createGameStatusMessage(this);
			gameServer.sendMessage(msg);
		} else if (keyCode == KeyEvent.VK_SPACE) {
			System.out.println("space bar");
			System.out.println("Bomb Position: " + player1PositionX + "," + player1PositionY);
			cell[player1PositionX][player1PositionY].setIcon(player1AndBombIcon);
			BombPosition bombPosition = new BombPosition();
			bombPosition.setPositionX(player1PositionX);
			bombPosition.setPositionY(player1PositionY);
			bombList.add(bombPosition);
			lastBombPlacedTime = new java.util.Date().getTime();
			//if (!isBombTimerStarted) {
				//isBombTimerStarted = true;
				bombTimer = new Timer(3000, this);
				bombTimer.restart();
			//}
		}
		if (keyCode == KeyEvent.VK_A) {
			System.out.println("left key");
			System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
			if (player2PositionY >= 1 && isCellFreeFromBricks(player2PositionX, player2PositionY - 1)) {
				if (player2AndBombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(bombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(null);
				}
				player2PositionY--;
				System.out.println("New Player Position: " + player2PositionX + "," + player2PositionY);
				if (bombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(player2AndBombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(player2Icon);
				}
			}
		} else if (keyCode == KeyEvent.VK_D) {
			System.out.println("right key");
			System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
			if (player2PositionY <= 18 && isCellFreeFromBricks(player2PositionX, player2PositionY + 1)) {
				if (player2AndBombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(bombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(null);
				}
				player2PositionY++;
				System.out.println("New Player Position: " + player2PositionX + "," + player2PositionY);
				if (bombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(player2AndBombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(player2Icon);
				}
			}

		}else if ((keyCode == KeyEvent.VK_W)) {
			System.out.println("up key");
			System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
			if (player2PositionX >= 1 && isCellFreeFromBricks(player2PositionX - 1, player2PositionY)) {
				if (player2AndBombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(bombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(null);
				}
				player2PositionX--;
				System.out.println("New Player Position: " + player2PositionX + "," + player2PositionY);
				if (bombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(player2AndBombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(player2Icon);
				}
			}

		}else if ((keyCode == KeyEvent.VK_X)) {
			System.out.println("down");
			System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
			if (player2PositionX <= 18 && isCellFreeFromBricks(player2PositionX + 1, player2PositionY)) {
				if (player2AndBombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(bombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(null);
				}
				player2PositionX++;
				System.out.println("New Player Position: " + player2PositionX + "," + player2PositionY);
				if (bombIcon.equals(cell[player2PositionX][player2PositionY].getIcon())) {
					cell[player2PositionX][player2PositionY].setIcon(player2AndBombIcon);
				} else {
					cell[player2PositionX][player2PositionY].setIcon(player2Icon);
				}
			}
		} else if (keyCode == KeyEvent.VK_S) {
			System.out.println("Enter Key");
			System.out.println("Bomb Position: " + player2PositionX + "," + player2PositionY);
			cell[player2PositionX][player2PositionY].setIcon(player2AndBombIcon);
			BombPosition bombPosition = new BombPosition();
			bombPosition.setPositionX(player2PositionX);
			bombPosition.setPositionY(player2PositionY);
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
     * Default ist als Single Player eingesetzt. Wenn man Multiplayer klickt, erscheint dann 2.Spieler in 
     * [19][0]...
     * Wenn man Restart klickt, wird ein neue Objekt erzeugt und initialiseSpiel() methode aufgerufen.
     * Wenn man Exit klickt, wird das Spiel beendet...
     */
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Single Player")) {
			JOptionPane.showMessageDialog(this,"Restarting the Game in Singleplayer mode...");
			this.setVisible(false);
			spiel = new Spiel(false);
			//Resetting the game level since player died.
			GameLevel reader = new GameLevel();
			GameLevel.currentLevel = 1;
			reader.writeLevelIntoFile(GameLevel.currentLevel);
			spiel.initaliseSpiel(false);
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Multi Player")) {
			restartGameInMultiPlayerMode();
		}else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Start Server")) {
			gameServer = new BombermanGameServer(spiel);
			gameServer.start();
		}
		else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Connect To Server")) {
			//reinitialise the board to multiplayer mode if it is not.
			if (!this.isMultiPlayerMode) {
				restartGameInMultiPlayerMode();
			}
			gameClient = new BombermanGameClient(spiel);
			gameClient.start();
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Restart")) {
			this.setVisible(false);
			spiel = new Spiel(this.isMultiPlayerMode);
			//Reset the game level by 1
			GameLevel reader = new GameLevel();
			GameLevel.currentLevel = 1;
			reader.writeLevelIntoFile(GameLevel.currentLevel);
			spiel.initaliseSpiel(false);
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Exit")) {
			System.exit(0);
		} else {
			System.out.println(" Inside action Performed in the Timer");
			if (bombList != null && bombList.size() <= 1 && new Date().getTime() - lastBombPlacedTime < 3000) {
				bombTimer.restart();
			} else if (bombList != null && bombList.size() > 0) {
				BombPosition bombPosition = (BombPosition) bombList.get(0);
				int bombPositionX = bombPosition.getPositionX();
				int bombPositionY = bombPosition.getPositionY();
				explodeBomb(bombPositionX, bombPositionY);
			}
			isBombTimerStarted = false;
			//bombList = new ArrayList();
			//bombPositionX = 0;
			//bombPositionY = 0;
			//bombTimer.stop();
		}
	}
	/**
	    * Hier wird die 9 Umgebung Zellen (Reihen und Spaltenweise)
	    * von aktuelle Position entleert, mit der Methode ClearCellIfBreakable(), die nur die zerstörbare Wände entleert.   
	    */
	public void explodeBomb(int bombPositionX, int bombPositionY) {
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
		//bombTimer.restart();
	}

	/**
	 * Diese Methode startet das Spiel erneut, wenn Spieler in Bomb position trifft;
	 * entleert die Zelle, wenn die ein zerstörbares Wand Icon hat.
	 * @param x : Reihe der Array
	 * @param y : Spalten der Array
	 */
	public void clearCellIfBreakable(int x, int y) {
		restartIfPlayerOnBombField(x, y);
		//wenn es ein Ausgangstür position ist
		if (x == exitPositionX && y == exitPositionY) {
			if (exitIcon.equals(cell[x][y].getIcon())) {
				return;
			} else if(bombIcon.equals(cell[x][y].getIcon())) {
				explodeBomb(x, y);
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
		if (player1Icon.equals(cell[x][y].getIcon()) || player1AndBombIcon.equals(cell[x][y].getIcon())) {
			JOptionPane.showMessageDialog(this,"Player Dead. Game Over. Restarting...");
			this.setVisible(false);
			spiel = new Spiel(this.isMultiPlayerMode);
			//Resetting the game level since player died.
			GameLevel reader = new GameLevel();
			GameLevel.currentLevel = 1;
			reader.writeLevelIntoFile(GameLevel.currentLevel);
			spiel.initaliseSpiel(false);
		}
	}

	/**
	 * Diese Methode überprüft, ob der boolean isBreakableBrickCell und isUnbreakableBrickCell 
	 * in x,y false ist 
	 * @param x Reihe der Booleanarray
	 * @param y Spalten der Booleanarray
	 * @return true wenn zellen frei von Mauer sind,else False
	 */
	public boolean isCellFreeFromBricks(int x, int y) {
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
	
	public void refresh(BombermanSocketMessage msg) {
		this.isBreakableBrickCell = msg.isBreakableBrickCell;
		this.isUnbreakableBrickCell = msg.isUnbreakableBrickCell;
		this.bombList = msg.bombList;
		this.player1PositionX  = msg.player1PositionX;
		this.player1PositionY = msg.player1PositionY;
		this.player2PositionX = msg.player2PositionX;
		this.player2PositionY = msg.player2PositionY;
		this.exitPositionX = msg.exitPositionX;
		this.exitPositionY = msg.exitPositionY;
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				System.out.println(i + "," + j + " " + this.cell[i][j].getIcon());
				if (msg.cell[i][j].getIcon() == null) {
					cell[i][j].setIcon(null);
				} else if (msg.cell[i][j].getIcon().toString().contains("player1.png")) {
					cell[i][j].setIcon(player1Icon);
				} else if (msg.cell[i][j].getIcon().toString().contains("player2.png")) {
					cell[i][j].setIcon(player2Icon);
				}else if (msg.cell[i][j].getIcon().toString().contains("player1AndBomb.jpg")) {
					cell[i][j].setIcon(player1AndBombIcon);
				}else if (msg.cell[i][j].getIcon().toString().contains("player2AndBomb.jpg")) {
					cell[i][j].setIcon(player2AndBombIcon);
				} else if (msg.cell[i][j].getIcon().toString().contains("bomb.jpg")) {
					cell[i][j].setIcon(bombIcon);
				}
				else if (msg.cell[i][j].getIcon().toString().contains("exit.jpeg")) {
					cell[i][j].setIcon(exitIcon);
				}else if (msg.cell[i][j].getIcon().toString().contains("unbreakableWall.jpg")) {
					cell[i][j].setIcon(unbreakableBrickIcon);
				} else if (msg.cell[i][j].getIcon().toString().contains("breakableWall.jpg")) {
					cell[i][j].setIcon(breakableBrickIcon);
				}else {
					cell[i][j].setIcon(null);
				}
			}
		}
		
		//this.repaint();
        //this.pack();
        //this.setVisible(true);
	}
	
	public void restartGameInMultiPlayerMode() {
		JOptionPane.showMessageDialog(this,"Restarting the Game in Multplayer mode...");
		this.setVisible(false);
		spiel = new Spiel(true);
		//Resetting the game level since player died.
		GameLevel reader = new GameLevel();
		GameLevel.currentLevel = 1;
		reader.writeLevelIntoFile(GameLevel.currentLevel);
		spiel.initaliseSpiel(false);
	}
}

