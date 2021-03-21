package sk.kosickaakademia.lenart;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    Log log = new Log();
    @PostMapping("games/add")
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
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).
                body(null);
    }
    @GetMapping("games")
    public ResponseEntity<String> ShowAllGames(@RequestBody String data){
        List<Game> list = new Database().showAllGames();
        String json = new Game().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
    @PutMapping("/games/{id}")
    public ResponseEntity<String> changeGame(@PathVariable int id,@RequestBody String body) {
        JSONObject o=new JSONObject();
        try {
            o= (JSONObject) new JSONParser().parse(body);
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }catch(ParseException e) {
            e.printStackTrace();
        }
        String data=String.valueOf(o.get("newGame"));
        System.out.println("data:"+data);
        if(data.equalsIgnoreCase("null")){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        String newGame = String.valueOf(data);
        if(newGame==null){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        boolean result = new Database().changeGame(id,newGame);
        if(result){
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("");
        }else{
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body("");
        }
    }
    @RequestMapping(value="/game/{id}",method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteGame(@PathVariable int id){
        if (database.getGameById(id)==null){
            JSONObject object=new JSONObject();
            object.put("ERROR","Game not found");
            log.error("Game not found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
        }
        database.deleteGame(id);
        log.print("Game deleted");
        return ResponseEntity.status(204).contentType(MediaType.APPLICATION_JSON).body(null);
    }
}