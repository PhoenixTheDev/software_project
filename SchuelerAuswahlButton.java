import javax.swing.*;

public class SchuelerAuswahlButton extends JButton{
    SchuelerAuswahlButton(String name, String stufe, int yPos, int width) {
        super();
        this.setText(String.format("s | s", stufe, name));
        this.setForeground(KonstanteWerte.STANDART_FARBE);
        this.setBounds(0, yPos, width, 40);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[1]);
    }
}