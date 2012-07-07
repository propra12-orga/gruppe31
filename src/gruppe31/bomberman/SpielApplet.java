package gruppe31.bomberman;

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
