package sk.kosickaakademia.lenart;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    Database database = new Database();
    Log log = new Log();
    @PostMapping("/games/add")
    public ResponseEntity<String> insertNewGame(@RequestBody String input) {
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(input);

            String name = (String.valueOf(object.get("name")));
            String genre = (String.valueOf(object.get("genre")));
            int date = Integer.parseInt((String) object.get("date"));
            int players = Integer.parseInt((String) object.get("players"));
            if (name == null || name.trim().length() == 0 || genre==null || genre.trim().length()==0) {
                log.error("Missing game");
                JSONObject obj = new JSONObject();
                obj.put("error", "missing game");
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).
                        body(obj.toJSONString());
            }
            Game game = new Game(0, name, genre, date, players);
            new Database().insertNewGame(game);
        } catch (ParseException e) {
            log.error("ERROR");
            e.printStackTrace();
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).
                body(null);
    }
    @GetMapping("/games")
    public ResponseEntity<String> games(){
        List<Game> list = new Database().showAllGames();
        String json = new Game(0, null, null, 0, 0).getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
    @PostMapping("/games/game")
    public ResponseEntity<String> SelectGame(@RequestBody String body) {
       JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(body);

            int id = Integer.parseInt((String) object.get("id"));
            if (id <= 0) {
                log.error("Missing game");
                JSONObject obj = new JSONObject();
                obj.put("error", "missing game");
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).
                        body(obj.toJSONString());
            }
            List<Game> list = new Database().SelectGame(id);
            //list.add(new Database().SelectGame(id));
            String JSON = new Game(0, null, null, 0, 0).getJSON(list);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(JSON);
        } catch (ParseException e) {
            log.error("ERROR");
            e.printStackTrace();
            return ResponseEntity.status(400).body("error");
        }
    }
    @PostMapping(value="/games/delete")
    public ResponseEntity<String> deleteGame(@RequestBody String body){
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(body);

            int id = Integer.parseInt((String) object.get("id"));
            if (id <= 0) {
                log.error("Missing game");
                JSONObject obj = new JSONObject();
                obj.put("error", "missing game");
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).
                        body(obj.toJSONString());
            }
            new Database().deleteGame(id);
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).
                    body(null);
        } catch (ParseException e) {
            log.error("ERROR");
            e.printStackTrace();
            return ResponseEntity.status(400).body("error");
        }
    }
    @PostMapping(value="/games/update")
    public ResponseEntity<String> changeGame(@RequestBody String body){
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(body);

            int id = Integer.parseInt((String) object.get("id"));
            String name = (String.valueOf(object.get("name")));
            if (id <= 0 || name == null) {
                log.error("Missing game");
                JSONObject obj = new JSONObject();
                obj.put("error", "missing game");
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).
                        body(obj.toJSONString());
            }
            new Database().changeGame(id, name);
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).
                    body(null);
        } catch (ParseException e) {
            log.error("ERROR");
            e.printStackTrace();
            return ResponseEntity.status(400).body("error");
        }
    }
}
