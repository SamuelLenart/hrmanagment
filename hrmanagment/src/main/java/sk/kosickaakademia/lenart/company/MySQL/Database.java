package sk.kosickaakademia.lenart.company.MySQL;

import org.springframework.web.bind.annotation.GetMapping;
import sk.kosickaakademia.lenart.company.entity.Statistic;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.enumerator.Gender;
import sk.kosickaakademia.lenart.company.log.LOG;
import sk.kosickaakademia.lenart.company.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {

    LOG log = new LOG();
    private final String query="INSERT INTO user (fname, lname, age, gender) VALUES ( ?, ?, ?, ?)";
    private final String female="SELECT * FROM user WHERE gender = 1";
    private final String male="SELECT * FROM user WHERE gender = 0";
    private final String other="SELECT * FROM user WHERE gender = 2";
    private final String usersId="SELECT * FROM user WHERE id = ?";
    private final String userPattern="SELECT * FROM user WHERE (fname, lname) = ?";
    private final String allUsers="SELECT * FROM user ";
    private final String newUserAge = "UPDATE user SET age = ? WHERE id = ?";
    private final String usersByAge ="SELECT * FROM user WHERE age >= ? AND age <= ?";
    private final String INSERTQUERY = "INSERT INTO user (fname, lname, age, gender)" +
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
    public boolean insertNewUser(User user){
        if(user==null)
            return false;
        String fName = new Util().normalizeName(user.getfName());
        String lName = new Util().normalizeName(user.getlName());
        Connection con = getConnection();
        if(con!=null){
            try{
                PreparedStatement ps = con.prepareStatement(INSERTQUERY);
                ps.setString(1,fName);
                ps.setString(2,lName);
                ps.setInt(3,user.getAge());
                int result = ps.executeUpdate();
                closeConnection(con);
                log.print("New user has been added");
                return result==1;
            }catch(SQLException ex){
                log.error(ex.toString());
            }
        }
        return false;
    }
    private List<User> executeSelect(PreparedStatement ps) throws SQLException {
        ResultSet rs =  ps.executeQuery();
        List<User> list = new ArrayList<>();
        int count = 0;
        while(rs.next()){
            count ++;
            String fName = rs.getString("fname");
            String lName = rs.getString("lname");
            int age = rs.getInt("age");
            int id = rs.getInt("id");
            int gender = rs.getInt("gender");
            User u=new User(id,fName,lName,age,gender);
            list.add(u);
        }
        log.info("Number of records: "+ count);
        return list;
    }
    public List<User> getFemales(){
        log.info("Executing: getFemales()");
        String sql = "SELECT * FROM user WHERE gender = 1";
        try (Connection con = getConnection()) {
            if(con!=null) {
                PreparedStatement ps = con.prepareStatement(sql);
                return executeSelect(ps);
            }
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    public List<User> getMales(){
        String sql = "SELECT * FROM user WHERE gender = 0";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    public List<User> getUsersByAge(int from, int to){
        if(to<from){
            return null;
        }
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE age >= ? AND age <= ? ORDER BY age";
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, from);
            ps.setInt(2, to);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                int age = rs.getInt("age");
                int id = rs.getInt("id");
                int gender = rs.getInt("gender");
                User user = new User(id, fName, lName, age, gender);
                list.add(user);
            }
            closeConnection(con);
            return list;
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }
    public List<User> getAllUsers(){

        String sql = "SELECT * FROM user";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return null;

    }
    public User getUserByID(int id){
        String sql = "SELECT * FROM user WHERE id LIKE ?";
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                int age = rs.getInt("age");
                int idecko = rs.getInt("id");
                int gender = rs.getInt("gender");
                User user = new User(idecko, fName, lName, age, gender);
                closeConnection(con);
                return user;
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }
    public boolean changeAge(int id, int newAge){
        if (id < 0 || newAge < 1 || newAge >= 100)
            return false;
        try (Connection conn=getConnection()){
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(newUserAge);
                ps.setInt(1, newAge);
                ps.setInt(2, id);
                int update=ps.executeUpdate();
                log.print("Updated age for id: " + id);
                return update==1;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }

    public List<User> getUser(String pattern){
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE fname like ? OR lname like ?";

        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,"%" + pattern + "%");
            ps.setString(2,"%" + pattern + "%");
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String fName = rs.getString("fname");
                String lName = rs.getString("lname");
                int age = rs.getInt("age");
                int idecko = rs.getInt("id");
                int gender = rs.getInt("gender");
                User user = new User(idecko, fName, lName, age, gender);
                list.add(user);
            }
            closeConnection(con);
            return list;
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

}
