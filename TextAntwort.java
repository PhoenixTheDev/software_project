import javax.swing.*;
import javax.swing.border.LineBorder;

public class TextAntwort extends JPanel {
    private String text, fragenId;
    private int x, y, width;
    public static int HEIGHT = 40;
    boolean ueberschreiben;
    JTextField tfAntwort;

    public TextAntwort( int x, int y, int width, String text, String fragenId ) {
        super();
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.text = text;
        
        this.lookAndComponents();
    }

    private void lookAndComponents() {
        this.setBounds( x, y, width, HEIGHT );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );

        JLabel lFrage = new JLabel();
        lFrage.setText( text );
        lFrage.setBounds( 10, 10, 680, 10 );
        lFrage.setForeground( KonstanteWerte.STANDARD_FARBE );
        
        tfAntwort = new JTextField();
        tfAntwort.setBounds( 10, 40, 680, 50 );
        tfAntwort.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        tfAntwort.setBorder( new LineBorder( KonstanteWerte.STANDARD_FARBE, 2 ) );
        tfAntwort.setOpaque( false );
        tfAntwort.setForeground( KonstanteWerte.STANDARD_FARBE );

        this.add( lFrage );
        this.add( tfAntwort );
    }

    public void speichern( DatabaseConnector dbConnector, String schuelerId, String lehrerId ) {
        String sql = "";
        if ( lehrerId.equals("") ) {
            if ( ueberschreiben ) sql = "UPDATE bewertetSchueler SET antwort=" + tfAntwort.getText() + " WHERE id=\"" + fragenId + "\" AND schuelerId\"" + schuelerId + "\"";
            else sql = String.format( "INSERT INTO bewertetSchueler (antwort) VALUES (\"%s\", \"%s\", \"%s\")", fragenId, schuelerId, tfAntwort.getText() );
        }
        else {
            if ( ueberschreiben ) sql = "UPDATE bewertetLehrer SET antwort=" + tfAntwort.getText() + " WHERE id=\"" + fragenId + "\" AND schueler=\"" + schuelerId + "\" AND kuerzel=\"" + lehrerId + "\"";
            else sql = String.format( "INSERT INTO bewertetLehrer (antwort) VALUES (\"%s\", \"%s\", \"%s\", \"%s\")", fragenId, lehrerId, tfAntwort.getText(), schuelerId );
        }
        dbConnector.executeStatement( sql );
    }
}
