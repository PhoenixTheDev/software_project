import javax.swing.*;

public class Fragebogen extends JPanel {
    CustomScrollPane pain;
    DatabaseConnector db;
    boolean istLehrer;
    int length, x, y, width, height;
    FragenHalter fragenHalter;

    public Fragebogen( boolean istLehrer, int x, int y, int width, int height ) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.istLehrer = istLehrer;
        
        this.erstelleAussehen();

        fragenHalter = new FragenHalter( width, height );
        pain = new CustomScrollPane( width, height, fragenHalter, KonstanteWerte.BASIS_FARBEN[ 3 ] );
        db = new DatabaseConnector( "10.120.33.187", 3306, "SoftwareProjektDB", "nepo2", "nepo" );

        //die Anzahl der Lehrerfragen bekommen
        db.executeStatement( "SELECT Count(id) FROM lehrerFragen" );
        this.length = Integer.parseInt( db.getCurrentQueryResult().getData()[ 0 ][ 0 ] );
        
    }

    private void erstelleAussehen() {
        this.setBounds( x, y, width, height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        this.setLayout( null );
    }

    public void ladeFragen( String idSchueler ) {
        this.ladeFragen( idSchueler, "", new String[ 0 ], new String[ 0 ] );
    }

    public void ladeFragen( String idSchueler, String[] checkAntworten, String[] textAntworten ) {
        this.ladeFragen( idSchueler, "", checkAntworten, textAntworten );
    }

    public void ladeFragen( String idSchueler, String idLehrer ) {
        this.ladeFragen( idSchueler, idLehrer, new String[ 0 ], new String[ 0 ] );
    }

    public void ladeFragen( String idSchueler, String idLehrer, String[] checkAntworten, String[] textAntworten ) {
        if ( idLehrer.equals( "" ) ) this.ladeSchuelerFragen( idSchueler, checkAntworten, textAntworten );
        else this.ladeLehrerFragen( idSchueler, idLehrer, checkAntworten, textAntworten );
    }

    private void ladeLehrerFragen ( String idSchueler, String idLehrer, String[] checkAntworten, String[] textAntworten ) {
        db.executeStatement( "SELECT Count(id) FROM lehrerFragen" );
        this.length = Integer.parseInt( db.getCurrentQueryResult().getData()[ 0 ][ 0 ] );

        //die eigene Antwort bekommen
        db.executeStatement( "SELECT * FROM beantwortetLehrer AS b RIGHT JOIN lehrerFragen AS f ON b.id = f.id WHERE b.id = \"" + idLehrer + "\" AND schueler = \"" + idSchueler + "\"" );
        String[][] data = db.getCurrentQueryResult().getData();
        for ( int i = 0; i < length; i++ ) {
            if ( data[ i ][ 6 ].equals( "mc" ) ) {
            } else {

            }
        }
        
        this.clearView();
    }

    private void ladeSchuelerFragen( String idSchueler, String[] checkAntworten, String[] textAntworten ) {
        db.executeStatement( "SELECT Count(id) FROM schuelerFragen" );
        this.length = Integer.parseInt( db.getCurrentQueryResult().getData()[ 0 ][ 0 ] );

        String sql = "SELECT * from ";

        this.clearView();
    }

    private void clearView() {
        pain.wechselAngezeigtesElement( fragenHalter );
    }
}