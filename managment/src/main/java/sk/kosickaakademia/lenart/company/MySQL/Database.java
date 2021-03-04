package sk.kosickaakademia.lenart.company.MySQL;

import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.enumerator.Gender;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {

    private final String INSERTQUERY = "INSERT INTO user (fName, lName, age, gender)" + "VALUES ( ?, ?, ?, ?)";

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
        } catch (IOException | SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Connection disconnect(Connection connection) {
        try {
            if (connection != null) {
                System.out.println("Goodbye");
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("No database for closing connection / not connected before");
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertNewUser(User user) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(INSERTQUERY);
                ps.setString(1, user.getfName());
                ps.setString(2, user.getlName());
                ps.setInt(3, user.getAge());
                ps.setInt(4, user.getGender().getValue());
                int queryAffected = ps.executeUpdate();
                return queryAffected == 1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public List<User> getFemales() {
        String sql = "SELECT * FROM user WHERE gender = 1";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        } catch (Exception ex) {
        }
        return null;
    }

    public List<User> getMales() {
        String sql = "SELECT * FROM user WHERE gender = 0";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch(Exception ex) {
        }
        return null;
    }

    public List<User> getUsersByAge(int from, int to) {
        if(from > to) {
            System.out.println("Wrong ");
            return null;
        } else {
            try(Connection connection = getConnection()) {
                String AGERANGESELECTIONQUERY = "select * from user where age between ? and ?";
                PreparedStatement preparedStatement = connection.prepareStatement(AGERANGESELECTIONQUERY);
                preparedStatement.setInt(1, from);
                preparedStatement.setInt(2, to);
                executeSelect(preparedStatement);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private List<User> executeSelect(PreparedStatement preparedStatement) {
        List<User> userList = new ArrayList<>();
        int results = 0;
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet != null) {
                while(resultSet.next()) {
                    results++;
                    int id = resultSet.getInt("id");
                    String fName = resultSet.getString("fName");
                    String lName = resultSet.getString("lName");
                    int age = resultSet.getInt("age");
                    int gender = resultSet.getInt("gender");
                    userList.add(new User(id, fName, lName, age, gender));
                    System.out.println(id + " " + fName + " " + lName + " " + age + " " + gender);
                }
            } else {
                System.out.println("No users found");
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if(userList.size() != 0) {
            return userList;
        } else {
            System.out.println("No users found");
            return null;
        }
    }
    public PreparedStatement selectByGender(int gender) {
        if(gender >= 0){
            try(Connection connection = getConnection()) {
                String SELECTBYGENDERQUERY = "select * from user where gender = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(SELECTBYGENDERQUERY);
                preparedStatement.setInt(1, gender);
                executeSelect(preparedStatement);
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}