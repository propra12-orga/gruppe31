package gruppe31.bomberman;
abstract class Figur {
  protected final int _x, _y;  

  
  Figur(final int i_x, final int i_y)
  {
    _x = i_x;   _y = i_y;
  }
  final int x(){return _x;}
  final int y(){return _y;}
  abstract void zeichnen(final java.awt.Graphics io_graphics);
}

class Kreis extends Figur {
  private final int _r;   

  
  Kreis(final int i_x, final int i_y, final int i_r)
  {
    super(i_x, i_y);
    _r = i_r;
  }

  final int r(){return _r;}
  void zeichnen(final java.awt.Graphics io_graphics){
    io_graphics.drawOval(_x-_r, _y-_r, 2*_r, 2*_r);
  }
}

class Rechteck extends Figur {
  private final int _b, _h; 

  
  Rechteck(final int i_x, final int i_y, final int i_b, final int i_h)
  {
    super(i_x, i_y);
    _b = i_b; _h = i_h;
  }

  
  void zeichnen(final java.awt.Graphics io_graphics)
  {
    io_graphics.drawRect(_x, _y, _b, _h);
  }

}

class MyCanvas extends java.awt.Canvas {
  public void paint(final java.awt.Graphics io_graphics){

    final java.awt.Rectangle rectangle = getBounds();
    final int breite = rectangle.width  - 1;
    final int hoehe  = rectangle.height - 1;

    final Kreis kopf = new Kreis(
      breite/2, 
      hoehe/2, 
      Math.min(breite, hoehe)*2/5
    );
    final Kreis linkesAuge = new Kreis(
      kopf.x()-kopf.r()/3, 
      kopf.y()-kopf.r()/2,
      kopf.r()/4
    );
    final Kreis rechtesAuge = new Kreis(
      kopf.x()+kopf.r()/3, 
      kopf.y()-kopf.r()/2,
      kopf.r()/4
    );
    final Rechteck mund = new Rechteck(
      kopf.x() - kopf.r()/2,
      kopf.y() + kopf.r()/4,
      kopf.r(),
      kopf.r()/4
    );
                          
    final Figur[] figuren = {kopf, linkesAuge, rechtesAuge, mund};
    for(int i=0; i<figuren.length; i++){
      figuren[i].zeichnen(io_graphics); 
    }
  }
}

public class Graphik {

  public static void main(final String[] i_args) throws Exception {
    final java.awt.Frame frame 
    = new java.awt.Frame("erste schritt");
    frame.setSize(300,200);
    frame.add(new MyCanvas());
    frame.setVisible(true);
  }

}
