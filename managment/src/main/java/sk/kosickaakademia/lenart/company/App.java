package sk.kosickaakademia.lenart.company;

import sk.kosickaakademia.lenart.company.MySQL.Database;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.util.Util;

import java.sql.Connection;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Database database = new Database();
        database.insertNewUser(new User("Julia","Novakova",21, 1));
        List<User> list = database.getUsersByAge(20, 50);
        for(User u:list)
        System.out.println(new Util().getJson(u));
    }
}
