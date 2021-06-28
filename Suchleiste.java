import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Suchleiste extends JTextField {
  int panelX, panelY, width, height, rundung;
  final ImageIcon suchIcon = new ImageIcon( "Icons/search.png" ), loeschenIcon = new ImageIcon( "Icons/close.png" );
  private Shape shape;
  JLabel iconHalter;
  Suchleiste suchleiste;
  ImageIcon icon;

  // ---------- Anfangsmethoden --------------
  public Suchleiste( int panelX, int panelY, int width, int height, int rundung ) {
    super( rundung );

    this.panelX = panelX;
    this.panelY = panelY;
    this.width = width;
    this.height = height;
    this.rundung = rundung;
    this.icon = suchIcon;
    this.suchleiste = this;

    this.erstelleStandardAussehen();
  }

  private void erstelleStandardAussehen() {
    this.setBounds( panelX, panelY, width, height );
    this.setBackground( KonstanteWerte.BASIS_FARBEN[3] );
    this.setBorder( BorderFactory.createCompoundBorder( this.getBorder(), BorderFactory.createEmptyBorder( 0, 10, 0, 0 ) ) );
    this.setLayout( new BorderLayout() );
    this.setOpaque( false );
    this.setForeground( KonstanteWerte.STANDARD_FARBE );
    
    if ( this.icon != null ) this.erstelleIcon(); 
  }

  private void erstelleIcon() {
    iconHalter = new JLabel( icon );
    iconHalter.setCursor( Cursor.getDefaultCursor() );
    iconHalter.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 10 ) );
    this.add( iconHalter, BorderLayout.LINE_END );

    iconHalter.addMouseListener( new MouseAdapter() {
      @Override
      public void mouseClicked( MouseEvent e ) {
        suchleiste.setText( "" );
      }
    } );
  }

  public void erneuerIcon( int index ) {
    if ( index == 1 || index == 0 ) {
      iconHalter.setIcon( index == 0 ? suchIcon : loeschenIcon );
      iconHalter.setVisible( true );
    }
    else iconHalter.setVisible( false );
  }

  // Methoden um die Runden Ecken zu erstellen **DO NOT TOUCH**
  @Override
  protected void paintComponent( Graphics g ) {
    g.setColor( getBackground() );
    g.fillRoundRect( 0, 0, getWidth()-1, getHeight()-1, rundung, rundung );
    super.paintComponent( g );
  }

  @Override
  protected void paintBorder( Graphics g ) {
    g.setColor( getBackground() );
    g.drawRoundRect( 0, 0, getWidth()-1, getHeight()-1, rundung, rundung);
  }

  @Override
  public boolean contains( int x, int y ) {
    if ( shape == null || !shape.getBounds().equals( getBounds() ) ) {
      shape = new RoundRectangle2D.Float( 0, 0, getWidth()-1, getHeight()-1, rundung, rundung );
    }
    return shape.contains( x, y );
  }
}
