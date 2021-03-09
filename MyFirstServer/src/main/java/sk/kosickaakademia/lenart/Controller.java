package sk.kosickaakademia.lenart;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Controller {

    @RequestMapping("/hello")
    public String getHello(){
        return "Hello there";
    }

    @RequestMapping("/time")
    public String currentTime(){
        return new Date().toString();
    }

    @RequestMapping("/hello/sk")
    public String getHelloSk(){
        return "Ahoj ty tam";
    }

    @RequestMapping("/hi/test")
    public String getHiTest(){
        return "This is a test.";
    }

    @RequestMapping(path = "/data", method = RequestMethod.POST)
    public String getHiTest(@PathVariable String username){
        return "Hi. How are u doing?";
    }

    @RequestMapping("/hi/{username}")
    public String getWithName(@PathVariable String username){
        return "Hi "+username+". How are u doing?";
    }
}
