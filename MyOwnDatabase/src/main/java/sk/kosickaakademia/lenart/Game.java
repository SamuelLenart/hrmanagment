package sk.kosickaakademia.lenart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Game {
    private int id;
    private String name;
    private String genre;
    private int date;
    private int players;

    public Game (int id, String name, String genre, int date, int players){
        this.players = players;
        this.date = date;
        this.genre = genre;
        this.name = name;
        this.id = id;
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

    public int getDate(){
        return date;
    }

    public int getPlayers(){
        return players;
    }

    public Game() {
        this.name = name;
        this.genre = genre;
        this.date = date;
        this.players = players;
        this.id = id;

    }
    public String getJSON(List<Game> list) {
        if (list.isEmpty()) return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime", LocalDate());
        object.put("size", list.size());
        JSONArray jsonArray = new JSONArray();
        for (Game u : list) {
            JSONObject userJson = new JSONObject();
            userJson.put("id", u.getId());
            userJson.put("game", u.getName());
            userJson.put("genre", u.getGenre());
            userJson.put("date", u.getDate());
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
        return dateFormat.format(date);
    }
}
