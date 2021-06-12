import javax.swing.*;
import java.awt.event.*;

public class AuswahlPanel extends JPanel {
    ActionListener actionListener;
    
    int panelWidth;
    int panelHeight;
    int panelX;
    int panelY;

    SchuelerAuswahlButton[] schuelerAuswahlButtons;
    SchuelerAuswahlButton letzterButton;
    CustomScrollPane buttonListenScrollPane;

    public AuswahlPanel(boolean istLehrer, int panelX, int panelY, int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.panelX = panelX;
        this.panelY = panelY;

        this.erstelleStandardAussehen();

        if (istLehrer) ladeLehrerUI();
        else ladeSchuelerUI();
    }

    private void erstelleStandardAussehen() {
        this.setBounds(panelX, panelY, panelWidth, panelHeight);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[2]);
        this.setLayout(null);
        this.setVisible(true);
    }

    private void ladeLehrerUI() {
        String[] bewertet = {"Bernd", "Brigitte", "Beate", "Benjamin", "Ben", "Boris", "Bertholt", "Backstein", "Bert"};
        String[] unbewertet = {"Ulrich", "Ulma", "Ulricke", "Ursula", "Uli", "Uwu", "Ugonna (dead)"};
        buttonListenScrollPane = new CustomScrollPane(panelWidth, panelHeight, new ButtonListe(bewertet, unbewertet, panelWidth, 540, 0));
        this.add(buttonListenScrollPane);
    }

    private void ladeSchuelerUI() {
        String[] bewertet = {"Selbsteinschätzung"};
        String[] unbewertet = {};
        buttonListenScrollPane = new CustomScrollPane(panelWidth, panelHeight, new ButtonListe(bewertet, unbewertet, panelWidth, 600, 0));
        this.add(buttonListenScrollPane);
    }
}