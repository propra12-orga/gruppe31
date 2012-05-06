package gruppe31.bomberman;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import javax.swing.JFrame;


public class Spielfeld extends JFrame{
	
	public Spielfeld(){
		super("Bomberman");
		setSize(300,300);
		setLocation(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//System.out.println("before pack");
		pack();
		
	}
	
	public void paint(Graphics g) {
		//System.out.println("Inside Paint");
		int i=0;
		int j=0;
        Graphics2D graphics2 = (Graphics2D) g;
        Rectangle2D rectangle = new Rectangle2D.Float(300, 60, 600, 600);
        graphics2.draw(rectangle);
        for(i=300;i<=900; i=i+60){
        	for(j= 60;j<= 660;j=j+60){
        	      graphics2.drawLine(i, j, 900, j);
        	      graphics2.drawLine(i,j, i,660); 
        	} 	
        }
    }
	
     public static void main(String[] args){
		 Spielfeld spielfeld = new Spielfeld();
		
	}
}



