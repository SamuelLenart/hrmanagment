package sk.kosickaakademia.lenart.company.controller;

import org.apache.commons.logging.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import sk.kosickaakademia.lenart.company.MySQL.Database;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.enumerator.Gender;


@RestController
public class Controller {
    Log log = new Log();
    @PostMapping("/user/new")
    public ResponseEntity<String> insertNewUser(@RequestBody String data) {
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);

            String fName = ((String) object.get("fName")).trim();
            String lName = ((String) object.get("lName")).trim();
            String gender = (String) object.get("gender");
            Integer age = Integer.parseInt((String) object.get("fName"));
            if (fName == null || lName == null || lName.length() = 0 || fName.length() == 0 || age < 1) {
                log.error("Missing lName or fName in the body of the request.");
                JSONObject obj = new JSONObject();
                obj.put("error", "Missing lName or fName in the body of the request.");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());
            }
        } catch (Exception exception) {
            log.error("Cannot process input data in /user/new controller");
            JSONObject object = new JSONObject();
            object.put("error", "Cannot process input data in /user/new controller");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());
        }
        Gender g;
        if (gender == null)
            g = Gender.OTHER;
    } else if(gender.equalsIgnoreCase("male")){
        g=Gender.MALE;
        else if(gender.equalsIgnoreCase("female")) {
            g = Gender.FEMALE;
        }else
            g=Gender.OTHER;

        User user = new User(fName, lName, age, g.getValue());
        new Database().insertNewUser(user);
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString(null));
    }
    return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON);
}
