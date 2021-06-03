import javax.swing.*;

public class AuswahlPanel extends JPanel {
    public AuswahlPanel(boolean istLehrer, int panelX, int panelY, int panelWidth, int panelHeight) {
        this.setBounds(panelX, panelY, panelWidth, panelHeight);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[2]);
        this.setLayout(null);

        this.setVisible(true);
    }
}