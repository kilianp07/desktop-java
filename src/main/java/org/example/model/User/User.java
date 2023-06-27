package org.example.model.User;
import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private String name;
    private String surname;
    private Date birthdate;
    private String sex;
    private ArrayList<Activity> activityList;

    private ObjectId objectId;

    public User() {
    }

    public User(String name, String surname, Date birthdate, String sex, ArrayList<Activity> activityList) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.activityList = activityList;
    }

    // Getters and setters for the properties


    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ObjectId getObjectId() {return objectId;}
    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }
}
