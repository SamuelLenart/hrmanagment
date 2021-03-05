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

    public List<User> getUsersByAge(int from, int to) {
        if (from > to) {
            System.out.println("Wrong ");
            return null;
        } else {
            try (Connection connection = getConnection()) {
                String usersbyage = "select * from user where age between ? and ?";
                PreparedStatement ps2 = connection.prepareStatement(usersbyage);
                ps2.setInt(1, from);
                ps2.setInt(2, to);
                executeSelect(ps2);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private List<User> executeSelect(PreparedStatement ps3) {
        List<User> userList = new ArrayList<>();
        int results = 0;
        try {
            ResultSet resultSet = ps3.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
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
        if (userList.size() != 0) {
            return userList;
        } else {
            System.out.println("No users found");
            return null;
        }
    }

    public List<User> selectByGender(int gender) {
        if (gender >= 0) {
            try (Connection connection = getConnection()) {
                String selectbygender = "select * from user where gender = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectbygender);
                preparedStatement.setInt(1, gender);
                return executeSelect(preparedStatement);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public List<User> selectRangeBasedOnUserAge(int from, int to) {
        if (from > to) {
            System.out.println("Input should be ascending");
            return null;
        } else {
            try (Connection connection = getConnection()) {
                String agerangebased = "select * from user where age between ? and ?";
                PreparedStatement preparedStatement = connection.prepareStatement(agerangebased);
                preparedStatement.setInt(1, from);
                preparedStatement.setInt(2, to);
                return executeSelect(preparedStatement);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public User getUserById(int id) {
        String userbyid = "select * from user where id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(userbyid);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String fname = resultSet.getString("fName");
                String lname = resultSet.getString("lName");
                int age = resultSet.getInt("age");
                int gender = resultSet.getInt("gender");
                User user = new User(id, fname, lname, age, gender);
                user.toString();
                return user;
            } else {
                System.out.println("User not found");
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        String allusers = "select * from user";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(allusers);
            return executeSelect(preparedStatement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean changeUserAge(int id, int newAge) {
        String changeuserage = "update user set age = ? where id = ?";
        if (id > 0 && (!(newAge > 99) && !(newAge < 1))) {
            try (Connection connection = getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(changeuserage);
                preparedStatement.setInt(1, newAge);
                preparedStatement.setInt(2, id);
                int queriesAffected = preparedStatement.executeUpdate();
                return queriesAffected == 1;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Wrong parameters");
            System.out.println("ID can't be less than zero");
            System.out.println("Age must be between 1 and 99");
            return false;
        }
        return false;
    }

    public List<User> getUsersByPattern(String pattern) {
        String getusersbypattern = "select * from user where fName like ? or lName like ?";
        String formatedPattern = "%" + pattern + "%";
        if (pattern.equals("")) {
            System.out.println("Pattern required");
            return null;
        } else {
            try (Connection connection = getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(getusersbypattern);
                preparedStatement.setString(1, formatedPattern);
                preparedStatement.setString(2, formatedPattern);
                return executeSelect(preparedStatement);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
