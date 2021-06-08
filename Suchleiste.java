import javax.swing.*;

public class Suchleiste extends JTextField {
    public Suchleiste(int panelX, int panelY, int width, int height) {
           super();
        this.setBounds(panelX, panelY, width, height);
        this.setBackground(KonstanteWerte.BASIS_FARBEN[3]);
        
    DatabaseConnector db = new DatabaseConnector("10.120.33.187",3306,"SoftwareProjektDB","nepo2","nepo");
    }
  
  public String[] schuelerSuchen(String pSuchbegriff){
    String statement = "SELECT Klasse, Vorname, Name FROM Schueler WHERE Name LIKE \"" + pSuchbegriff + "%\"";
    db.executeStatement(statement);
    QueryResult qr = db.getCurrentQueryResult();
    String[] schuelerArray = new String[qr.getRowCount()];
    for(int i = 0; i < qr.getRowCount(); i++){
      schuelerArray[i] = qr.getData()[i][0] +" "+ qr.getData()[i][1] +" "+ qr.getData()[i][2];
    }
    return schuelerArray;
    } 
}
