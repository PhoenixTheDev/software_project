import java.awt.*;
import javax.swing.*;

public class TestJFrame extends JFrame {
  
  public TestJFrame() { 
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 1000; 
    int frameHeight = 650;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Schüler Sprechtag");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    cp.add(new FragenDialog(true));
    setVisible(true);
  }
  
  public static void main(String[] args) {
    new TestJFrame();
  } // end of main
}