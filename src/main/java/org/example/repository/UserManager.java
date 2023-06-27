package org.example.repository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.model.User.User;

public class UserManager implements IUserManager {
    private MongoCollection<Document> userCollection;

    public UserManager(MongoCollection<Document> userCollection) {
        this.userCollection = userCollection;
    }
    
    @Override
    public InsertOneResult addOneUser(Document userDocument) {
        return userCollection.insertOne(userDocument);
    }

    @Override
    public Document getUserDocumentById(ObjectId userId) {
        return userCollection.find(new Document("_id", userId)).first();
    }

    @Override
    public UpdateResult updateUserById(ObjectId userId, Document newEntity) {
        return userCollection.updateOne(new Document("_id", userId), new Document("$set", newEntity));
    }


}
