package sk.kosickaakademia.lenart;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Database {
    Log log = new Log();
    private final String query="INSERT INTO user (name, created, genre, players) VALUES ( ?, ?, ?, ?)";
    private final String INSERTQUERY = "INSERT INTO user (name, created, genre, players)" +
            "VALUES (?, ?, ?, ?)";
    private final String insertNewGame = "INSERT INTO gamedatabase (DataName, DataGenre, DataPlayers) VALUES(?, ?, ?)";
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
    public boolean insertNewGame(Game game){
        Connection conn=getConnection();
        if(conn!=null){
            try{
                PreparedStatement ps=conn.prepareStatement(insertNewGame);
                ps.setString(1, game.getName());
                ps.setString(2,game.getGenre());
                ps.setString(3, game.getPlayers());
                int result=ps.executeUpdate();
                closeConnection(conn);
                log.print("New Game was added to Database.");
                return result==1;
            }catch(SQLException ex){
                log.error(ex.toString());
            }
        }
        return false;
    }
    public List<Game> showAllGames(){
        try (Connection conn = getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(showAllGames);
                return executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }
    public boolean changeGame(int id,String newGame){
        if (id < 0)
            return false;
        try (Connection conn=getConnection()){
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(changeGame);
                ps.setString(1, newGame);
                ps.setInt(2, id);
                int update=ps.executeUpdate();
                log.print("Updated game for id: " + id);
                return update==1;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }
    public Game getGameById(int id){
        try (Connection conn = getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(gameByID);
                ps.setInt(1, id);
                return (Game) executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }
    public boolean deleteGame(int id){
        if (getGameById(id) == null){
            log.error("No game found");
            return false;
        }
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(deleteGame);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 1){
                log.print("Deleted game: " + id);
                return true;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }
}
