import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;


public class AuswahlPanel extends JPanel {
    ActionListener actionListener;
    
    int width, height, x, y;
    DatabaseConnector databaseConnector;
    QueryResult qr;
    String kuerzel, id;
    String[] bewertet, unbewertet;
    Fragebogen fragebogen;

    //Elemente
    SchuelerAuswahlButton[] schuelerAuswahlButtons;
    SchuelerAuswahlButton letzterButton;
    CustomScrollPane buttonListenScrollPane;
    Suchleiste suchleiste;
    //Suchleiste suchleiste;

    public AuswahlPanel( boolean istLehrer, String primaerschluessel, int panelX, int panelY, int panelWidth, int panelHeight, DatabaseConnector databaseConnector, Fragebogen fragebogen ) {
        this.width = panelWidth;
        this.height = panelHeight;
        this.x = panelX;
        this.y = panelY;
        this.databaseConnector = databaseConnector;
        this.fragebogen = fragebogen;

        this.erstelleAussehen();

        if( istLehrer ){     
          this.kuerzel = primaerschluessel;
          this.id = "";
          ladeLehrerUI();
        }
        else{
          this.id = primaerschluessel;
          this.kuerzel = "";
          ladeSchuelerUI();
        }
    }

    private void erstelleAussehen() {
        this.setBounds( x, y, width, height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[ 2 ] );
        this.setLayout( null );
        this.setVisible( true );
    }

    private void ladeLehrerUI() {        
        String[] bewertet = getBewerteteSchueler( "" );
        String[] unbewertet = getUnbewerteteSchueler( "" );
        
        this.suchleisteErstellen(); //man kann nur suchen, wenn man lehrer ist
        buttonListenScrollPane = new CustomScrollPane( 60, width, height, new ButtonListe( bewertet, unbewertet, width, 540, 0, databaseConnector, fragebogen, kuerzel ) );
        this.add( buttonListenScrollPane );
    }

    private void ladeSchuelerUI() {
        //als Schueler gibt es nur die Selbsteinschätzung
        String sql = "SELECT schuelerId FROM beantwortetSchueler WHERE schuelerId = \"" + id + "\""; //checken ob schon einmal Selbsteingeschätzt wurde
        String[] bewertet;
        String[] unbewertet;
        
        databaseConnector.executeStatement( sql );
        QueryResult qr = databaseConnector.getCurrentQueryResult();
        
        if ( qr.getData().length != 0 ) {
            bewertet = new String[1];
            unbewertet = new String[ 0 ];
            bewertet[ 0 ] = "Selbstbewertung";
        }
        else {
            unbewertet = new String[ 1 ];
            bewertet = new String[ 0 ];
            unbewertet[ 0 ] = "Selbstbewertung";
        }
        buttonListenScrollPane = new CustomScrollPane( 60, width, height, new ButtonListe( bewertet, unbewertet, width, 600, 0, databaseConnector, fragebogen, id ) );
        this.add( buttonListenScrollPane );
    }

    private void suchleisteErstellen() {
        suchleiste = new Suchleiste( 10, 10, 240, 40, 40 );
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
        if ( suchleiste.getText().length() <= 0 ) suchleiste.erneuerIcon( 0 );
        else suchleiste.erneuerIcon( 1 );
        ladeZubewertendeNeu();
    }

    private void ladeZubewertendeNeu() {
        String[] bewertet = getBewerteteSchueler( suchleiste.getText() );
        String[] unbewertet = getUnbewerteteSchueler( suchleiste.getText() );

        buttonListeAktualisieren(unbewertet, bewertet);
    }

    private void buttonListeAktualisieren( String[] unbewertet, String[] bewertet ) {
        buttonListenScrollPane.aktualisiereElement( new ButtonListe( bewertet, unbewertet, width, 600, 0, databaseConnector, fragebogen, id ) );
    }

    private String[] getBewerteteSchueler( String suche ) {
        String sqlBewertet = 
            "SELECT DISTINCT name, vorname "
            + "FROM schueler AS s "
            + "JOIN beantwortetLehrer AS b "
            + "ON s.id = b.schueler "
            + "WHERE b.kuerzel = \"" + kuerzel + "\" "
            + ( suche.equals( "" ) ? "" : "AND (s.name LIKE \"%" + suche +  "%\" OR s.vorname LIKE \"%" + suche + "%\")" ); //alle beantworteten schueler bekommen

        databaseConnector.executeStatement(sqlBewertet);
        qr = databaseConnector.getCurrentQueryResult();
        String[] bewertet = new String[ qr.getRowCount() ];
        for ( int i = 0; i < qr.getRowCount(); i++ ) {
            bewertet[ i ] = qr.getData()[ i ][ 0 ] + " " + qr.getData()[ i ][ 1 ];
        }
        return bewertet;
    }
    
    private String[] getUnbewerteteSchueler( String suche ) {
        String sqlUnbewertet = 
            "SELECT DISTINCT name, vorname "
            + "FROM schueler AS s "
            + "LEFT JOIN beantwortetLehrer AS b "
            + "ON s.id = b.schueler "
            + "WHERE s.id NOT IN "
                + "(SELECT s.id "
                + "FROM schueler AS s "
                + "JOIN beantwortetLehrer AS b "
                + "ON s.id = b.schueler "
                + "WHERE b.kuerzel = \"" + kuerzel + "\") "
            + ( suche.equals( "" ) ? "" : "AND (s.name LIKE \"%" + suche +  "%\" OR s.vorname LIKE \"%" + suche + "%\")" ); //alle unbeantworteten schueler bekommen

        databaseConnector.executeStatement( sqlUnbewertet );
        qr = databaseConnector.getCurrentQueryResult();
        String[] unbewertet = new String[ qr.getRowCount() ];
        for ( int i = 0; i < qr.getRowCount(); i++ ) {
            unbewertet[ i ] = qr.getData()[ i ][ 0 ] + " " + qr.getData()[ i ][ 1 ];
        }
        return unbewertet;
    }
}