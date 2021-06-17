//import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;

public class FragenDialog extends JPanel {

  public FragenDialog( boolean istLehrer, String primaerschluessel ) {
    super();
    this.setBounds( 0, 50, 1000, 600 );
    this.setBackground( KonstanteWerte.STANDARD_FARBE );
    this.setLayout( null );
    setVisible( true );

    this.add( new AuswahlPanel( istLehrer, primaerschluessel, 0, 0, 260, 600 ) );
    this.add( new Fragebogen( istLehrer, 260, 0, 740, 600 ) );

  }
}
