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

    public ButtonListe( String[] bewertet, String[] unbewertet, int panelWidth, int panelHeight, int yPos ) {
        super();
        
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.bewertet = bewertet;
        this.unbewertet = unbewertet;
        
        erstelleStandardAussehen();

        actionListener = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( e.getSource() instanceof SchuelerAuswahlButton ) {
                    if ( letzterButton != null )
                        letzterButton.setForeground( KonstanteWerte.STANDARD_FARBE );
                    letzterButton = ( (SchuelerAuswahlButton) e.getSource() );

                    ( (SchuelerAuswahlButton) e.getSource() ).ladeSchuelerFragebogen();
                    //String text = ((SchuelerAuswahlButton) e.getSource()).getText();
                    //JOptionPane.showMessageDialog(null, text);
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
        this.add( new ButtonListeLabel( 0, 40 * unbewertet.length + 60, panelWidth, 60, "<HTML><U>Bewertet    </U><HTML>", 25 ) );
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
