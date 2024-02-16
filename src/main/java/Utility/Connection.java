package Utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connection {

    public static java.sql.Connection getConnection() {

        try (InputStream input = Connection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return null;
            }

            Properties prop = new Properties();

            //load a properties file from class path, inside static method
            prop.load(input);

            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String pass = prop.getProperty("db.password");

            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
