import javax.swing.*;

public class Fragebogen extends JPanel {
    public Fragebogen(boolean istLehrer, int panelX, int panelY, int panelWidth, int panelHeight) {
        this.setBounds(panelX, panelY, panelWidth, panelHeight);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[3]);
        this.setLayout(null);
    }
}