import javax.swing.*;
import java.awt.*;

class CustomScrollPane extends JScrollPane {
    Component element;
    int panelWidth, panelHeight, y;
    Color hintergrundFarbe;

    public CustomScrollPane( int y, int panelWidth, int panelHeight, Component element ) {
        this( panelWidth, panelHeight, element, KonstanteWerte.BASIS_FARBEN[2] );
    }
    
    public CustomScrollPane( int panelWidth, int panelHeight, Component element, Color hintergrundFarbe ) {
        this.element = element;
        this.panelWidth = panelWidth >= 0 ? panelWidth : 0;
        this.panelHeight = panelHeight >= 0 ? panelHeight : 0;
        this.hintergrundFarbe = hintergrundFarbe;

        this.erstelleStandardAussehen();
        //this.erstelleCustomScrollbar();
    }

    private void erstelleStandardAussehen() {
        this.setBounds( 0, y, panelWidth, panelHeight - 96 );
        this.setBackground( hintergrundFarbe );
        this.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
        this.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        this.setBorder( BorderFactory.createEmptyBorder() );
        this.setViewportView( element );
    }

    /*private void erstelleCustomScrollbar() {
        this.setLayout( new ScrollPaneLayout() {
            @Override
            public void layoutContainer( Container parent ) {
                JScrollPane scrollPane = (JScrollPane) parent;
                Rectangle availR = scrollPane.getBounds();
                availR.x = availR.y = 0;

                Insets parentInsets = parent.getInsets();
                availR.x = parentInsets.left;
                availR.y = parentInsets.top;
                availR.width -= parentInsets.left + parentInsets.right;
                availR.height -= parentInsets.top + parentInsets.bottom;
                
                Rectangle vsbR = new Rectangle();
                vsbR.width = 12;
                vsbR.height = availR.height;
                vsbR.x = availR.x + availR.width - vsbR.width;
                vsbR.y = availR.y;

                if ( viewport != null ) {
                    viewport.setBounds( availR );
                }
                if ( vsb != null ) {
                    vsb.setVisible( true );
                    vsb.setBounds( vsbR );
                }
            }
        } );
        this.getVerticalScrollBar().setUI( new CustomScrollbar() );
    }*/

    public void wechselAngezeigtesElement( Component element ) {
        element.setVisible( false );
        this.setViewportView( element );
    }

    public void aktualisiereElement( Component element ) {
        if( element == null ) return;
        this.element.setVisible( false );
        this.setViewportView( element );
    }
}
