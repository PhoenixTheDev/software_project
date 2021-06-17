import javax.swing.*;

public class Fragebogen extends JPanel {
  DatabaseConnector db;
  public Fragebogen(boolean istLehrer, int panelX, int panelY, int panelWidth, int panelHeight) {
    db = new DatabaseConnector( "10.120.33.187",3306,"SoftwareProjektDB","nepo2","nepo" );
    this.setBounds(panelX, panelY, panelWidth, panelHeight);
    this.setBackground(KonstanteWerte.BASIS_FARBEN[3]);
    this.setLayout(null);
    this.setVisible(true);
    this.ladeFragen();
    
  }
  
  private void ladeFragen() {
    String statement = "SELECT frage FROM lehrerFragen WHERE id < 3";
    db.executeStatement( statement );
    QueryResult qr = db.getCurrentQueryResult();
    String[] frage = new String[ qr.getRowCount() ];
    
    for (int i = 0; i < qr.getRowCount(); i++) {
        frage[i] = qr.getData()[i][0];
        this.add(new TextAntwort(10, i* 150 + 20, 700, 140, frage [i]));
    }
    
  }
}
