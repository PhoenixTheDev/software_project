import javax.swing.*;
import java.awt.*;

class CustomScrollPane extends JScrollPane {
    Component element;
    int panelWidth, panelHeight;
    Color hintergrundFarbe;

    public CustomScrollPane( int panelWidth, int panelHeight, Component element ) {
        this( panelWidth, panelHeight, element, KonstanteWerte.BASIS_FARBEN[2] );
    }
    
    public CustomScrollPane( int panelWidth, int panelHeight, Component element, Color hintergrundFarbe ) {
        this.element = element;
        this.panelWidth = panelWidth >= 0 ? panelWidth : 0;
        this.panelHeight = panelHeight >= 0 ? panelHeight : 0;
        this.hintergrundFarbe = hintergrundFarbe;

        this.erstelleStandardAussehen();
    }

    private void erstelleStandardAussehen() {
        this.setBounds( 0, 60, panelWidth, panelHeight - 96 );
        this.setBackground( hintergrundFarbe );
        this.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        this.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        this.setBorder( BorderFactory.createEmptyBorder() );
        this.setViewportView( element );
    }

    public void wechselAngezeigtesElement( Component element ) {
        element.setVisible( false );
        this.setViewportView( element );
    }

    public void aktualisiereElement( Component element ) {
        if( element == null )
            return;
        this.element.setVisible( false );
        this.setViewportView( element );
    }
}
