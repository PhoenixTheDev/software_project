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
        this.setMinimumSize( new Dimension( panelWidth, 540 ) );
        this.setPreferredSize( new Dimension( panelWidth, 540 ) );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        this.setLayout( null );
        this.setVisible( true );
    }

    private void erstelleListenElemente() {
        int padding = 20;
        //int trenner = 20;

        schuelerAuswahlButtons = new SchuelerAuswahlButton[ unbewertet.length + bewertet.length ];
        for ( int i = 0; i < unbewertet.length; i++ ) {
            schuelerAuswahlButtons[i] = new SchuelerAuswahlButton( unbewertet[i], 40 * i, panelWidth, actionListener );
            this.add( schuelerAuswahlButtons[i] );
        }
        for ( int i = 0; i < bewertet.length; i++ ) {
            schuelerAuswahlButtons[ i + unbewertet.length ] = new SchuelerAuswahlButton( bewertet[i], 40 * unbewertet.length + padding + 40 * i, panelWidth, actionListener );
            this.add( schuelerAuswahlButtons[ i + unbewertet.length ] );
        }
    }
}
