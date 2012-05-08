package gruppe31.bomberman;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Random;

import javax.annotation.Resource;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Spielfeld {

	public static void main(String[] args) {
		JFrame frame= new JFrame("Bomberman");
		JPanel pa= new JPanel();
		JPanel pa2= new JPanel();
		JPanel pa3 = new JPanel();
		JButton begin = new JButton("START");
		JButton stop = new JButton("STOP");
		//ImageIcon icon1 = new ImageIcon("bricks1.jpg");
		ImageIcon icon2 = new ImageIcon("/home/meena/bricks2.jpg");	
		frame.add(pa);
		pa.setLayout(new BorderLayout());
		pa3.setLayout(new FlowLayout());
		pa3.add(new JButton("START"));
		pa3.add(new JButton("STOP"));
		pa.add(pa3,BorderLayout.NORTH);
		pa.add(new JButton(""), BorderLayout.EAST);
		pa.add(new JButton(""), BorderLayout.WEST);
		pa.add(new JButton(""),BorderLayout.SOUTH);
		pa2.setLayout(new GridLayout(15, 15));

	    for(int y = 0; y < 15; y++) {
	      for(int x = 0; x < 15; x++) {
	       JLabel l = new JLabel();
	       Random random = new Random();
	       l.setIcon(icon2);
	        pa2.add(l);
	      }
	    }
	    pa.add(pa2,BorderLayout.CENTER);
		frame.setSize(300,300);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}  
}


