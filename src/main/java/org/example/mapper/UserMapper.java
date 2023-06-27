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

    public static User DocumentToUser(Document document){
        User newUser = new User();
        newUser.setName(document.getString("name"));
        newUser.setSurname(document.getString("surname"));
        newUser.setBirthdate(document.getDate("birthdate"));
        newUser.setObjectId(document.getObjectId("_id"));
        newUser.setSex(document.getString("sex"));
        newUser.setActivityList((ArrayList<Activity>) document.getList("activities", Activity.class));
        return newUser;
    }
}
