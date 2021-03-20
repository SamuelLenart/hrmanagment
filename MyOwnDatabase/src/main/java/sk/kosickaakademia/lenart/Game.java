package sk.kosickaakademia.lenart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Game {
    private int id;
    private String name;
    private String genre;
    private String players;

    public Game(int id, String name, String genre, String players){
        this(name, genre, players);
        this.id = id;
    }

    public Game(String name, String genre, int players) {
        this.name=name;
        this.genre=genre;
        this.players=players;
    }
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getGenre(){
        return genre;
    }

    public String getPlayers(){
        return players;
    }

    public String getJSON(List<Game> list) {
        if (list.isEmpty())
            return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime", LocalDate());
        object.put("size", list.size());
        JSONArray jsonArray = new JSONArray();
        for (Game u : list) {
            JSONObject userJson = new JSONObject();
            userJson.put("id", u.getId());
            userJson.put("game", u.getName());
            userJson.put("genre", u.getGenre());
            userJson.put("players", u.getPlayers());
            jsonArray.add(userJson);
        }
        object.put("games", jsonArray);
        return object.toString();
    }

    public String LocalDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        return null;
    }

}
