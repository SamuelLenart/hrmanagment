package sk.kosickaakademia.lenart;

import sk.kosickaakademia.lenart.Log;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    Log log = new Log();
    private final String query="INSERT INTO games (name, genre, date, players) VALUES (?, ?, ?, ?)";
    private final String INSERTQUERY = "INSERT INTO games (name, genre, date, players)" +
            "VALUES (?, ?, ?, ?)";
    private final String insertNewGame = "INSERT INTO games (name, genre, date, players) VALUES(?, ?, ?, ?)";
    private final String allGames="SELECT * FROM games";
    private final String changeGame="UPDATE games SET name = ? WHERE id = ?";
    private final String SelectGame="SELECT * FROM games WHERE id = ?";
    private final String deleteGame="DELETE FROM games WHERE id = ?";
    public Connection getConnection(){
        try {
            Properties props = new Properties();
            InputStream loader = getClass().getClassLoader().getResourceAsStream("database.properties");
            props.load(loader);
            Class.forName("com.mysql.cj.jdbc.Driver");
            /*String url = props.getProperty("url");
            String username=props.getProperty("username");
            String password=props.getProperty("password");*/
            String url = "jdbc:mysql://localhost:3306/gamedata";
            String username = "root";
            String password = "";
            Connection con = null;
            try {
                con = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException e)
            {
                System.out.println(e);
            }
            if (con!=null){
                log.print("Connection successful");
            }
            return con;
        } catch (Exception exception) {
            log.error(exception.toString());
        }
        return null;
    }
    public void closeConnection(Connection connection){
        if (connection!=null) {
            try {
                connection.close();
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
                ps.setString(2, game.getGenre());
                ps.setInt(3,game.getDate());
                ps.setInt(4, game.getPlayers());
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
    private List<Game> executeSelect(PreparedStatement ps) {
        List<Game> userList=new ArrayList<>();
        int result = 0;
        try{
            ResultSet rs = ps.executeQuery();
            if(rs!=null){
                while(rs.next()) {
                    result++;
                    int id=rs.getInt("id");
                    String name=rs.getString("name");
                    String genre= rs.getString("genre");
                    int date = rs.getInt("date");
                    int players= rs.getInt("players");
                    userList.add(new Game(id,name,genre,date,players));
                    System.out.println(id+" "+name+" "+genre+" "+date+" "+players);
                }
                return userList;
            }else{
                System.out.println("No games found.");
                return null;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        if(userList.size()!=0){
            return userList;
        }else{
            System.out.println("No games found.");
            return null;
        }
    }
    public List<Game> showAllGames(){
        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(allGames);
                System.out.println("success");
                return executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }
    public boolean changeGame(int id,String newGame){
        if (id < 0)
            return false;
        try (Connection connection=getConnection()){
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(changeGame);
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
    public List<Game> SelectGame(int id){
        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(SelectGame);
                ps.setInt(1, id);
                return executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }
    public boolean deleteGame(int id){
        if (SelectGame(id) == null){
            log.error("No game found");
            return false;
        }
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(deleteGame);
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
