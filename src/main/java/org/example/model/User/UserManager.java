package org.example.model.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
public class UserManager {
    private MongoCollection<Document> userCollection;

    public UserManager(MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;

    }

    public void register(User user) {
        Document userDocument = new Document("name", user.getName())
                .append("surname", user.getSurname())
                .append("birthdate", user.getBirthdate())
                .append("sex", user.getSex());

        userCollection.insertOne(userDocument);
        System.out.println("User registered successfully.");
    }
}
