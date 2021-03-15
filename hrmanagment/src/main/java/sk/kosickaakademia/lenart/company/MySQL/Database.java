package sk.kosickaakademia.lenart.company.MySQL;

import org.springframework.web.bind.annotation.GetMapping;
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
        String fname = new Util().normalizeName(user.getfName());
        String lname = new Util().normalizeName(user.getlName());
        Connection con = getConnection();
        if(con!=null){
            try{
                PreparedStatement ps = con.prepareStatement(INSERTQUERY);
                ps.setString(1,fname);
                ps.setString(2,lname);
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
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            int age = rs.getInt("age");
            int id = rs.getInt("id");
            int gender = rs.getInt("gender");
            User u=new User(id,fname,lname,age,gender);
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

        if(to<from)
            return null;
        try {
            String sql = "SELECT * FROM user WHERE age >= ? AND age <= ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1,from);
            ps.setInt(2, to);
            return executeSelect(ps);
        }catch(Exception ex){
            log.error(ex.toString());
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
    public User getUserById(int id){
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1,id);
            List<User> list = executeSelect(ps);
            if(list.isEmpty())
                return null;
            else
                list.get(0);
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    public boolean changeAge(int )
}
