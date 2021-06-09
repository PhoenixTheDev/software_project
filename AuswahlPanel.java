import javax.swing.*;
import java.awt.event.*;

public class AuswahlPanel extends JPanel {
    ActionListener actionListener;
    int panelWidth;
    SchuelerAuswahlButton[] schuelerAuswahlButtons;
    SchuelerAuswahlButton letzterButton;
    JScrollPane buttonListenScrollPane = new JScrollPane();

    public AuswahlPanel(boolean istLehrer, int panelX, int panelY, int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;

        this.setBounds(panelX, panelY, panelWidth, panelHeight);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[2]);
        this.setLayout(null);
        this.setVisible(true);

        String[] bewertet = {"Bernd", "Brigitte", "Beate", "Benjamin", "Ben", "Boris", "Bertholt", "Backstein", "Bert"};
        String[] unbewertet = {"Ulrich", "Ulma", "Ulricke", "Ursula", "Uli", "Uwu", "Ugonna (dead version)"};

        buttonListenScrollPane.setBounds(0, 60, panelWidth, panelHeight - 100);
        buttonListenScrollPane.setBackground(KonstanteWerte.BASIS_FARBEN[2]);
        buttonListenScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        buttonListenScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonListenScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(buttonListenScrollPane);
        buttonListenScrollPane.setViewportView(new ButtonListe(bewertet, unbewertet, panelWidth, 0));

        if (istLehrer) ladeLehrerUI();
        else ladeSchuelerUI();
    }

    public void ladeLehrerUI() {
        
    }

    public void ladeSchuelerUI() {

    }
}