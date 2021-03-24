package sk.kosickaakademia.lenart.company.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.lenart.company.log.LOG;
import sk.kosickaakademia.lenart.company.MySQL.Database;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.enumerator.Gender;
import sk.kosickaakademia.lenart.company.util.Util;

import java.util.List;


@RestController
public class Controller {
    LOG log = new LOG();

    @PostMapping("/user/new")
    public ResponseEntity<String> insertNewUser(@RequestBody String data) {
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);

            String fname = (String) object.get("fname");
            String lname = (String) object.get("lname");
            String gender = (String) object.get("gender");
            int age = Integer.parseInt(String.valueOf(object.get("age")));
            System.out.println(age);
            if (fname == null || lname == null || lname.trim().length() == 0 || fname.trim().length() == 0 || age < 1) {
                log.error("Missing lname or fname.");
                JSONObject obj = new JSONObject();
                obj.put("error", "missing lname or fname.");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).
                        body(obj.toJSONString());
            }
            Gender g;
            if (gender == null) {
                g = Gender.OTHER;
            } else if (gender.equalsIgnoreCase("male")) {
                g = Gender.MALE;
            } else if (gender.equalsIgnoreCase("female")) {
                g = Gender.FEMALE;
            } else
                g = Gender.OTHER;
            User user = new User(fname, lname, age, g.getValue());
            new Database().insertNewUser(user);

        } catch (Exception e) {
            log.error("Cannot process input data.");
            JSONObject obj = new JSONObject();
            obj.put("error", "cannot process input data");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).
                    body(obj.toJSONString());
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).
                body(null);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        List<User> list = new Database().getAllUsers();
        String json = new Util().getJson(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("users/age?from=A&to=B")
    public ResponseEntity<String> getUsersbyAge(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to) {
        System.out.println(from + " " + to);
        if (from > to) {
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        List<User> list = new Database().getUsersByAge(from, to);
        for(User user: list)
        System.out.println(user);
        String json = new Util().getJson(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> changeAge(@PathVariable Integer id, @RequestBody String body){
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(body);
        }catch (ParseException e){
            e.printStackTrace();
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");
        }

        String data = (String)object.get("newage");
        int newage = Integer.parseInt(data);
        if(newage<1)
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");

        System.out.println(id+" "+newage);
        boolean result =  new Database().changeAge(id, newage);
        int status;
        if (result) status = 200; else status = 404;

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body("{}");
    }

    @GetMapping("/stats")
    public ResponseEntity<String> overview(){
        List<User> list = new Database().getAllUsers();
        String jsonStatistic = new Util().getStatistic(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(jsonStatistic.toString());
    }


}