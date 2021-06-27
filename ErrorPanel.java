import javax.swing.*;

public class ErrorPanel extends JPanel {
    public ErrorPanel( int width, int height, String warning ) {
        super();

        this.setBounds( 0, 0, width, height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        this.setLayout( null );
        this.setVisible( true );

        JLabel lWarning = new JLabel();
        lWarning.setText( warning );
        lWarning.setBounds( 0, height / 2 - 50, width, 20 );
        lWarning.setHorizontalAlignment( SwingConstants.CENTER );
        lWarning.setForeground( KonstanteWerte.STANDARD_FARBE );
        this.add( lWarning );
    }
}
