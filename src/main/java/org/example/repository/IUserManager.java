package org.example.repository;

import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public interface IUserManager {
    InsertOneResult addOneUser(Document userDocument);
}
