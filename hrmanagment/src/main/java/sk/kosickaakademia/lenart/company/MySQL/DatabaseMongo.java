package sk.kosickaakademia.lenart.company.MySQL;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.log.LOG;

import java.util.ArrayList;
import java.util.List;

public class DatabaseMongo {
    LOG log = new LOG();
    MongoClient mc = new MongoClient("localhost", 27017);
    MongoDatabase db = mc.getDatabase("myFirstdb");

    public void insertNewUser(User user){
        Document doc = new Document();
        doc.append("fname", user.getfName());
        doc.append("lname", user.getlName());
        doc.append("age", user.getAge());
        doc.append("gender", user.getGender());
        db.getCollection("users").insertOne(doc);
        log.print("Success");
    }

    public List<User> getAllUsersMongo(){
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("allUsers");
        MongoCollection<Document> collection = database.getCollection("users")
                .withReadPreference(ReadPreference.primary())
                .withReadConcern(ReadConcern.MAJORITY)
                .withWriteConcern(WriteConcern.MAJORITY);
        List<User> list = new ArrayList<>();
        for(Document document : collection.find()){
            User user = new User((String)document.get("fname"), (String)document.get("lname"), (int)document.get("age"), (int)document.get("gender"));
            list.add(user);
        }
        return list;
    }
    public boolean insertHobby(String name, JSONArray hobby){
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("allUsers");
        if(name == null){
            return false;
        }
        if(hobby == null){
            Document document = new Document();
            document.append("name", name);
            document.append("hobby", new JSONArray());
            database.getCollection("hobbies").insertOne(document);
            System.out.println("Document inserted successfully");
            return true;
        }else{
            Document document = new Document();
            document.append("name", name);
            document.append("hobby", hobby);
            database.getCollection("hobbies").insertOne(document);
            System.out.println("Document inserted successfully");
        }
        return true;
    }

    public JSONObject gainAllUsers(){
        JSONObject js = new JSONObject();
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("allUsers");
        MongoCollection<Document> collection = database.getCollection("hobbies")
                .withReadPreference(ReadPreference.primary())
                .withReadConcern(ReadConcern.MAJORITY)
                .withWriteConcern(WriteConcern.MAJORITY);

        int i = 0;
        for(Document document : collection.find()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", document.get("name"));
            jsonObject.put("hobby", document.get("hobby"));
            System.out.println(document.get("_id"));
            js.put(document.get("_id"),jsonObject);
        }
        return js;
    }

    public boolean deleteUser(String hexString){
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("allUsers");
        DeleteResult dr = database.getCollection("hobbies").deleteOne(new Document("_id", new ObjectId(hexString)));
        System.out.println(dr);
        long i = dr.getDeletedCount();
        if(i == 0){
            return false;
        }
        return true;
    }

    public boolean updateUser(String name, JSONArray jsonArray){
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("allUsers");
        MongoCollection<Document> collection = database.getCollection("hobbies");
        UpdateResult updateResult = collection.updateOne(Filters.eq("name", name), Updates.set("hobby", jsonArray));
        System.out.println("Document update successfully...");
        System.out.println(updateResult);
        long i = updateResult.getModifiedCount();
        if(i == 0){
            return false;
        }
        return true;
    }
}
