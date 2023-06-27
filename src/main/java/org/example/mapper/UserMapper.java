package org.example.mapper;

import org.bson.Document;
import org.example.model.Activity.Activity;
import org.example.model.User.User;
import java.util.ArrayList;

public class UserMapper {
    public static Document UserToDocument(User user){
        return new Document()
                .append("name", user.getName())
                .append("surname", user.getSurname())
                .append("birthdate", user.getBirthdate())
                .append("sex", user.getSex());
    }

    public static User DocumentToUser(Document userDocument){
        User newUser = new User();
        newUser.setObjectId(userDocument.getObjectId("_id"));
        newUser.setName(userDocument.getString("name"));
        newUser.setSurname(userDocument.getString("surname"));
        newUser.setBirthdate(userDocument.getDate("birthdate"));
        newUser.setSex(userDocument.getString("sex"));
        newUser.setActivityList((ArrayList<Activity>) userDocument.getList("activities", Activity.class));

        return newUser;
    }
}
