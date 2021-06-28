import java.awt.*;
import javax.swing.*;

public class TestJFrame extends JFrame {
  
  public TestJFrame() { 
    super();
    setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
    int frameWidth = 1200; 
    int frameHeight = 800;
    setSize( frameWidth, frameHeight );
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = ( d.width - getSize().width ) / 2;
    int y = ( d.height - getSize().height ) / 2;
    setLocation( x, y );
    setTitle( "Schülersprechtag" );
    setResizable( false );
    Container cp = getContentPane();
    cp.setLayout( null );
    cp.add( new FragenDialog( 1, "bru" ) );
    setVisible( true ); 
  }
  
  public static void main( String[] args ) {
    new TestJFrame();
  } // end of main
}