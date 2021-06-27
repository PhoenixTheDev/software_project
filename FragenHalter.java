import javax.swing.*;

public class FragenHalter extends JPanel {
    TextAntwort[] textArray;
    CheckAntwort[] checkArray;

    int width, height;

    public FragenHalter( int width, int height ) {
        super();

        this.width = width;
        this.height = height;
        
        this.erstelleAussehen();
    }

    private void erstelleAussehen() {
        this.setBounds( 0, 0, this.width, this.height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        this.setLayout( null );
    }
}
