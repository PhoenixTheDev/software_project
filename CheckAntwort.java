import javax.swing.*;

public class CheckAntwort extends JPanel {
    int x, y, width;
    static int HEIGHT = 100;
    boolean ueberschreiben;
    String idSchueler, idLehrer, fragenId, frage;
    JCheckBox[] checkboxen;

    public CheckAntwort( int x, int y, int width, String frage, String fragenId ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.fragenId = fragenId;
        this.frage = frage;
        this.ueberschreiben = false;

        this.erstelleAussehen();
    }

    private void erstelleAussehen() {
        this.setBounds( x, y, width, HEIGHT );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        this.setLayout( null );

        this.add( new JLabel(this.frage) );

        this.erstelleCheckboxen();
    }

    private void erstelleCheckboxen() {
        checkboxen = new JCheckBox[5];
        for ( int i = 0; i < checkboxen.length; i++ ) {
            checkboxen[i] = new JCheckBox();
            checkboxen[i].setBounds( (width - 20) / 5 * i + 10, HEIGHT - 50, 15, 15 );
            this.add( checkboxen[i] );
        }
    }

    public void ladeStatistik( String[] antworten ) {
        ueberschreiben = true;
        for ( int i = 0; i < checkboxen.length; i++ ) {
            JLabel label = new JLabel();
            label.setBounds( (width - 20) / 5 * i + 10, HEIGHT - 20, 30, 10 );
            label.setForeground( KonstanteWerte.STANDARD_FARBE );
            label.setText( antworten[i] );
        }
    }

    public void speichern( DatabaseConnector dbConnector, String schuelerId, String lehrerId ) {
        int cIndex = 0;
        for ( int i = 0; i < checkboxen.length; i++ ) {
            if ( checkboxen[i].isSelected() ) {
                cIndex = 5 - i;
                break;
            }
        }
        String sql = "";
        if ( idLehrer.equals("") ) {
            if ( ueberschreiben ) sql = "UPDATE bewertetSchueler SET antwort=\"" + cIndex + "\" WHERE id=\"" + fragenId + "\" AND schuelerId\"" + schuelerId + "\"";
            else sql = String.format( "INSERT INTO bewertetSchueler (antwort) VALUES (\"%s\", \"%s\", \"%s\")", fragenId, schuelerId, cIndex );
        }
        else {
            if ( ueberschreiben ) sql = "UPDATE bewertetLehrer SET antwort=" + cIndex + " WHERE id=\"" + fragenId + "\" AND schueler=\"" + schuelerId + "\" AND kuerzel=\"" + lehrerId + "\"";
            else sql = String.format( "INSERT INTO bewertetLehrer (antwort) VALUES (\"%s\", \"%s\", \"%s\", \"%s\")", fragenId, lehrerId, cIndex, schuelerId );
        }
        dbConnector.executeStatement( sql );
    }
}
