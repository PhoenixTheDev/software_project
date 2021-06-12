import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class AuswahlPanel extends JPanel {
    ActionListener actionListener;
    
    int panelWidth;
    int panelHeight;
    int panelX;
    int panelY;
    final ImageIcon suchIcon = new ImageIcon( "Icons/search.png" ), loeschenIcon = new ImageIcon( "Icons/close.png" );
    DatabaseConnector db;

    //Elemente
    SchuelerAuswahlButton[] schuelerAuswahlButtons;
    SchuelerAuswahlButton letzterButton;
    CustomScrollPane buttonListenScrollPane;
    Suchleiste suchleiste;
    //Suchleiste suchleiste;

    public AuswahlPanel( boolean istLehrer, int panelX, int panelY, int panelWidth, int panelHeight ) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.panelX = panelX;
        this.panelY = panelY;

        db = new DatabaseConnector( "10.120.33.187",3306,"SoftwareProjektDB","nepo2","nepo" );

        this.erstelleStandardAussehen();

        if (istLehrer) ladeLehrerUI();
        else ladeSchuelerUI();
    }

    private void erstelleStandardAussehen() {
        this.setBounds( panelX, panelY, panelWidth, panelHeight );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        this.setLayout( null );
        this.setVisible( true );
    }

    private void ladeLehrerUI() {
        String[] bewertet = { "Bernd", "Brigitte", "Beate", "Benjamin", "Ben", "Boris", "Bertholt", "Backstein", "Bert" };
        String[] unbewertet = { "Ulrich", "Ulma", "Ulricke", "Ursula", "Uli", "Uwu", "Ugonna (dead)" };
        
        this.suchleisteErstellen();
        buttonListenScrollPane = new CustomScrollPane( panelWidth, panelHeight, new ButtonListe(bewertet, unbewertet, panelWidth, 540, 0 ) );
        this.add(buttonListenScrollPane);
    }

    private void ladeSchuelerUI() {
        String[] bewertet = { "Selbsteinschätzung" };
        String[] unbewertet = {};

        buttonListenScrollPane = new CustomScrollPane( panelWidth, panelHeight, new ButtonListe(bewertet, unbewertet, panelWidth, 600, 0 ) );
        this.add( buttonListenScrollPane );
    }

    private void suchleisteErstellen() {
        suchleiste = new Suchleiste( 10, 10, 240, 40, 40, suchIcon);
        this.add( suchleiste );

        suchleiste.getDocument().addDocumentListener( new DocumentListener() {
            public void changedUpdate( DocumentEvent e ) {
                if( suchleiste.getText().length() <= 0 ) suchleiste.erneuerIcon( suchIcon );
                else suchleiste.erneuerIcon( loeschenIcon );
                ladeZubewertendeNeu();
            }
      
            public void removeUpdate( DocumentEvent e ) {
                if( suchleiste.getText().length() <= 0 ) suchleiste.erneuerIcon( suchIcon );
                else suchleiste.erneuerIcon( loeschenIcon );
                ladeZubewertendeNeu();
            }
            
            public void insertUpdate( DocumentEvent e ) {
                if( suchleiste.getText().length() <= 0 ) suchleiste.erneuerIcon( suchIcon );
                else suchleiste.erneuerIcon( loeschenIcon );
                ladeZubewertendeNeu();
            }
        } );
    }

    private void ladeZubewertendeNeu() {
        String eingabe = suchleiste.getText();
        String statement = "SELECT Klasse, Vorname, Name FROM Schueler WHERE Name LIKE \"" + eingabe + "%\"";
        db.executeStatement( statement );
        QueryResult qr = db.getCurrentQueryResult();
        if( qr == null )
            return; //hier sollte ein Element erscheinen, welches sagt keine Personen gefunden
        String[] unbewertet = new String[ qr.getRowCount() ];
        for( int i = 0; i < qr.getRowCount(); i++ ){
            unbewertet[i] = qr.getData()[i][0] +" "+ qr.getData()[i][1] +" "+ qr.getData()[i][2];
        }
        String[] bewertet = {"Ente"};
        buttonListeAktualisieren(unbewertet, bewertet);
    }

    private void buttonListeAktualisieren( String[] unbewertet, String[] bewertet ) {
        buttonListenScrollPane.aktualisiereElement( new ButtonListe( bewertet, unbewertet, panelWidth, 600, 0 ) );
    }
}