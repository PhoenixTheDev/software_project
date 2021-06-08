import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;

public class SchuelerAuswahlButton extends JButton{
    SchuelerAuswahlButton(String text, int yPos, int width, ActionListener actionListener) {
        super();
        this.setText(text);
        this.setFocusPainted(false);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setForeground(KonstanteWerte.STANDARD_FARBE);
        this.setBounds(0, yPos, width, 40);
        this.setBorder(new LineBorder(KonstanteWerte.BASIS_FARBEN[2]));
        this.setBackground(KonstanteWerte.BASIS_FARBEN[2]);
        this.addActionListener(actionListener);
    }

    public void ladeSchuelerFragebogen() {
        this.setForeground(KonstanteWerte.FOKUS_FARBE);
    }
}