

package gruppe31;
  
 import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

public class Bombermanfigur extends Applet implements Runnable
{
	// Initialisierung der Variablen
	int x_pos = 100;			// x - Position des Balles
	int y_pos=300;		// y - Position des Balles
	int x_speed = 0;		// Geschwindigkeit des Balles in x - Richtung
	static int radius = 10;		// Radius des Balles
	int appletsize_x = 200; // Größe des Applets in x - Richtung
	int appletsize_y = 200;	// Größe des Applets in y - Richtung
	 



	// Variablen für die Doppelpufferung
	private Image dBild;
	private Graphics db;

	public void init()
	{
		setBackground (Color.white);
	
	}

	public void start ()
	{
		// Schaffen eines neuen Threads, in dem das Spiel läuft
		Thread th = new Thread (this);
		// Starten des Threads
		th.start ();
	}

	public void stop()
	{

	}

	public void destroy()
	{

	}
	

	public boolean keyDown (Event e, int key)
	{
	// linke Cursortaste
		if (key == Event.LEFT)
		{
		//Ball bewegt sich dann nach links
			x_speed = -1;
		}
		// rechte Cursortaste
		else if (key == Event.RIGHT)
		{
			// Ball bewegt sich dann nach rechts
			x_speed = +1;
		}
		// SpaceBar hat Wert 32
	else if (key == 32)
	{
		x_speed = +1;
	}
	// if(key==Event.UP)
		//{ y_pos=+1;}
		//else if(key==Event.DOWN)
		//{ y_pos=-1;}
			// Ausgabe von gedrüktem Key und Zahlenwert an die Standardausgabe
		//	System.out.println ("Charakter: " + (char)key + " Integer Value: " + key);
		


		return true;
	}
	//public boolean keyUp (Event e, int key){
		//if (key==33){
		//	appletsize_y =1;
		//}
		//return true;
//	}

	public void run ()
	{
		// Erniedrigen der ThreadPriority um zeichnen zu erleichtern
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		// Solange true ist läuft der Thread weiter
		while (true)
		{
			// Wenn der Ball den rechten Rand berührt, dann prallt er ab
			if (x_pos> appletsize_x)
			{
				// Ändern der Richtung des Balles
				x_speed = -1;
			}
			// Ball brührt linken Rand und prallt ab
			
			
		else if (x_pos < radius )
			{
				// Ändern der Richtung des Balles
			x_speed = +1;
			}

			// Verändern der x- Koordinate
		x_pos += x_speed;
		

			// Neuzeichnen des Applets
			repaint();

			try
			{
				// Stoppen des Threads für in Klammern angegebene Millisekunden
				Thread.sleep (20);
			}
			catch (InterruptedException ex)
			{
				
			}

			// Zurücksetzen der ThreadPriority auf Maximalwert
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}

	/** Update - Methode, Realisierung der Doppelpufferung zur Reduzierung des Bildschirmflackerns */
	public void update (Graphics g)
	{
		//Initialisierung des DoubleBuffers
		if (dBild == null)
		{
			dBild= createImage(this.getSize().width, this.getSize().height);
			db = dBild.getGraphics ();
		}

	// Bildschirm im Hintergrund löschen
		db.setColor (getBackground ());
		db.fillRect (0, 0, this.getSize().width, this.getSize().height);

		// Auf gelöschten Hintergrund Vordergrund zeichnen
	db.setColor (getForeground());
		paint (db);

		//Nun fertig gezeichnetes Bild Offscreen auf dem richtigen Bildschirm anzeigen
		g.drawImage (dBild, 0, 0, this);
	}

	public void paint (Graphics g)
	{
		
		resize(700,500);
	

		 Image bild = getImage(this.getCodeBase(),"bomber.jpg");
		 g.drawImage(bild, x_pos,y_pos, this);
		}

public static void main (String [] args ) {
	Bombermanfigur b =new Bombermanfigur();
	
	

}
	
	
	}