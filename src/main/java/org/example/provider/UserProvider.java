package org.example.provider;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import org.example.model.User.User;

import java.util.ArrayList;
import java.util.Date;

import static org.example.mapper.UserMapper.DocumentToUser;
import static org.example.mapper.UserMapper.UserToDocument;

public class UserProvider implements IUserProvider {
    private MongoCollection<Document> userCollection;

    public UserProvider(MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;
    }
    
    @Override
    public InsertOneResult addOneUser(User user) {
        return userCollection.insertOne(UserToDocument(user));
    }

    @Override
    public User getUserById(ObjectId userId) {
        return DocumentToUser(userCollection.find(new Document("_id", userId)).first());
    }

    @Override
    public UpdateResult updateUserById(ObjectId userId, User newEntity) {
        return userCollection.replaceOne(new Document("_id", userId), UserToDocument(newEntity));
    }

    @Override
    public ArrayList<User> getAllUser() {
        FindIterable<Document> allUserDocuments = userCollection.find();
        ArrayList<User> users = new ArrayList<User>();
        for (Document userDocument : allUserDocuments) {
            users.add(DocumentToUser(userDocument));
        }
        return users;
    }


}
