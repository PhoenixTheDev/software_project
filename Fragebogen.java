import javax.swing.*;

public class Fragebogen extends JPanel {
    CustomScrollPane pain;
    DatabaseConnector db;
    boolean istLehrer;
    int length, x, y, width, height;

    public Fragebogen( boolean istLehrer, int x, int y, int width, int height ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.istLehrer = istLehrer;
        
        pain = new CustomScrollPane( width, height, new FragenHalter( width, height, 1, 1 ), KonstanteWerte.BASIS_FARBEN[ 3 ] );
        db = new DatabaseConnector( "10.120.33.187", 3306, "SoftwareProjektDB", "nepo2", "nepo" );

        //die Anzahl der Lehrerfragen bekommen
        db.executeStatement( "SELECT Count(id) FROM lehrerFragen" );
        this.length = Integer.parseInt( db.getCurrentQueryResult().getData()[ 0 ][ 0 ] );
        
    }

    private void erstelleAussehen(int x, int y, int width, int height) {
        this.setBounds( x, y, width, height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        this.setLayout( null );
    }

    public void ladeStatistik( String[] antworten, String idSchueler, String idLehrer ) {
        // keine Statistik laden, wenn es keine Statistik gibt
        if ( antworten.length == 0 ) this.ladeFragen( idSchueler, idLehrer );
        else {
            
        }
        
        
    }

    public void ladeFragen( String idSchueler, String idLehrer ) {
        if ( idLehrer.equals( "" ) ) this.ladeSchuelerFragen();
        else this.ladeLehrerFragen( idSchueler, idLehrer );
    }

    public void ladeLehrerFragen ( String idSchueler, String idLehrer ) {
        for ( int i = 0; i < length; i++ ) {
            String sql = "SELECT * from beantwortetLehrer WHERE schueler = \"" + idSchueler + "\" AND kuerzel = \"" + idLehrer + "\" AND id = \"" + i + "\"";
            db.executeStatement(sql);
            String[][] result = db.getCurrentQueryResult().getData();
            if ( result.length == 0 ) {
                this.add( new TextAntwort( 1, 1, 10, 10, "frage" ) );
            }
        }
        
    }

    public void ladeSchuelerFragen() {
        String sql = "SELECT * from ";
    }
}