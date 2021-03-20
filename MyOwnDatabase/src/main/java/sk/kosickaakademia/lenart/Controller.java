package sk.kosickaakademia.lenart;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    Log log = new Log();

    public ResponseEntity<String> insertNewGame(@RequestBody String data) {
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);

            String name = (String) object.get("name");
            String genre = (String) object.get("genre");
            String players = (String) object.get("players");
            if (name == null || name.trim().length() == 0) {
                log.error("Missing game");
                JSONObject obj = new JSONObject();
                obj.put("error", "missing game");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).
                        body(obj.toJSONString());
            }
            Genre g;
            if (genre == null) {
                g = Genre.OTHER;
            } else if (genre.equalsIgnoreCase("Shooting")) {
                g = Genre.SHOOTING;
            } else if (genre.equalsIgnoreCase("MOBA")) {
                g = Genre.MOBA;
            } else if(genre.equalsIgnoreCase("RTS")){
                    g = Genre.RTS;
            } else
                g = Genre.OTHER;
            Game game = new Game(name, players, g.getValue());
            new Database().insertNewGame(game);
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
}