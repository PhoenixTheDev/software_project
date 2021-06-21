import javax.swing.*;

public class FragenDialog extends JPanel {
  public FragenDialog( boolean istLehrer, String primaerschluessel ) {
    super();
    this.erstelleAussehen();

    this.add( new AuswahlPanel( istLehrer, primaerschluessel, 0, 0, 260, 600 ) );
    this.add( new Fragebogen( istLehrer, 260, 0, 740, 600 ) );
  }

  private void erstelleAussehen() {
    this.setBounds( 0, 50, 1000, 600 );
    this.setBackground( KonstanteWerte.STANDARD_FARBE );
    this.setLayout( null );
    setVisible( true );
  }
}
