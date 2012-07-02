package gruppe31.bomberman;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
/**
 * Diese Klass implementiert Interface Serializable und initializiert alle Variablen mit Variablen des Spiel-Objekts
 */
public class BombermanSocketMessage implements Serializable {

	Spiel spiel;
	
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
	
	//public Spiel spiel;
	
//	public static final int EMPTY_CELL = 1;
//	public static final int BREAKABLE_BRICK = 2;
//	public static final int UNBREAKABLE_BRICK = 3;
//	public static final int PLAYER1 = 4;
//	public static final int PLAYER2 = 5;
	
//	public boolean startGame = false;
	
	public int[][] boardStatus = new int[20][20];
	
//	public static BombermanSocketMessage mockBombermanSocketMessage(int j) {
//		BombermanSocketMessage msg = new BombermanSocketMessage();
//		for (int i = 0; i<20; i++) {
//			msg.boardStatus[i][j] = 2;
//		}
//		return msg;
//	}
	
//	public static BombermanSocketMessage createStartGameMessage() {
//		BombermanSocketMessage msg = new BombermanSocketMessage();
//		msg.startGame = true;
//		return msg;
//	}
	/**
	 * die Konstruktor, die variablen mit variablen von Spiel objekt initializiert
	 * @param spiel
	 * @return msg
	 */
	public static BombermanSocketMessage createGameStatusMessage(Spiel spiel) {
		BombermanSocketMessage msg = new BombermanSocketMessage();
		msg.isBreakableBrickCell = spiel.isBreakableBrickCell;
		msg.isUnbreakableBrickCell = spiel.isUnbreakableBrickCell;
		msg.player1PositionX  = spiel.player1PositionX;
		msg.player1PositionY = spiel.player1PositionY;
		msg.player2PositionX = spiel.player2PositionX;
		msg.player2PositionY = spiel.player2PositionY;
		msg.bombList= spiel.bombList;
		msg.isBombTimerStarted = spiel.isBombTimerStarted;
		msg.exitPositionX = spiel.exitPositionX;
		msg.exitPositionY = spiel.exitPositionY;
		msg.cell = spiel.cell;
		
		
		return msg;
	}
}
