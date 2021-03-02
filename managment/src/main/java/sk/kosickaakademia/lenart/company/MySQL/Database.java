package sk.kosickaakademia.lenart.company.MySQL;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            InputStream loader = getClass().getClassLoader().getResourceAsStream("database.properties");
            properties.load(loader);
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            Connection connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println("Connected");
                return connection;
            } else {
                System.out.println("Connection failed");
            }
        }catch (IOException | SQLException exception){
            exception.printStackTrace();
        }return null;
    }

    public Connection disconnect(Connection connection){
        try {
            if(connection != null){
                System.out.println("Goodbye");
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("No database for closing connection / not connected before");
            ex.printStackTrace();
        }
        return null;
    }
}
