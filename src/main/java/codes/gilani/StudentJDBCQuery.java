package codes.gilani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentJDBCQuery {
    @SuppressWarnings("unused")
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private static final String JDBC_DRIVER =  "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/udemy?serverTimezone=UTC";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Tsi#41";

    public void handleDatabase() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
            String sqlQuery = """
                    SELECT a.anrede,
                           p.vorname,
                           p.nachname,
                           p.geb,
                           adr.strasse,
                           adr.haus_nr,
                           o.plz,
                           o.ort,
                           bnd.bnd,
                           f.stand,
                           h.herst_name,
                           m.modell_name,
                           pr.preis FROM uebung_aufgabe.personen AS p
                             INNER JOIN uebung_aufgabe.anrede AS a ON p.anrede_id = a.anrede_id
                             INNER JOIN uebung_aufgabe.adresse AS adr ON p.adresse_id = adr.adresse_id
                             INNER JOIN uebung_aufgabe.orte AS o ON adr.ort_id = o.ort_id
                             INNER JOIN uebung_aufgabe.bundesland AS bnd ON adr.bnd_id = bnd.bnd_id
                             INNER JOIN uebung_aufgabe.person_familenstand AS pf ON pf.perso_id = p.perso_id
                             INNER JOIN uebung_aufgabe.familienstand AS f ON f.fam_id = pf.fam_id
                             INNER JOIN uebung_aufgabe.besitzer AS b ON b.perso_id = p.perso_id
                             INNER JOIN uebung_aufgabe.fahrzeuge AS kfz ON kfz.fahrzeug_id = b.fahrzeug_id
                             INNER JOIN uebung_aufgabe.hersteller AS h ON kfz.hersteller_id = h.hersteller_id
                             INNER JOIN uebung_aufgabe.modelle AS m ON kfz.modelle_id = m.modelle_id
                             INNER JOIN uebung_aufgabe.preise AS pr ON kfz.preise_id = pr.preise_id;""";
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                System.out.print(resultSet.getString("anrede") + ", ");
                System.out.print(resultSet.getString("vorname") + ", ");
                System.out.print(resultSet.getString("nachname") + " ");
                System.out.print("," + sdf.format(resultSet.getDate("geb")) + " ");
                System.out.print(resultSet.getString("strasse") + " ");
                System.out.print(resultSet.getString("haus_nr") + " ");
                System.out.print(resultSet.getString("plz") + "\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
