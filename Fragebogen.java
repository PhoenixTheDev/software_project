import javax.swing.*;

public class Fragebogen extends JPanel {
    CustomScrollPane scrollWindow;
    DatabaseConnector databaseConnector;
    public FragenHalter fragenHalter;

    boolean istLehrer;
    int anzahlFragen, x, y, width, height;
    int padding = 10;
    int paddingBorders = 10;

    public Fragebogen( boolean istLehrer, int x, int y, int width, int height, DatabaseConnector databaseConnector ) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.istLehrer = istLehrer;
        this.databaseConnector = databaseConnector;
        
        this.design();

        scrollWindow = new CustomScrollPane( 0, width, height, new FragenHalter( width, height, false, databaseConnector, "", "" ), KonstanteWerte.BASIS_FARBEN[ 3 ] );
        this.add( scrollWindow );

        //die Anzahl der Lehrerfragen bekommen
        databaseConnector.executeStatement( "SELECT Count(id) FROM lehrerFragen" );
        this.anzahlFragen = Integer.parseInt( databaseConnector.getCurrentQueryResult().getData()[0][0] );
        
    }

    private void design() {
        this.setBounds( x, y, width, height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        this.setLayout( null );
    }

    public void ladeFragen( String idSchueler ) {
        this.ladeFragen( idSchueler, "", new String[0], new String[0] );
    }

    public void ladeFragen( String idSchueler, String[] checkStatistik, String[] textStatistik ) {
        this.ladeFragen( idSchueler, "", checkStatistik, textStatistik );
    }

    public void ladeFragen( String idSchueler, String idLehrer ) {
        this.ladeFragen( idSchueler, idLehrer, new String[0], new String[0] );
    }

    public void ladeFragen( String idSchueler, String idLehrer, String[] checkStatistik, String[] textStatistik ) {
        if ( idLehrer.equals( "" ) ) this.schuelerFragen( idSchueler, checkStatistik, textStatistik );
        else this.lehrerFragen( idSchueler, idLehrer, checkStatistik, textStatistik );
    }

    private void lehrerFragen ( String idSchueler, String idLehrer, String[] checkStatistik, String[] textStatistik ) {
        databaseConnector.executeStatement( 
            String.format( 
                "SELECT f.id, frage, kategorie, antwort " 
                + "FROM lehrerFragen AS f "
                + "LEFT JOIN (SELECT antwort, id FROM beantwortetLehrer WHERE kuerzel = \"%s\" AND schueler = \"%s\") AS b " 
                + "ON f.id = b.id",
            idLehrer, idSchueler ) 
        ); //Alle lehrerFragen mit antwort hinten dran gehangen
        QueryResult qr = databaseConnector.getCurrentQueryResult();
        String[][] data = qr.getData(); //Aufbau von Data Tabelle < id | frage | kategorie | antwort >
        
        this.erstelleFragen( qr, data, idSchueler, idLehrer );
    }

    private void schuelerFragen( String idSchueler, String[] checkAntworten, String[] textAntworten ) {
        databaseConnector.executeStatement( 
            String.format( 
                "SELECT f.id, frage, kategorie, antwort " 
                + "FROM schuelerFragen AS f "
                + "LEFT JOIN (SELECT antwort, id FROM beantwortetSchueler WHERE schuelerId = \"%s\") AS b " 
                + "ON f.id = b.id", 
            idSchueler ) 
        ); //Alle schuelerFragen mit antwort hinten dran gehangen
        QueryResult qr = databaseConnector.getCurrentQueryResult();
        String[][] data = qr.getData(); //Aufbau von Data Tabelle < id | frage | kategorie | antwort >
        
        this.erstelleFragen( qr, data, idSchueler, "" );
    }

    private void erstelleFragen( QueryResult qr, String[][] data, String idSchueler, String idLehrer ) {
        //berechnen der Hoehe für den FragenHalter
        int hoehe = padding;
        int anzahlMc = 0;
        int anzahlT = 0;
        for ( int i = 0; i < qr.getRowCount(); i++ ) {
            if ( data[i][2].equals( "mc" ) ) {
                hoehe += this.padding + CheckAntwort.HEIGHT;
                anzahlMc++;
            }
            else {
                hoehe += this.padding + TextAntwort.HEIGHT;
                anzahlT++;
            }
        }
        
        fragenHalter = new FragenHalter( this.width, hoehe, true, databaseConnector, idSchueler, idLehrer );
        fragenHalter.textArray = new TextAntwort[anzahlT];
        fragenHalter.checkArray = new CheckAntwort[anzahlMc];

        int yPos = this.padding;
        for ( int i = 0, a = 0, b = 0; i < qr.getRowCount(); i++ ) {
            if ( data[i][2].equals( "mc" ) ) {
                CheckAntwort temp = new CheckAntwort( paddingBorders, yPos, this.width - paddingBorders * 2 - 20, data[i][1], Integer.parseInt( data[i][0] ), data[i][3] );
                fragenHalter.add( temp );
                yPos += this.padding + CheckAntwort.HEIGHT;
                fragenHalter.checkArray[a] = temp;
                a++;
            } else { //muss dann eine text antwort sein
                TextAntwort temp = new TextAntwort( paddingBorders, yPos, this.width - paddingBorders * 2 - 20, data[i][1], Integer.parseInt( data[i][0] ), data[i][3] );
                fragenHalter.add( temp );
                yPos += this.padding + TextAntwort.HEIGHT;
                fragenHalter.textArray[b] = temp;
                b++;
            }
        }
        
        this.aktualisiereFenster();
    }

    private void aktualisiereFenster() {
        scrollWindow.aktualisiereElement( fragenHalter );
    }
}