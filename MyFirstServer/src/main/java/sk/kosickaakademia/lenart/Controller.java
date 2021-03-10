package sk.kosickaakademia.lenart;

import org.springframework.web.bind.annotation.*;

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
    public String getHiTest(@RequestBody String text){
        return "Hi. This is a test page. Your name is: "+text;
    }

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public String getTest(@RequestBody String text){
        return "Let's go!"+text;
    }

    @RequestMapping("/hi/{username}")
    public String getWithName(@PathVariable String username){
        return "Hi "+username+". How are u doing?";
    }
}
