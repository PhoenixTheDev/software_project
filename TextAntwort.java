import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class TextAntwort extends JPanel {
    private String text, antwort;
    private int x, y, width, fragenId;
    public static int HEIGHT = 200;
    boolean ueberschreiben;
    JTextArea tfAntwort;

    public TextAntwort( int x, int y, int width, String text, int fragenId, String antwort ) {
        super();
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.text = text;
        this.fragenId = fragenId;
        this.antwort = antwort;
        
        this.lookAndComponents();
    }

    private void lookAndComponents() {
        this.setBounds( x, y, width, HEIGHT );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );

        JLabel lFrage = new JLabel();
        lFrage.setText( text );
        lFrage.setBounds( 10, 10, width - 20, 20 );
        lFrage.setForeground( KonstanteWerte.STANDARD_FARBE );
        
        tfAntwort = new JTextArea();
        tfAntwort.setBounds( 10, 100, 680, 50 );
        tfAntwort.setLineWrap( true );
        tfAntwort.setPreferredSize( new Dimension( width - 20, HEIGHT - 40 ) );
        tfAntwort.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        tfAntwort.setBorder( new LineBorder( KonstanteWerte.STANDARD_FARBE, 2 ) );
        tfAntwort.setOpaque( false );
        tfAntwort.setForeground( KonstanteWerte.STANDARD_FARBE );

        if ( this.antwort != null ) {
            tfAntwort.setText( this.antwort );
            ueberschreiben = true;
        }

        this.add( lFrage );
        this.add( tfAntwort );
    }

    public void speichern( DatabaseConnector dbConnector, String schuelerId, String lehrerId ) {
        String sql = "";
        if ( lehrerId.equals("") ) {
            if ( ueberschreiben ) sql = "UPDATE beantwortetSchueler SET antwort=\"" + tfAntwort.getText() + "\" WHERE id=\"" + fragenId + "\" AND schuelerId=\"" + schuelerId + "\"";
            else sql = String.format( "INSERT INTO beantwortetSchueler (id, schuelerId, antwort) VALUES (%d, \"%s\", \"%s\")", fragenId, schuelerId, tfAntwort.getText() );
        }
        else {
            if ( ueberschreiben ) sql = "UPDATE beantwortetLehrer SET antwort=" + tfAntwort.getText() + " WHERE id=\"" + fragenId + "\" AND schueler=\"" + schuelerId + "\" AND kuerzel=\"" + lehrerId + "\"";
            else sql = String.format( "INSERT INTO beantwortetLehrer (id, kuerzel, antwort, schueler) VALUES (%d, \"%s\", \"%s\", \"%s\")", fragenId, lehrerId, tfAntwort.getText(), schuelerId );
        }
        dbConnector.executeStatement( sql );
    }
}
