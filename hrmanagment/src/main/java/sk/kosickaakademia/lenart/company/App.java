package sk.kosickaakademia.lenart.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.lenart.company.MySQL.Database;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.util.Util;


@SpringBootApplication
public class App 
{
    public static void main(String[] args)
    {
        System.out.println(args[0]);
        SpringApplication.run(App.class,args);
    }
}
