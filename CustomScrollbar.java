import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import javax.swing.*;

public class CustomScrollbar extends BasicScrollBarUI {
    private final Dimension d = new Dimension();

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {

    }
}
