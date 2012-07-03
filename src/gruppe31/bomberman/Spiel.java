package gruppe31.bomberman;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;package gruppe31.bomberman;

public class BombPosition {
	package gruppe31.bomberman;
	ackage gruppe31.bomberman;

	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;

	import javax.xml.parsers.DocumentBuilder;
	import javax.xml.parsers.DocumentBuilderFactory;

	import org.w3c.dom.Document;
	import org.w3c.dom.Element;
	import org.w3c.dom.Node;
	import org.w3c.dom.NodeList;
	/**
	 * 
	 * @author gruppe 31 - bomberman
	 * Diese Klasse hat methode, die die aktuelle Status der Spiel und Punkten vom xml Datei einliest 
	 * und in xml Datei einschreibt.
	 *
	 */
	public class GameLevel {

		public static int currentLevel = 0;
		/**
		 * liest die aktuelle Status der Spiel von GameLevel.xml ein
		 * @return the Spiel Status
		 */
		public String readCurrentLevel() {

			try {
				File file = new File("GameLevel.xml");
				if (file == null) {
					return null;
				}
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				NodeList nodeLst = doc.getElementsByTagName("bomberman");
				
				Node fstNode = nodeLst.item(0);

					if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

						Element fstElmnt = (Element) fstNode;
						NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("level");
						Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
						NodeList fstNm = fstNmElmnt.getChildNodes();
						return ((Node) fstNm.item(0)).getNodeValue();
						//System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public String readPlayer1Score() {

			try {
				File file = new File("GameLevel.xml");
				if (file == null) {
					return null;
				}
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				NodeList nodeLst = doc.getElementsByTagName("bomberman");
				
				Node fstNode = nodeLst.item(0);

					if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

						Element fstElmnt = (Element) fstNode;
						NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("player1score");
						Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
						NodeList fstNm = fstNmElmnt.getChildNodes();
						return ((Node) fstNm.item(0)).getNodeValue();
						//System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public String readPlayer2Score() {

			try {
				File file = new File("GameLevel.xml");
				if (file == null) {
					return null;
				}
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				NodeList nodeLst = doc.getElementsByTagName("bomberman");
				
				Node fstNode = nodeLst.item(0);

					if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

						Element fstElmnt = (Element) fstNode;
						NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("player1score");
						Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
						NodeList fstNm = fstNmElmnt.getChildNodes();
						return ((Node) fstNm.item(0)).getNodeValue();
						//System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * schriebt die aktuelle Status der Spiel und Punkten in GameLevel.xml. 
		 * @param Status
		 */
		public void writeLevelIntoFile(int level, int player1score, int player2score) {
			File file = new File("GameLevel.xml");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer xmlBuffer = new StringBuffer();
			xmlBuffer.append("<?xml version=\"1.0\"?><bomberman><level>");
			xmlBuffer.append(Integer.toString(level));
			xmlBuffer.append("</level>");
			xmlBuffer.append("<player1score>");
			xmlBuffer.append(Integer.toString(player1score));
			xmlBuffer.append("</player1score><player2score>");
			xmlBuffer.append(Integer.toString(player2score));
			xmlBuffer.append("</player2score></bomberman>");
			
			try {
				fos.write(xmlBuffer.toString().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public class BombPosition {

		private int positionX;
		private int positionY;
		
		
		public int getPositionX() {
			return positionX;
		}
		public void setPositionX(int positionX) {
			this.positionX = positionX;
		}
		public int getPositionY() {
			return positionY;
		}
		public void setPositionY(int positionY) {
			this.positionY = positionY;
		}
	}

	private int positionX;
	private int positionY;
	
	
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
}

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;package gruppe31.bomberman;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class SpielApplet extends JApplet {

	public void init() {
		try {

			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {

					GameLevel reader = new GameLevel();
					GameLevel.currentLevel = 1;
					Spiel x = new Spiel(false);
					// Spiel ist mit startGame methode gestartet
					x.startGame();
					reader.writeLevelIntoFile(GameLevel.currentLevel, x.player1Score, x.player2Score);

				}
			});

		} catch (Exception ex) {

		}
	}
}

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
 * Diese Klasse Spiel erweitert JFrame und implementiert Interface KeyListener, ActionListener,Serializable
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
	ImageIcon giftBoxIcon = new ImageIcon(this.getClass().getResource("gift_boxes.jpg"));
	//boolean isBombInPlayground = false;
	transient Timer bombTimer;

	Spiel spiel = null;
	transient BombermanGameServer gameServer = null;
	transient BombermanGameClient gameClient = null;
	
	private JPanel spielStatusPanel = new JPanel();
	private JLabel networkMode = new JLabel(""); 
	private JLabel scoreBoard1 = new JLabel("");
	private JLabel scoreBoard2 = new JLabel("");
	private JLabel playerMode = new JLabel("Single Player");
	private JLabel gameLevelLabel = new JLabel("");
	transient AudioEffects audio;
	private boolean soundOn;
	int player1Score;
	int player2Score;
	
	private boolean isServer;
	private boolean isClient;
	
	private boolean isPlayer1 = true;
	private boolean isPlayer2 = true;
	JMenuBar menuBar;
	/**
	 * Konstruktor
	 * @param isMultiPlayerMode 
	 */
	public Spiel(boolean isMultiPlayerMode) {
		this.isMultiPlayerMode = isMultiPlayerMode;
	}

	/**
	 * In diese methode wird neue GameLevel Objekt erzeugt und die aktuelle Game Level wird mittels readCurrentLevel
	 * gelesen und in String gepeichert.
	 * Diese  String wird als Integer umgewandelt und wird für initialisierung der Variable currentLevel benutzt. 
	 * CreateMenu methode wird aufgerufen. JPanel mit BorderLayout,rechts gelegte FlowLayout mit verschiedene JLabel
	 * 20 x 20 GridLayout in der mittlere Teil der BorderLayout werden eingefügt
	 * zwei Zufallzahlen werden generiert , die zerstörbare,die unzerstörbare Wände und Geschenke werden eingefügt.
	 * (0,0) (0,1) (1,0) (1,1) (2,0) (2,1) mit Absicht freigelassen damit der 1.Spieler in Position (0,0) beginnen kann.
	 * Falls playermode multiple Player ist, erscheint 2.Spieler in (19,0). und (19,0) (19,1) (18,0) (18,1) (17,0) (17,1) 
	 * mit Absicht freigelassen für 2. Spieler. 
	 * PlaceExitDoor methode wird aufgerufen. Soundeffekts werden angepasst.
	 * 
	 * @param networkPlayer
	 */
	public void initaliseSpiel(boolean networkPlayer) {
		//neue GameLevel Objekt wird erzeugt und die aktuelle Game Level wird mittels readCurrentLevel gelesen und in String gepeichert.
	    //Diese  String wird als Integer umgewandelt und wird für initialisierung der Variable currentLevel benutzt.
		
				
		createMenu();
		addKeyListener(this);
		topPanel = new JPanel();
		cellPanel = new JPanel();
		this.add(topPanel);
		topPanel.setLayout(new BorderLayout());
		spielStatusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 25));
		spielStatusPanel.add(new JLabel("Spieler:"));
		spielStatusPanel.add(networkMode);
		spielStatusPanel.add(new JLabel("Score (Player1):"));
		spielStatusPanel.add(scoreBoard1);
		spielStatusPanel.add(new JLabel("Score (Player2):"));
		spielStatusPanel.add(scoreBoard2);
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
		
		// zwei Zufallzahlen werden generiert , die zerstörbare,die unzerstörbare Wände und Geschenke werden eingefügt

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
				else if ((j == random2 + 2) || (i == random1 + 2) && !isUnbreakableBrickCell[i][j] && !isBreakableBrickCell[i][j] ){
					l.setIcon(giftBoxIcon);	
				}
				cellPanel.add(l);

			}
		}
		// In mittlere Teil von BorderLayout , wird die 20x20 GridLayout eingefügt
		topPanel.add(cellPanel,BorderLayout.CENTER);
		// Frame Eigenschaften
		topPanel.setSize(200, 200);
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
			// Falls Player mode ist Multiplayer mode , dann werden (19,0) (19,1) (18,0) (18,1) (17,0) (17,1) 
		    //mit Absicht freigelassen für 2. Spieler
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

			//Position der Spieler2 in 19,0 zu beginnen und Player Mode ändern
			cell[19][0].setIcon(player2Icon);
			playerMode.setText("Multiple Player");
		}
		placeExitDoor();
		this.setFocusable(true);
		spiel = this;
		audio = new AudioEffects();
		if (audio.isAudioOn()) {
			soundOn = true;
		}
	}
	/**
	 * generiert MenuBar ,in dem ein Menu mit verschiedene Menu Einträge für Spieler Modus(single/multi-Player)
	 * Spielstatus(restart / Exit), netwerk mode(Search Player 2 / Connect to Player 1),und Speichern des Spiels(save Game)
	 * Laden des Spiels(Open saved game) eingefügt 
	 * andere Menu für Audio mit Menueinträge (On/Off) werden auch eingefügt 
	 * ActionListener für Menueinträge werden auch implementiert.
	 */
	public void createMenu() {
		// menuBar wird  erzeugt
		menuBar = new JMenuBar();
		// menu wird wird erzeugt und in menuBar eingefügt
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		  
		JMenuItem singlePlayer = new JMenuItem("Single Player");
		singlePlayer.addActionListener(this);
		menu.add(singlePlayer);

		JMenuItem multiPlayer = new JMenuItem("Multi Player");
		multiPlayer.addActionListener(this);
		menu.add(multiPlayer);
		
		JMenuItem startServer = new JMenuItem("Search Player 2");
		startServer.addActionListener(this);
		menu.add(startServer);
		
		JMenuItem connectToServer = new JMenuItem("Connect To Player 1");
		connectToServer.addActionListener(this);
		menu.add(connectToServer);
		
		// menuitem wird erzeugt mit ActionListener in menu eingefügt  
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(this);
		menu.add(restart);

		JMenuItem saveGame = new JMenuItem("Save Game");
		saveGame.addActionListener(this);
		menu.add(saveGame);
		
		JMenuItem openGame = new JMenuItem("Open saved Game");
		openGame.addActionListener(this);
		menu.add(openGame);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);
		// Menü für Audio wird erzeugt und in MenuBar eingefügt.
		JMenu audioMenu = new JMenu("Sound");
		menuBar.add(audioMenu);
		JMenuItem audioOn = new JMenuItem("on");
		audioOn.addActionListener(this);
		audioMenu.add(audioOn);
		JMenuItem audioOff = new JMenuItem("off");
		audioOff.addActionListener(this);
		audioMenu.add(audioOff);
		
		// menubar in frame eingesetzt
		this.setJMenuBar(menuBar);
	}
	/**
	 * generiert eine Zufallnummer und setzt die Ausgangstür innerhalb 15. - 19.Reihe und Spalten unten ein 
	 * (BreakableBrickIcon) zerstörbare Wand.
	 */
	public void placeExitDoor() {
		
		Random generator = new Random();
		int row = 0;
		int column = 0;
		row = generator.nextInt(20);
		while (row < 7 || row > 12) {
			row = generator.nextInt(20);
		}
		column = generator.nextInt(20);
		while (column < 15) {
			column = generator.nextInt(20);
		}
		exitPositionX = row;
		exitPositionY = column;
		System.out.println("Exit Door position: " + exitPositionX + "," + exitPositionY);
		cell[row][column].setIcon(breakableBrickIcon);
		isBreakableBrickCell[row][column] = true;
	}
   
	/**
	 * ruft den Konstruktor auf um das Spiel zu starten.
	 */
	public void startGame() {
		 // startGame methode wird definiert für das Spiel anzufangen
		this.initaliseSpiel(false);
	}
	
	 /** 
     * Hauptprogramm. Spiel Objekt und GameLevel Objekt werden erzeugt.
     * 'startGame', 'writeLevelIntoFile' methode werden aufgerufen.
     *
     * @param args 
     */
	public static void main(String[] args) {
		GameLevel reader = new GameLevel();
		GameLevel.currentLevel = 1;
		Spiel x = new Spiel(false);
		// Spiel ist mit startGame methode gestartet
		x.startGame();
		reader.writeLevelIntoFile(GameLevel.currentLevel, x.player1Score, x.player2Score);
	}
	
	
	/**
	    * überprüft ob die Spieler Position und AusgangstürPosition gleich sind( Reihen und spaltenweise des Arrays) 
	    * Falls ja, wird die Nachricht "Player 1/2 completed the Level x " mittels JOptionPanel ausgegeben und
	    * neue Objekt wird erzeugt.x = aktuelle Level
	    * und die Level des Spiels ist 1 inkrementiert und xml gespeichert.
	    */
	public void exitDoorAction() {
		System.out.println("ExitDoor X: " + exitPositionX + " ExitDoor Y: " + exitPositionY);
		if ((player1PositionX == exitPositionX && player1PositionY == exitPositionY)) {
			JOptionPane.showMessageDialog(this,"Player1 completed the Level " + GameLevel.currentLevel);
			this.player1Score = this.player1Score + 500;
		} else if ((player2PositionX == exitPositionX && player2PositionY == exitPositionY)) {
			JOptionPane.showMessageDialog(this,"Player2 completed the Level " + GameLevel.currentLevel);
			this.player2Score = this.player2Score + 500;
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
			reader.writeLevelIntoFile(GameLevel.currentLevel, this.player1Score, this.player2Score);
			spiel.initaliseSpiel(false);
			
			String level = reader.readCurrentLevel();
			if (level != null) {
				GameLevel.currentLevel = Integer.parseInt(level);
				System.out.println("currentLevel: " + GameLevel.currentLevel);
			} else {
				GameLevel.currentLevel = 1;
				System.out.println("currentLevel else: " + GameLevel.currentLevel);
			}
			String player1Score = reader.readPlayer1Score();
			if (player1Score != null) {
				spiel.player1Score = Integer.parseInt(player1Score);
			}
			spiel.scoreBoard1.setText(Integer.toString(this.player1Score));
			String player2Score = reader.readPlayer2Score();
			if (player2Score != null) {
				spiel.player2Score = Integer.parseInt(player2Score);
			}
			spiel.scoreBoard2.setText(Integer.toString(this.player2Score));

		}

	}
	/**
	 *  überprüft ob die zerstörbare und unzerstörbare Wände vorhanden sind, und ob es die Spielfeld Grenze ist.
	 *  Falls nein, kann der 1. Spieler/2.Speiler die Richtung (nach oben, unten, rechts und links) durch Tastatur Steuerung
	 *  bewegt werden.Hier wird das aktuelle Hintergrundbild dann als null gesetzt.
	 *  Falls SPACEBAR (1.Spieler)/S (2.Spieler) gedrückt wird, wird der Hintergrund  als playerAndBombIcon geändert und Timer gestartet. 
	 *  Falls die spieler Icon mit PlayerAndBombIcon während der Bewegung trifft, dann wird das Icon als BombIcon geändert.
	 *  Falls Serverprogram für Spieler 1/ Clientprogram für Spieler 2 aktiviert sind( gameServer / ClientServer nicht null), 
	 *  dann werden die aktuelle Spielfeld status mit sendMessage methode geschickt.
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		// Player 1
		if (isPlayer1) {
			if (keyCode == KeyEvent.VK_LEFT) {
				System.out.println("left key");
				System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
				if (player1PositionY >= 1 && isCellFreeFromBricks(player1PositionX, player1PositionY - 1)) {
					addScorePlayer1(player1PositionX, player1PositionY - 1);
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
					if (gameServer != null) {
						gameServer.sendMessage(this);
					}
				}
			} else if ((keyCode == KeyEvent.VK_RIGHT)) {
				System.out.println("right key");
				System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
				if (player1PositionY <= 18 && isCellFreeFromBricks(player1PositionX, player1PositionY + 1)) {
					addScorePlayer1(player1PositionX, player1PositionY + 1);
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
					if (gameServer != null) {
						gameServer.sendMessage(this);
					}
				}
			}else if ((keyCode == KeyEvent.VK_UP)) {
				System.out.println("up key");
				System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
				if (player1PositionX >= 1 && isCellFreeFromBricks(player1PositionX - 1, player1PositionY)) {
					addScorePlayer1(player1PositionX - 1, player1PositionY);
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
					if (gameServer != null) {
						gameServer.sendMessage(this);
					}
				}
			}else if ((keyCode == KeyEvent.VK_DOWN)) {
				System.out.println("down");
				System.out.println("Current Player Position: " + player1PositionX + "," + player1PositionY);
				if (player1PositionX <= 18 && isCellFreeFromBricks(player1PositionX + 1, player1PositionY)) {
					addScorePlayer1(player1PositionX + 1, player1PositionY);
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
					if (gameServer != null) {
						gameServer.sendMessage(this);
					}
				}
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
				if (gameServer != null) {
					gameServer.sendMessage(this);
				}
			}
		}
		if (isPlayer2) {
			// Player 2
			if (keyCode == KeyEvent.VK_A) {
				System.out.println("left key");
				System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
				if (player2PositionY >= 1 && isCellFreeFromBricks(player2PositionX, player2PositionY - 1)) {
					addScorePlayer2(player2PositionX, player2PositionY - 1);
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
					if (gameClient != null) {
						gameClient.sendMessage(this);
					}
				}
			} else if (keyCode == KeyEvent.VK_D) {
				System.out.println("right key");
				System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
				if (player2PositionY <= 18 && isCellFreeFromBricks(player2PositionX, player2PositionY + 1)) {
					addScorePlayer2(player2PositionX, player2PositionY + 1);
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
					if (gameClient != null) {
						gameClient.sendMessage(this);
					}
				}

			}else if ((keyCode == KeyEvent.VK_W)) {
				System.out.println("up key");
				System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
				if (player2PositionX >= 1 && isCellFreeFromBricks(player2PositionX - 1, player2PositionY)) {
					addScorePlayer2(player2PositionX - 1, player2PositionY);
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
					if (gameClient != null) {
						gameClient.sendMessage(this);
					}
				}

			}else if ((keyCode == KeyEvent.VK_X)) {
				System.out.println("down");
				System.out.println("Current Player2 Position: " + player2PositionX + "," + player2PositionY);
				if (player2PositionX <= 18 && isCellFreeFromBricks(player2PositionX + 1, player2PositionY)) {
					addScorePlayer2(player2PositionX + 1, player2PositionY);
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
					if (gameClient != null) {
						gameClient.sendMessage(this);
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
				if (gameClient != null) {
					gameClient.sendMessage(this);
				}
			}
		}
		exitDoorAction();
		
	}
	/**
     * Default ist als Single Player eingesetzt. Wenn man Multiplayer klickt, erscheint dann 2.Spieler in 
     * [19][0].
     * Wenn man Search Player 2 klickt, wird auf 2.spieler gewartet.
     * Wenn man Connect to Player 1 klickt, wird die Spiel mode als Multiplayer geändert und mit 1.Spieler verbunden.
     * Wenn man Save Game klickt, wird das Spiel gespeichert.
     * Wenn man open saved game klickt, wird das gespeicherte Spiel geöffnet.
     * Wenn man Restart klickt, wird ein neue Objekt erzeugt und initialiseSpiel() methode aufgerufen.
     * Wenn man Exit klickt, wird das Spiel beendet.
     * Wenn man On klickt, wird die Hintergrundmusik gepielt.
     * Wenn man Off klickt, wird die Hintergrundmusik gestoppt.
     */
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Single Player")) {
			JOptionPane.showMessageDialog(this,"Restarting the Game in Singleplayer mode...");
			this.setVisible(false);
			spiel = new Spiel(false);
			//Spiel status wieder anfangen, weil die Spieler tot ist.
			GameLevel reader = new GameLevel();
			GameLevel.currentLevel = 1;
			reader.writeLevelIntoFile(GameLevel.currentLevel, this.player1Score, this.player2Score);
			spiel.initaliseSpiel(false);
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Multi Player")) {
			restartGameInMultiPlayerMode(false);
		}else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Search Player 2")) {
			isServer = true;
			isPlayer1 = true;
			isPlayer2 = false;
			networkMode.setText("Player 1");
			deactivateStartServerMenuItem();
			gameServer = new BombermanGameServer(this);
			gameServer.start();
		}
		else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Connect To Player 1")) {
			//Spielfeld wieder initialisieren wenn das Spieler mode nicht multiplayer mode ist.
			if (!this.isMultiPlayerMode) {
				restartGameInMultiPlayerMode(true);
			} else {
				//Client initialisiert nur wann die Mode multiplayer mode ist, sonst wird die Initisialiserung der 
				//gameClient durch restartGameInMultiPlayerMode() gekummert
				
				isClient = true;
				isPlayer2 = true;
				isPlayer1 = false;
				networkMode.setText("Player 2");
				gameClient = new BombermanGameClient(this);
				gameClient.start();
			}
			
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Restart")) {
			this.setVisible(false);
			closeSockets();
			this.gameServer = null;
			spiel = new Spiel(this.isMultiPlayerMode);
			//Reset the game level by 1
			GameLevel reader = new GameLevel();
			GameLevel.currentLevel = 1;
			reader.writeLevelIntoFile(GameLevel.currentLevel, this.player1Score, this.player2Score);
			spiel.initaliseSpiel(false);
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Save Game")) {
			try {
				JFileChooser fileChooser = new JFileChooser();
				int option = fileChooser.showSaveDialog(this);
				if (option == JFileChooser.APPROVE_OPTION) {
					String fileName = fileChooser.getCurrentDirectory().getAbsolutePath() + "/" + fileChooser.getSelectedFile().getName(); 
					System.out.println("Saving the game in file: " + fileName);
					FileOutputStream fos = new FileOutputStream(fileName);
					ObjectOutputStream out = new ObjectOutputStream(fos);
					out.writeObject(this);
				}
			} catch(IOException exception) {
				System.out.println("IOException while saving the game: ");
				exception.printStackTrace();
			}
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Open saved Game")) {
			try {
				JFileChooser fileChooser = new JFileChooser();
				int option = fileChooser.showOpenDialog(this);
				if (option == JFileChooser.APPROVE_OPTION) {
					String fileName = fileChooser.getCurrentDirectory().getAbsolutePath() + "/" + fileChooser.getSelectedFile().getName();
					System.out.println("Loading the game from file: " + fileName);
					FileInputStream fis = new FileInputStream(fileName);
					ObjectInputStream in = new ObjectInputStream(fis);
					this.spiel = (Spiel) in.readObject();
					refresh();
					
				}
			} catch(IOException exception) {
				System.out.println("IOException while opening the game: ");
				exception.printStackTrace();
			} catch (ClassNotFoundException exception) {
				System.out.println("ClassNotException while opening the game: ");
				exception.printStackTrace();
			}
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("Exit")) {
			System.exit(0);
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("on")) {
			//Audio On
			soundOn = true;
			audio.playBgm();
		} else if (e.getActionCommand() != null && e.getActionCommand().equalsIgnoreCase("off")) {
			soundOn = false;
			if (audio != null) {
				audio.pauseBgm();
			}
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
	    * Falls die sound angeschaltet ist, wird Bomb explosion Geräusch addiert, die nur während der Bomben Anlegung spielt.  
	    */
	public void explodeBomb(int bombPositionX, int bombPositionY) {
		if (soundOn && audio != null) {
			//audio.pauseBgm();
			audio.playBombExplosion();
			//audio.playBgm();
		}
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
			//wieder das game level von 1 anfangen, weil spieler tot ist.
			GameLevel reader = new GameLevel();
			GameLevel.currentLevel = 1;
			reader.writeLevelIntoFile(GameLevel.currentLevel, this.player1Score, this.player2Score);
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
	/**
	 * Hier wird das Spiel neue gestartet und die Game Level wieder von 1 angefangen
	 */
	public void restartGameInMultiPlayerMode(boolean startGameClient) {
		JOptionPane.showMessageDialog(this,"Restarting the Game in Multplayer mode...");
		this.setVisible(false);
		spiel = new Spiel(true);
		//wieder das game level von 1 anfangen, weil spieler tot ist.
		GameLevel reader = new GameLevel();
		GameLevel.currentLevel = 1;
		reader.writeLevelIntoFile(GameLevel.currentLevel, this.player1Score, this.player2Score);
		spiel.initaliseSpiel(false);
		if (gameClient != null) {
			spiel.gameClient = gameClient;
			gameClient.spiel = spiel;
			gameClient.sendMessage(spiel);
		} else {
			if (startGameClient) {
				gameClient = new BombermanGameClient(spiel);
				spiel.gameClient = gameClient;
				gameClient.start();
			}
			//gameClient.sendMessage(spiel);
		}
		spiel.isClient = true;
		spiel.isPlayer2 = true;
		spiel.isPlayer1 = false;
		spiel.networkMode.setText("Player 2");
	}
	/**
	 * 
	 * @param msg
	 */
	public void refresh(Spiel msg) {
		this.spiel = msg;
		refresh();
	}
	/**
	 * Alle variablen sind neue initializiert und Wenn eine Icon in eine Zelle vorhanden ist, ist wieder diese Icon 
	 * bei Spielfeld eingefügt.
	 */
	public void refresh() {
		System.out.println("Refresh called." + new Date().toString());
		this.isBreakableBrickCell = this.spiel.isBreakableBrickCell;
		this.isUnbreakableBrickCell = this.spiel.isUnbreakableBrickCell;
		this.bombList = this.spiel.bombList;
		this.player1PositionX  = this.spiel.player1PositionX;
		this.player1PositionY = this.spiel.player1PositionY;
		this.player2PositionX = this.spiel.player2PositionX;
		this.player2PositionY = this.spiel.player2PositionY;
		this.exitPositionX = this.spiel.exitPositionX;
		this.exitPositionY = this.spiel.exitPositionY;
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				//System.out.println(i + "," + j + " " + this.cell[i][j].getIcon());
				if (this.spiel.cell[i][j].getIcon() == null) {
					cell[i][j].setIcon(null);
				} else if (this.spiel.cell[i][j].getIcon().toString().contains("player1.png")) {
					cell[i][j].setIcon(player1Icon);
				} else if (this.spiel.cell[i][j].getIcon().toString().contains("player2.png")) {
					cell[i][j].setIcon(player2Icon);
				}else if (this.spiel.cell[i][j].getIcon().toString().contains("player1AndBomb.jpg")) {
					cell[i][j].setIcon(player1AndBombIcon);
				}else if (this.spiel.cell[i][j].getIcon().toString().contains("player2AndBomb.jpg")) {
					cell[i][j].setIcon(player2AndBombIcon);
				} else if (this.spiel.cell[i][j].getIcon().toString().contains("bomb.jpg")) {
					cell[i][j].setIcon(bombIcon);
				}
				else if (this.spiel.cell[i][j].getIcon().toString().contains("exit.jpeg")) {
					cell[i][j].setIcon(exitIcon);
				}else if (this.spiel.cell[i][j].getIcon().toString().contains("unbreakableWall.jpg")) {
					cell[i][j].setIcon(unbreakableBrickIcon);
				} else if (this.spiel.cell[i][j].getIcon().toString().contains("breakableWall.jpg")) {
					cell[i][j].setIcon(breakableBrickIcon);
				}else if (this.spiel.cell[i][j].getIcon().toString().contains("gift_boxes.jpg")){
							cell[i][j].setIcon(giftBoxIcon);	
				}else {
					cell[i][j].setIcon(null);
				}
			}
		}
		//this.spielStatusPanel = this.spiel.spielStatusPanel;
		this.playerMode = this.spiel.playerMode;
		this.soundOn = this.spiel.soundOn;
		audio = new AudioEffects();
	}
	

	/**
	 * deaktiviert the "Search Player 2" menueintrag, sobald die Server gestartet wird.
	 */
	public void deactivateStartServerMenuItem() {
		this.menuBar.getMenu(0).getMenuComponent(2).setEnabled(false);
	}
	/**
	 * Sockets geschlossen
	 */
	public void closeSockets() {
		if (gameServer != null) {
			gameServer.close();
		}
		if (gameClient != null) {
			gameClient.close();
		}
	}
	
	public void addScorePlayer1(int x, int y) {
		if (giftBoxIcon.equals(cell[x][y].getIcon())) {
			this.player1Score = this.player1Score + 50;
			this.scoreBoard1.setText(Integer.toString(this.player1Score));
		}
	}
	
	public void addScorePlayer2(int x, int y) {
		if (giftBoxIcon.equals(cell[x][y].getIcon())) {
			this.player2Score = this.player2Score + 50;
			this.scoreBoard2.setText(Integer.toString(this.player2Score));
		}
	}
}

