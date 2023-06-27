package org.example.provider;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.User.User;

import static org.example.mapper.UserMapper.DocumentToUser;

public class UserProvider implements IUserProvider {
    private MongoCollection<Document> userCollection;

    public UserProvider(MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;
    }
    
    @Override
    public InsertOneResult addOneUser(Document userDocument) {
        return userCollection.insertOne(userDocument);
    }

    @Override
    public User getUserById(ObjectId userId) {
        return DocumentToUser(userCollection.find(new Document("_id", userId)).first());
    }

    @Override
    public UpdateResult updateUserById(ObjectId userId, Document newEntity) {
        return userCollection.updateOne(new Document("_id", userId), new Document("$set", newEntity));
    }


}
