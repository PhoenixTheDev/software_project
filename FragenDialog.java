import javax.swing.*;

public class FragenDialog extends JPanel {
    DatabaseConnector databaseConnector;
    Fragebogen fragebogen;
    AuswahlPanel auswahlPanel;

    public FragenDialog( int istLehrer, String primaerschluessel ) {
        this( istLehrer, primaerschluessel, null );
    }

    public FragenDialog( int istLehrer, String primaerschluessel, DatabaseConnector databaseConnector ) {
        super();
        this.erstelleAussehen();

        // ich bin nicht in der Lage mich mit dem PI bei Dario zu verbinden, wieso auch immer -> andere sind in der Lage
        if ( databaseConnector != null )
            this.databaseConnector = databaseConnector;
        else
            this.databaseConnector = new DatabaseConnector( "62.155.187.115", 3306, "SoftwareProjektDB", "nepo2", "nepo" ); //Darioport: 62.155.187.115; Schulport: 10.120.33.187
        
        if ( this.databaseConnector.getErrorMessage() != null ) {
            this.add( new ErrorPanel( 1000, 600, "Es konnte keine Verbindung mit der Datenbank hergestellt werden" ) );
        } else {
            fragebogen = new Fragebogen( istLehrer > 0 ? true : false, 260, 0, 740, 600, databaseConnector );
            this.add( new AuswahlPanel( istLehrer > 0 ? true : false, primaerschluessel, 0, 0, 260, 600, databaseConnector, fragebogen ) );
            this.add( fragebogen );
        }
    }

    private void erstelleAussehen() {
        this.setBounds( 0, 50, 1000, 600 );
        this.setBackground( KonstanteWerte.STANDARD_FARBE );
        this.setLayout( null );
        setVisible( true );
    }
}