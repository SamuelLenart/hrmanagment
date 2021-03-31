package sk.kosickaakademia.lenart.company.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.bson.Document;
import org.bson.Transformer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import sk.kosickaakademia.lenart.company.MySQL.DatabaseMongo;
import sk.kosickaakademia.lenart.company.log.LOG;
import sk.kosickaakademia.lenart.company.MySQL.Database;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.enumerator.Gender;
import sk.kosickaakademia.lenart.company.util.Util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@RestController
public class Controller {
    LOG log = new LOG();

    @PostMapping("/user/new")
    public ResponseEntity<String> insertNewUser(@RequestBody String data) {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);
            String fName = (String)jsonObject.get("fname");
            String lName = (String)jsonObject.get("lname");
            String gender = (String)jsonObject.get("gender");
            int age = Integer.parseInt(String.valueOf(jsonObject.get("age")));
            System.out.println(fName + lName + age);
            if(fName == null || lName == null || fName.trim().equals("") || lName.trim().equals("") || age < 1){
                log.error("Missing name or surname!!!");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong");
            }

            Gender g;
            if(gender==null){
                g = Gender.OTHER;
            }else if(gender.equalsIgnoreCase("male")){
                g = Gender.MALE;
            }else if(gender.equalsIgnoreCase("female")){
                g = Gender.FEMALE;
            }else{
                g = Gender.OTHER;
            }

            User user = new User(fName,lName,age,g.getValue());

            new Database().insertNewUser(user);
            //mongo
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("User has been added");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        List<User> list = new Database().getAllUsers();
        String json = new Util().getJson(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("users/age")
    public ResponseEntity<String> getUsersbyAge(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to) {
        System.out.println(from + " " + to);
        if (from > to) {
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        List<User> list = new Database().getUsersByAge(from, to);
        for(User user: list)
        System.out.println(user);
        String json = new Util().getJson(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> changeAge(@PathVariable Integer id, @RequestBody String body){
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(body);
        }catch (ParseException e){
            e.printStackTrace();
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");
        }

        String data = (String)object.get("newage");
        int newage = Integer.parseInt(data);
        if(newage<1)
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");

        System.out.println(id+" "+newage);
        boolean result =  new Database().changeAge(id, newage);
        int status;
        if (result) status = 200; else status = 404;

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body("{}");
    }

    @GetMapping("/stats")
    public ResponseEntity<String> overview(){
        List<User> list = new Database().getAllUsers();
        String jsonStatistic = new Util().getStatistic(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(jsonStatistic.toString());
    }
    /*@GetMapping("/usersss")
    public ResponseEntity<String>  getAllUsersXML(@RequestParam("type") String xml){
        if(xml == null || (!xml.equals("xml"))){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_XML).body("Something wrong");
        }

        List<User> list = new Database().getAllUsers();

        String xmlFilePath = "resource/xmlfile.xml";
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = (Document) documentBuilder.newDocument();

            // root element
            Element root = document.createElement("users");
            document.append(root);

            // person element
            for(int i = 0; i < list.size(); i++){
                Element person = document.createElement("user");

                root.appendChild(person);
                
                Attr attr = document.createAttribute("id");
                attr.setValue(String.valueOf(list.get(i).getId()));
                person.setAttributeNode(attr);
                
                Element firstName = document.createElement("firstname");
                firstName.appendChild(document.createTextNode(list.get(i).getfName()));
                person.appendChild(firstName);
                
                Element lastname = document.createElement("lastname");
                lastname.appendChild(document.createTextNode(list.get(i).getlName()));
                person.appendChild(lastname);

            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = (Transformer) transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource((Node) document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        BufferedReader reader;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader("resource/xmlfile.xml"));
            line = reader.readLine();
            System.out.println(line);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("+++++");
        System.out.println(line);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_XML).body(line);

    }*/

    @GetMapping("/backend")
    public ResponseEntity<String> getAllUsersMongo(){
        List<User> list = new DatabaseMongo().getAllUsersMongo();
        String json = new Util().getJson(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
    @PostMapping("/hobby/user/{name}")
    public ResponseEntity<String> insertIntoMongoOnePerson(@PathVariable String name, @RequestBody String data){
        if(name==null){
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body("something's wrong i can feel it");
        }
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);
            if(jsonObject == null){
                if(new DatabaseMongo().insertHobby(name,null)){
                    return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("User added");
                }
                return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body("something's wrong i can feel it");
            }
            jsonArray = (JSONArray) jsonObject.get("hobby");
            if(new DatabaseMongo().insertHobby(name,jsonArray)){
                return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("User added");
            }
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body("something's wrong i can feel it");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body("something's wrong i can feel it");
    }

    @GetMapping("/hobby/users")
    public ResponseEntity<String> showAllUsers(){
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(new DatabaseMongo().gainAllUsers().toJSONString());
    }

    @GetMapping("/hobby/users/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("_id") String id){
        boolean result = new DatabaseMongo().deleteUser(id);
        if(result){
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("deleted");
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("something's wrong i can feel it");
    }

    @PutMapping("/hobby/update/users")
    public ResponseEntity<String> update(@RequestHeader("name") String name, @RequestBody String data){
        System.out.println(name + " " + data);
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);
            JSONArray jsonArray = (JSONArray) jsonObject.get("hobby");
            if(new DatabaseMongo().updateUser(name,jsonArray)){
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("Updated");
            }else{
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("something's wrong i can feel it");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("something's wrong i can feel it");
    }
}
