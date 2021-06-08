import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AuswahlPanel extends JPanel {
    ActionListener actionListener;
    int panelWidth;
    SchuelerAuswahlButton[] schuelerAuswahlButtons;
    SchuelerAuswahlButton letzterButton;
    JScrollPane buttonListe;

    public AuswahlPanel(boolean istLehrer, int panelX, int panelY, int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;

        this.setBounds(panelX, panelY, panelWidth, panelHeight);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[2]);
        this.setLayout(null);
        this.setVisible(true);

        JPanel panel = new JPanel();
        JPanel testing = new JPanel();
        testing.setBounds(0,0,200,1000);
        panel.add(testing);
        JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);

        if (istLehrer) ladeLehrerUI();
        else ladeSchuelerUI();
    }

    public void ladeLehrerUI() {
        
    }

    public void ladeSchuelerUI() {

    }
}