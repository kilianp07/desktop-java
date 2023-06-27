package org.example.repository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.example.model.User.User;

public class UserManager implements IUserManager {
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

    @Override
    public InsertOneResult addOneUser(Document userDocument) {
        return userCollection.insertOne(userDocument);
    }
}
