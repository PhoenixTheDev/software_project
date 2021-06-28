import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CheckAntwort extends JPanel {
    int x, y, width, fragenId;
    static int HEIGHT = 100;
    boolean ueberschreiben;
    String frage, antwort;
    JCheckBox[] checkboxen;

    public CheckAntwort( int x, int y, int width, String frage, int fragenId, String antwort ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.fragenId = fragenId;
        this.frage = frage;
        this.ueberschreiben = false;
        this.antwort = antwort;

        this.erstelleAussehen();
    }

    private void erstelleAussehen() {
        this.setBounds( x, y, width, HEIGHT );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        this.setLayout( null );

        JLabel lFrage = new JLabel();
        lFrage.setText( this.frage );
        lFrage.setBounds( 10, 10, width - 20, 20 );
        lFrage.setForeground( KonstanteWerte.STANDARD_FARBE );
        lFrage.setHorizontalAlignment( SwingConstants.CENTER );
        this.add( lFrage );

        this.erstelleCheckboxen();
    }

    private void erstelleCheckboxen() {
        checkboxen = new JCheckBox[5];
        for ( int i = 0; i < checkboxen.length; i++ ) {
            checkboxen[i] = new JCheckBox();
            checkboxen[i].setBounds( (this.width - 20) / 5 * i + 65, HEIGHT - 50, 20, 20 );
            checkboxen[i].setOpaque( false );
            checkboxen[i].addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        if ( e.getSource() instanceof JCheckBox ) {
                            for ( JCheckBox b : checkboxen ) {
                                b.setSelected( false );
                            }
                            ( (JCheckBox) e.getSource() ).setSelected( true );
                        }
                    }
                }
            );
            if ( this.antwort != null && Integer.parseInt( this.antwort ) == 5 - i ) {
                checkboxen[i].setSelected( true );
                this.ueberschreiben = true;
            }
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
        if ( lehrerId.equals("") ) {
            if ( ueberschreiben ) sql = "UPDATE beantwortetSchueler SET antwort=\"" + cIndex + "\" WHERE id=\"" + fragenId + "\" AND schuelerId=\"" + schuelerId + "\"";
            else sql = String.format( "INSERT INTO beantwortetSchueler (id, schuelerId, antwort) VALUES (%d, \"%s\", \"%s\")", fragenId, schuelerId, cIndex );
        }
        else {
            if ( ueberschreiben ) sql = "UPDATE beantwortetLehrer SET antwort=" + cIndex + " WHERE id=\"" + fragenId + "\" AND schueler=\"" + schuelerId + "\" AND kuerzel=\"" + lehrerId + "\"";
            else sql = String.format( "INSERT INTO beantwortetLehrer (id, kuerzel, antwort, schueler) VALUES (%d, \"%s\", \"%s\", \"%s\")", fragenId, lehrerId, cIndex, schuelerId );
        }
        dbConnector.executeStatement( sql );
    }
}
