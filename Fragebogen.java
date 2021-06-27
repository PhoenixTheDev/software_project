import javax.swing.*;
import javax.swing.border.LineBorder;

public class Fragebogen extends JPanel {
    CustomScrollPane scrollWindow;
    DatabaseConnector databaseConnector;
    FragenHalter fragenHalter;
    JButton bSpeichern;

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

        scrollWindow = new CustomScrollPane( width, height, new FragenHalter( width, height ), KonstanteWerte.BASIS_FARBEN[ 3 ] );
        this.add( scrollWindow );

        //die Anzahl der Lehrerfragen bekommen
        databaseConnector.executeStatement( "SELECT Count(id) FROM lehrerFragen" );
        this.anzahlFragen = Integer.parseInt( databaseConnector.getCurrentQueryResult().getData()[0][0] );
        
    }

    private void design() {
        this.setBounds( x, y, width, height );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
        this.setLayout( null );

        //save button designen
        bSpeichern = new JButton();
        bSpeichern.setBounds( width / 2 - 50, 5950, 100, 50);
        bSpeichern.setText( "Speichern" );
        bSpeichern.setForeground( KonstanteWerte.STANDARD_FARBE );
        bSpeichern.setBackground( KonstanteWerte.BASIS_FARBEN[0] );
        bSpeichern.setBorder( new LineBorder( KonstanteWerte.STANDARD_FARBE ) );
        this.add( bSpeichern );
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
        if ( idLehrer.equals( "" ) ) this.ladeSchuelerFragen( idSchueler, checkStatistik, textStatistik );
        else this.ladeLehrerFragen( idSchueler, idLehrer, checkStatistik, textStatistik );
    }

    private void ladeLehrerFragen ( String idSchueler, String idLehrer, String[] checkStatistik, String[] textStatistik ) {
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
        
        fragenHalter = new FragenHalter( this.width, 6000 );
        
        int xPos = this.padding;
        for ( int i = 0; i < qr.getRowCount(); i++ ) {
            if ( data[ i ][ 2 ].equals( "mc" ) ) {
                fragenHalter.add( new CheckAntwort( xPos, paddingBorders, this.width - paddingBorders * 2, data[i][1], data[i][0] ) );
                xPos += this.padding + CheckAntwort.HEIGHT;
            } else { //muss dann eine text antwort sein
                fragenHalter.add( new TextAntwort( xPos, paddingBorders, this.width - paddingBorders * 2, data[i][1], data[i][0] ) );
                xPos += this.padding + TextAntwort.HEIGHT;
            }
        }
        
        this.aktualisiereFenster();
    }

    private void ladeSchuelerFragen( String idSchueler, String[] checkAntworten, String[] textAntworten ) {
        databaseConnector.executeStatement( 
            String.format( 
                "SELECT id, frage, kategorie, antwort " 
                + "FROM schuelerFragen AS f "
                + "LEFT JOIN (SELECT antwort, id FROM beantwortetSchueler WHERE id = \"%s\") AS b " 
                + "ON f.id = b.id", 
            idSchueler ) 
        ); //Alle schuelerFragen mit antwort hinten dran gehangen
        QueryResult qr = databaseConnector.getCurrentQueryResult();
        String[][] data = qr.getData(); //Aufbau von Data Tabelle < id | frage | kategorie | antwort >
        
        fragenHalter = new FragenHalter( this.width, 6000 );
        
        int xPos = this.padding;
        for ( int i = 0; i < qr.getRowCount(); i++ ) {
            if ( data[i][2].equals( "mc" ) ) {
                fragenHalter.add( new CheckAntwort( xPos, paddingBorders, this.width - paddingBorders * 2, data[i][1], data[i][0] ) );
                xPos += this.padding + CheckAntwort.HEIGHT;
            } else { //muss dann eine text antwort sein
                fragenHalter.add( new TextAntwort( xPos, paddingBorders, this.width - paddingBorders * 2, data[i][1], data[i][0] ) );
                xPos += this.padding + TextAntwort.HEIGHT;
            }
        }
        
        this.aktualisiereFenster();
    }

    private void aktualisiereFenster() {
        scrollWindow.wechselAngezeigtesElement( fragenHalter );
    }

    public void disableSaveButton() {
        bSpeichern.setVisible( false );
    }

    public void enableSaveButton() {
        bSpeichern.setVisible( true );
    }
}