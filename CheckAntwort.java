import javax.swing.*;

public class CheckAntwort extends JPanel {
    int x, y, width;
    static int HEIGHT = 100;
    boolean ueberschreiben;
    String idSchueler, idLehrer;
    JCheckBox[] checkboxen;

    public CheckAntwort( int x, int y, int width, String idSchueler, String fragenId ) {
        this( x, y, width, idSchueler, "", fragenId );
    }

    public CheckAntwort( int x, int y, int width, String idSchueler, String idLehrer, String fragenId ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.idSchueler = idSchueler;
        this.idLehrer = idLehrer;

        this.erstelleAussehen();
    }

    private void erstelleAussehen() {
        this.setBounds( x, y, width, HEIGHT );
        this.setBackground( KonstanteWerte.BASIS_FARBEN[ 2 ] );
        this.setLayout( null );
    }

    private void erstelleCheckboxen() {

    }

    public void ladeFrageMitStatistik( String[] frage, String[] antworten ) {
        ueberschreiben = true;
    }

    public void ladeFrage() {
        ueberschreiben = false;
    }

    public void speichern( DatabaseConnector dbConnector ) {
        int cIndex = 0;
        for ( int i = 0; i < checkboxen.length; i++ ) {
            if ( checkboxen[i ].isSelected() ) {
                cIndex = 5 - i;
                break;
            }
        }
        String sql = "";
        String tabelle = this.idLehrer.equals( "" ) ? "bewertetSchueler" : "bewertetLehrer";
        if ( idLehrer.equals( "" ) ) {
            if ( ueberschreiben ) sql = "UPDATE " + tabelle + " SET antwort=" + cIndex + " WHERE " ;
            else sql = "INSERT ";
            
        }
        dbConnector.executeStatement( sql );

    }
}
