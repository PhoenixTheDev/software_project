import javax.swing.*;
import java.awt.*;

class CustomScrollPane extends JScrollPane {
    public CustomScrollPane(int panelWidth, int panelHeight, Component element, Color hintergrundFarbe) {
        this.setBounds(0, 60, panelWidth, panelHeight - 96);
        this.setBackground(hintergrundFarbe);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setViewportView(element);
    }

    public CustomScrollPane(int panelWidth, int panelHeight, Component element) {
        this(panelWidth, panelHeight, element, KonstanteWerte.BASIS_FARBEN[2]);
    }
}
