package sk.kosickaakademia.lenart;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    Log log = new Log();
    private final String query="INSERT INTO user (name, created, genre, players) VALUES ( ?, ?, ?, ?)";
    private final String INSERTQUERY = "INSERT INTO user (name, created, genre, players)" +
            "VALUES (?, ?, ?, ?)";
    public Connection getConnection(){
        try {
            Properties props = new Properties();
            InputStream loader = getClass().getClassLoader().getResourceAsStream("database.properties");
            props.load(loader);
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = props.getProperty("url");
            String username=props.getProperty("username");
            String password=props.getProperty("password");
            Connection con = DriverManager.getConnection(url, username, password);
            log.print("Connection successful");
            return con;
        } catch (Exception exception) {
            log.error(exception.toString());
        }
        return null;
    }
    public void closeConnection(Connection con){
        if (con!=null) {
            try {
                con.close();
                log.print("Connection closed");
            } catch (SQLException throwables) {
                log.error(throwables.toString());
            }
        }
    }
}
