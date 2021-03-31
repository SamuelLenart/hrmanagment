package sk.kosickaakademia.lenart.company.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.lenart.company.log.LOG;
import sk.kosickaakademia.lenart.company.util.Util;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecretController {
    private final String PASSWORD = "Kosice2021";
    Map<String, String> map = new HashMap<>();
    LOG log = new LOG();

    @GetMapping("/secret")
    public String secret(@RequestHeader("token") String header) {
        System.out.println(header);
        String token = header.substring(7);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(token)) {
                return "secret";
            }
        }
        return "401";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String auth) {
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(auth);
            String login = ((String) object.get("login"));
            String password = ((String) object.get("password"));
            System.out.println(login + " " + password);
            if (login == null || password == null) {
                log.error("Wrong login/password");
                return ResponseEntity.status(400).body("");
            }
            if (password.equals(PASSWORD)) {
                String token = new Util().generateToken();
                map.put(login, token);
                log.print("U are logged!");
                JSONObject object1 = new JSONObject();
                object1.put("login", login);
                object1.put("token", "Bearer " + token);
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object1.toJSONString());
            } else {
                log.error("Wrong passowrd");
                return ResponseEntity.status(401).body("");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String header) {
        String token = header.substring(7);
        String login = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(token)) {
                login = entry.getKey();
                break;
            }
        }
        int status;
        if (login != null) {
            map.remove(login);
            log.info("Goodbye: " + login);
            status = 204;
        } else {
            log.error("Logout failed. User " + login + " does not exist.");
            status = 404;
        }
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body("");
    }

    @PostMapping("/student")
    public ResponseEntity<String> student(@RequestHeader("token") String token){
        String login = "";
        for(Map.Entry<String, String> entry : map.entrySet()) {
            if(entry.getValue().equals(token)){
                login = entry.getKey();
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("Student of Kosicka Academy, name: " + login);
            }
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("You are only a host user which is not a student");
    }
}
