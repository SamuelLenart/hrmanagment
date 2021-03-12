package sk.kosickaakademia.lenart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class JokesOnU {
    String joke1 = "When I was in Japan I was asked by a woman on the train, What's black and white and red all over? Wow I replied. You speak English? Just a riddle";
    String joke2 = "What do you call a nervous jedi? Panikin Skywalker";
    String joke3 = "My husband and son getting competitive while playing games. Husband said: I fucked your mom. To which the son replied: I have been deeper inside her than you'll ever be";
    List<String> list = new ArrayList<>();

    public JokesOnU() {
        list.add(joke1);
        list.add(joke2);
        list.add(joke3);
    }

    @GetMapping("/jokes")
    public ResponseEntity<String> getJokes() {
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String string : list)
            jsonArray.add(string);
        object.put("jokes", jsonArray);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }

    @GetMapping("/joke/{id}")
    public ResponseEntity<String> getJokeById(@PathVariable Integer id) {
        JSONObject object = new JSONObject();
        int status;
        if (id < 0 || id > list.size()) {
            object.put("error", "Incorrect id");
            status = 404;
        } else {
            object.put("joke", list.get(id - 1));
            status = 200;
        }

        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }

    @GetMapping("/joke")
    public ResponseEntity<String> getRandomJoke() {
        JSONObject object = new JSONObject();
        int status;
        if (list.size() == 0) {
            object.put("error", "No jokes found");
            status = 404;
        } else {
            Random random = new Random();
            int index = random.nextInt(list.size());
            object.put("joke", list.get(index));
            status = 200;
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }

    @PostMapping("/joke/new")
    public ResponseEntity<String> newJoke(@RequestBody String data) {
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);
            String newJoke = (String) object.get("joke");
            if(newJoke==null)
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");

            list.add("joke");
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("{}");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");
    }
}