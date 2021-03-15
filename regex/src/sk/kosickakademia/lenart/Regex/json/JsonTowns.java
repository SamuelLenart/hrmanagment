package sk.kosickakademia.lenart.Regex.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonTowns {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try {
            Reader reader = new FileReader("resource/towns");
            JSONObject object = (JSONObject) parser.parse(reader);
            JSONArray data = (JSONArray) object.get("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject temp = (JSONObject) data.get(i);
                String city = (String) temp.get("city");
                System.out.println("City: " + city);
                String region = (String) temp.get("region");
                System.out.println("Region: " + region);
                String wikiDataID = (String) temp.get("wikiDataId");
                System.out.println("Wiki Data: " + wikiDataID);
                double latitude = (double) temp.get("latitude");
                double longitude = (double) temp.get("longitude");
                System.out.println("Latitude : " + latitude + "\n" + "Longitude: " + longitude);
                System.out.println("-----------------------------------------");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}