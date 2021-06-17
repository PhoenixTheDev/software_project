import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class TextAntwort extends JPanel {
    String text;
    String beantwortet;
    public TextAntwort(int xPos, int yPos, int width, int height, String text) {
        super();
        this.text = text;
        this.setBounds(xPos, yPos , width, height);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[2]);
        this.setLayout(null);
        this.erstellen();
    }

    private void erstellen() {
        JLabel frage = new JLabel();
        JTextArea antwort = new JTextArea();
        frage.setText( text );
        frage.setBounds( 10, 5, 680, 25 );
        frage.setForeground( KonstanteWerte.STANDARD_FARBE );
        frage.setHorizontalAlignment(SwingConstants.CENTER);
        frage.setVerticalAlignment(SwingConstants.CENTER);
        frage.setFont(new Font(this.getFont().getName(), Font.PLAIN, 18));

        antwort.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        antwort.setForeground( KonstanteWerte.STANDARD_FARBE );
        antwort.setBounds( 10, 35, 680, 95 );
        antwort.setFont(new Font(this.getFont().getName(), Font.PLAIN, 16));
        antwort.setCaretColor( KonstanteWerte.STANDARD_FARBE );
        antwort.setWrapStyleWord(true);
        antwort.setLineWrap(true);
        Border border = BorderFactory.createLineBorder( KonstanteWerte.STANDARD_FARBE );
        antwort.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        beantwortet = antwort.getText();

        this.add( frage );
        this.add( antwort );
    }

    private void speichereAntwort(int id, String kuerzel, String schueler) {
        String statement = "INSERT INTO beantwortetLehrer VALUES (?, ?, ?, ?)";
        

    
    }
}
