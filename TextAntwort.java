import javax.swing.*;
import javax.swing.border.LineBorder;

public class TextAntwort extends JPanel {
    String text;
    public TextAntwort( int x, int y, int width, int height, String text) {
        super();
        
        this.text = text;

        this.setBounds( x, y, width, height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        this.erstellen();
    }

    private void erstellen() {
        JLabel frage = new JLabel();
        JTextField antwort = new JTextField();
        frage.setText( text );
        frage.setBounds( 10, 10, 680, 10 );
        frage.setForeground( KonstanteWerte.STANDARD_FARBE );
        antwort.setBounds( 10, 40, 680, 50 );
        antwort.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        antwort.setBorder( new LineBorder( KonstanteWerte.STANDARD_FARBE, 2 ) );
        antwort.setOpaque( false );
        antwort.setForeground( KonstanteWerte.STANDARD_FARBE );

        this.add( frage );
        this.add( antwort );
    }
}
