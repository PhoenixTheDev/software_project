import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;

public class ButtonListe extends JPanel {
    ActionListener actionListener;
    SchuelerAuswahlButton letzterButton;
    SchuelerAuswahlButton[] schuelerAuswahlButtons;
    
    int panelWidth, panelHeight;
    String[] bewertet, unbewertet;
    DatabaseConnector databaseConnector;

    public ButtonListe( String[] bewertet, String[] unbewertet, int panelWidth, int panelHeight, int yPos, DatabaseConnector databaseConnector, Fragebogen fragebogen, String id ) {
        super();
        
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.bewertet = bewertet;
        this.unbewertet = unbewertet;
        this.databaseConnector = databaseConnector;

        erstelleStandardAussehen();

        actionListener = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( e.getSource() instanceof SchuelerAuswahlButton ) {
                    if ( letzterButton != null )
                        letzterButton.setForeground( KonstanteWerte.STANDARD_FARBE );
                    letzterButton = ( (SchuelerAuswahlButton) e.getSource() );
                    letzterButton.setForeground( KonstanteWerte.FOKUS_FARBE );

                    if ( letzterButton.getText().equals( "Selbstbewertung" ) )    fragebogen.ladeFragen( id );
                    else {
                        String[] name = letzterButton.getText().split( " " );
                        databaseConnector.executeStatement( "SELECT id FROM schueler WHERE name = \"" + name[0] + "\" AND vorname = \"" + name[1] + "\"" );
                        fragebogen.ladeFragen( databaseConnector.getCurrentQueryResult().getData()[0][0], id );
                    }
                }
            }
        };

        erstelleListenElemente();
    }

    private void erstelleStandardAussehen() {
        int anzahlElemente = unbewertet.length + bewertet.length;
        this.setMinimumSize( new Dimension( panelWidth, 40 * anzahlElemente + 120 ) );
        this.setPreferredSize( new Dimension( panelWidth, 40 * anzahlElemente + 120 ) );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        this.setLayout( null );
        this.setVisible( true );
        this.setBorder( BorderFactory.createCompoundBorder( this.getBorder(), BorderFactory.createEmptyBorder( 0, 0, 0, 10 ) ) );
    }

    private void erstelleListenElemente() {
        int padding = 120;
        //int trenner = 20;
        this.add( new ButtonListeLabel( 0, 0, panelWidth, 50, "<HTML><U>Unbewertet</U><HTML>", 25 ) );
        this.add( new ButtonListeLabel( 0, 40 * unbewertet.length + 60, panelWidth, 60, "<HTML><U>Bewertet</U><HTML>", 25 ) );
        schuelerAuswahlButtons = new SchuelerAuswahlButton[ unbewertet.length + bewertet.length ];
        for ( int i = 0; i < unbewertet.length; i++ ) {
            schuelerAuswahlButtons[i] = new SchuelerAuswahlButton( unbewertet[i], 40 * i + 60, panelWidth, actionListener );
            this.add( schuelerAuswahlButtons[i] );
        }
        for ( int i = 0; i < bewertet.length; i++ ) {
            schuelerAuswahlButtons[ i + unbewertet.length ] = new SchuelerAuswahlButton( bewertet[i], 40 * unbewertet.length + padding + 40 * i, panelWidth, actionListener );
            this.add( schuelerAuswahlButtons[ i + unbewertet.length ] );
        }
    }
}