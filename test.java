import java.util.Scanner;
public class test {
    //Ein SQL ausführen und in der Konsole ausgeben.
    public test()
    {
        Scanner scanner = new Scanner(System.in);
        DatabaseConnector dbc = new DatabaseConnector("10.120.33.187",3306,"SoftwareProjektDB","nepo2","nepo");
        String sql = "SELECT * FROM beantwortetSchueler";
        dbc.executeStatement(sql);

        System.out.println(dbc.getErrorMessage());

        QueryResult qr = dbc.getCurrentQueryResult();
        for(int i = 0; i < qr.getRowCount(); i++){
            for(int j = 0; j < qr.getColumnCount(); j++){
                System.out.println(qr.getData()[i][j]);
            }
            System.out.println(" ");
        }
        String e = scanner.nextLine(); 
    }

    public static void main(String args[]){new test();}
}
