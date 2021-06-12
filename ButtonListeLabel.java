import javax.swing.*;
import java.awt.*;

public class ButtonListeLabel extends JLabel {
    ButtonListeLabel( int x, int y, int width, int height, String text, int fontSize ) {
        super();

        this.setText( text );
        this.setBounds( x, y, width, height );
        this.setForeground( KonstanteWerte.STANDARD_FARBE );
        this.setFont( new Font( this.getFont().getName(), Font.PLAIN, fontSize ) );
    }
}
