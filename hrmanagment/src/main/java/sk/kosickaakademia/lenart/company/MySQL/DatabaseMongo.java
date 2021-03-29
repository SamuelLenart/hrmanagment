package sk.kosickaakademia.lenart.company.MySQL;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sk.kosickaakademia.lenart.company.entity.User;
import sk.kosickaakademia.lenart.company.log.LOG;

public class DatabaseMongo {
    LOG log = new LOG();
    MongoClient mc = new MongoClient("localhost", 27017);
    MongoDatabase db = mc.getDatabase("myFirstdb");

    public void insertNewUser(User user){
        Document doc = new Document();
        doc.append("fName", user.getfName());
        doc.append("lName", user.getlName());
        doc.append("age", user.getAge());
        doc.append("gender", user.getGender());
        db.getCollection("users").insertOne(doc);
        log.print("Success");
    }
}
