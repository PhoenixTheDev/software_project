import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class FragenHalter extends JPanel {
    TextAntwort[] textArray;
    CheckAntwort[] checkArray;
    String schuelerId, lehrerId;
    DatabaseConnector databaseConnector;
    public AuswahlPanel ap;

    int width, height;

    public FragenHalter( int width, int height, boolean saveButtonActive, DatabaseConnector db, String schuelerId, String lehrerId ) {
        super();

        this.width = width;
        this.height = height;
        this.databaseConnector = db;
        this.schuelerId = schuelerId;
        this.lehrerId = lehrerId;
        
        this.erstelleAussehen( saveButtonActive );
    }

    private void erstelleAussehen( boolean saveButtonActive ) {
        this.setBounds( 0, 0, this.width, this.height + 40 );
        this.setPreferredSize( new Dimension( this.width, this.height + 40 ) );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        this.setLayout( null );

        //save button designen
        if ( saveButtonActive ) {
            JButton bSpeichern = new JButton();
            bSpeichern.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for ( TextAntwort ta : textArray )
                        ta.speichern( databaseConnector, schuelerId, lehrerId );
                    for ( CheckAntwort ca : checkArray )
                        ca.speichern( databaseConnector, schuelerId, lehrerId );
                    if ( !lehrerId.equals( "" ) )
                        ap.ladeZubewertendeNeu();
                    else
                        ap.ladeSchuelerUI();
                }
            });
            bSpeichern.setBounds( width / 2 - 50, height, 100, 30 );
            bSpeichern.setText( "Speichern" );
            bSpeichern.setForeground( KonstanteWerte.STANDARD_FARBE );
            bSpeichern.setBackground( KonstanteWerte.BASIS_FARBEN[0] );
            bSpeichern.setFocusPainted( false );
            bSpeichern.setBorder( new LineBorder( KonstanteWerte.STANDARD_FARBE ) );
            this.add( bSpeichern );
        }
    }
}
