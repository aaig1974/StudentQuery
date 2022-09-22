package com.gilani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentJDBCQuery {
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
            String sqlQuery = "SELECT a.anrede,\n" +
                    "       p.vorname,\n" +
                    "       p.nachname,\n" +
                    "       p.geb,\n" +
                    "       adr.strasse,\n" +
                    "       adr.haus_nr,\n" +
                    "       o.plz,\n" +
                    "       o.ort,\n" +
                    "       bnd.bnd,\n" +
                    "       f.stand,\n" +
                    "       h.herst_name,\n" +
                    "       m.modell_name,\n" +
                    "       pr.preis FROM uebung_aufgabe.personen AS p\n" +
                    "         INNER JOIN uebung_aufgabe.anrede AS a ON p.anrede_id = a.anrede_id\n" +
                    "         INNER JOIN uebung_aufgabe.adresse AS adr ON p.adresse_id = adr.adresse_id\n" +
                    "         INNER JOIN uebung_aufgabe.orte AS o ON adr.ort_id = o.ort_id\n" +
                    "         INNER JOIN uebung_aufgabe.bundesland AS bnd ON adr.bnd_id = bnd.bnd_id\n" +
                    "         INNER JOIN uebung_aufgabe.person_familenstand AS pf ON pf.perso_id = p.perso_id\n" +
                    "         INNER JOIN uebung_aufgabe.familienstand AS f ON f.fam_id = pf.fam_id\n" +
                    "         INNER JOIN uebung_aufgabe.besitzer AS b ON b.perso_id = p.perso_id\n" +
                    "         INNER JOIN uebung_aufgabe.fahrzeuge AS kfz ON kfz.fahrzeug_id = b.fahrzeug_id\n" +
                    "         INNER JOIN uebung_aufgabe.hersteller AS h ON kfz.hersteller_id = h.hersteller_id\n" +
                    "         INNER JOIN uebung_aufgabe.modelle AS m ON kfz.modelle_id = m.modelle_id\n" +
                    "         INNER JOIN uebung_aufgabe.preise AS pr ON kfz.preise_id = pr.preise_id;";
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
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
