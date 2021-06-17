import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;


public class AuswahlPanel extends JPanel {
    ActionListener actionListener;
    
    int panelWidth;
    int panelHeight;
    int panelX;
    int panelY;
    final ImageIcon suchIcon = new ImageIcon( "Icons/search.png" ), loeschenIcon = new ImageIcon( "Icons/close.png" );
    DatabaseConnector db;
    QueryResult qr;
    String kuerzel;
    String id;

    //Elemente
    SchuelerAuswahlButton[] schuelerAuswahlButtons;
    SchuelerAuswahlButton letzterButton;
    CustomScrollPane buttonListenScrollPane;
    Suchleiste suchleiste;
    //Suchleiste suchleiste;

    public AuswahlPanel( boolean istLehrer, String primaerschluessel, int panelX, int panelY, int panelWidth, int panelHeight ) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.panelX = panelX;
        this.panelY = panelY;
        if(istLehrer == true){     
          this.kuerzel = primaerschluessel;
          this.id = "";
        }
        else{
          this.id = primaerschluessel;
          this.kuerzel = "";
        }

        db = new DatabaseConnector( "10.120.33.187",3306,"SoftwareProjektDB","nepo2","nepo" );

        this.erstelleStandardAussehen();
    
        
        if (istLehrer)
           ladeLehrerUI();
        else 
           ladeSchuelerUI();
    }

    private void erstelleStandardAussehen() {
        this.setBounds( panelX, panelY, panelWidth, panelHeight );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[2] );
        this.setLayout( null );
        this.setVisible( true );
    }

    private void ladeLehrerUI() {
        //sqls fÃ¼r die bewerteten und unbewerteten schÃ¼ler
        String sql = "SELECT DISTINCT name, vorname FROM schueler AS s JOIN beantwortetLehrer AS b ON s.id = b.schueler WHERE b.kuerzel = \"" + kuerzel + "\"";
        String sql2 = "SELECT DISTINCT name, vorname FROM schueler AS s LEFT JOIN beantwortetLehrer AS b ON s.id = b.schueler WHERE s.id NOT IN (SELECT s.id FROM schueler AS s JOIN beantwortetLehrer AS b ON s.id = b.schueler WHERE b.kuerzel = \"" + kuerzel + "\")";
        //bewertet initialisieren
        db.executeStatement(sql);
        if(db.getErrorMessage() != null){
          System.out.println(db.getErrorMessage()); 
          }
        qr = db.getCurrentQueryResult();
        String[] bewertet = new String[qr.getRowCount()];
        for(int i = 0; i < qr.getRowCount(); i++){
           bewertet[i] = qr.getData()[i][0] + " " + qr.getData()[i][1];
          }
        //unbewertet initialisieren
        db.executeStatement(sql2);
        if(db.getErrorMessage() != null){
          System.out.println(db.getErrorMessage()); 
          }
        qr = db.getCurrentQueryResult();
        String[] unbewertet = new String[qr.getRowCount()];
        for(int i = 0; i < qr.getRowCount(); i++){
           unbewertet[i] = qr.getData()[i][0] + " " + qr.getData()[i][1];
          }
        //Testdaten
        //bewertet = { "Bernd", "Brigitte", "Beate", "Benjamin", "Ben", "Boris", "Bertholt", "Backstein", "Bert", "Bernd", "Brigitte", "Beate", "Benjamin", "Ben", "Boris", "Bertholt", "Backstein", "Bert" };
        //unbewertet = { "Ulrich", "Ulma", "Ulricke", "Ursula", "Uli", "Uwu", "Ugonna (dead)", "Ulrich", "Ulma", "Ulricke", "Ursula", "Uli", "Uwu", "Ugonna (dead)" };
        
        this.suchleisteErstellen();
        buttonListenScrollPane = new CustomScrollPane( panelWidth, panelHeight, new ButtonListe(bewertet, unbewertet, panelWidth, 540, 0 ) );
        this.add(buttonListenScrollPane);
    }

    private void ladeSchuelerUI() {
        String sql = "SELECT schuelerId FROM beantwortetSchueler WHERE schuelerId = \"" + id + "\""    ;
        db.executeStatement(sql);
        String[] bewertet;
        String[] unbewertet;
        QueryResult temp = db.getCurrentQueryResult();
        if(db.getCurrentQueryResult().getData().length != 0){
          bewertet = new String[1];
          unbewertet = new String[0];
          bewertet[0] = "SelbsteinschÃ¤tzung";
        }
        else{
          unbewertet = new String[1];
          bewertet = new String[0];
          unbewertet[0] = "SelbsteinschÃ¤tzung";
          }
        buttonListenScrollPane = new CustomScrollPane( panelWidth, panelHeight, new ButtonListe(bewertet, unbewertet, panelWidth, 600, 0 ) );
        this.add( buttonListenScrollPane );
    }

    private void suchleisteErstellen() {
        suchleiste = new Suchleiste( 10, 10, 240, 40, 40, suchIcon);
        this.add( suchleiste );

        suchleiste.getDocument().addDocumentListener( new DocumentListener() {
            public void changedUpdate( DocumentEvent e ) {
                suchleistenFunktion();
            }
      
            public void removeUpdate( DocumentEvent e ) {
                suchleistenFunktion();
            }
            
            public void insertUpdate( DocumentEvent e ) {
                suchleistenFunktion();
            }
        } );
    }

    private void suchleistenFunktion() {
        if( suchleiste.getText().length() <= 0 ) suchleiste.erneuerIcon( suchIcon );
        else suchleiste.erneuerIcon( loeschenIcon );
        ladeZubewertendeNeu();
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
