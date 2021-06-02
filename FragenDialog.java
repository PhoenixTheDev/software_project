import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FragenDialog extends JPanel {

  public FragenDialog(boolean istLehrer) {
    super();
    this.setBounds(0, 50, 1000, 600);
    this.setBackground(KonstanteWerte.STANDART_FARBE);
    this.setLayout(null);

    this.add(new AuswahlPanel(istLehrer, 0, 0, 260, 600));
    this.add(new Fragebogen(istLehrer, 260, 0, 740, 600));

    setVisible(true);
  }
}
