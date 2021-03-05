package sk.kosickaakademia.lenart.company.util;

import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonValue;
import org.json.simple.JSONObject;
import sk.kosickaakademia.lenart.company.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Util {

    public String getJson(List<User> list){
        if(list.isEmpty()) return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime",getCurrentDateTime());
        object.put("size",list.size());
        JsonArray jsonArray=new JsonArray();
        for(User u : list ) {
        JSONObject userJson = new JSONObject();
        userJson.put("id", u.getId());
        userJson.put("fName", u.getfName());
        userJson.put("lName", u.getlName());
        userJson.put("age", u.getAge());
        userJson.put("gender", u.getGender().toString());   // ? overit ?
        jsonArray.add((JsonValue) userJson);
    }
        object.put("users",jsonArray);

        return object.toJSONString();
        }
        public String getJson (User user){
            if (user == null) return "{}";
            JSONObject object = new JSONObject();
            object.put("datetime", getCurrentDateTime());
            object.put("size", 1);
            JsonArray jsonArray = new JsonArray();
            JSONObject userJson = new JSONObject();
            userJson.put("id", user.getId());
            userJson.put("fName", user.getfName());
            userJson.put("lName", user.getlName());
            userJson.put("age", user.getAge());
            userJson.put("gender", user.getGender().toString());
            jsonArray.add((JsonValue) userJson);
            object.put("users", jsonArray);
            return object.toJSONString();
        }
        public String getCurrentDateTime() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            return null;
        }

        public String normalizeName (String name){
            if(name == null || name.equals(""))
                return "";
            return String.valueOf(Character.toUpperCase(Integer.parseInt(name.charAt(0)+name.substring(1).toLowerCase())));
        }
    }
