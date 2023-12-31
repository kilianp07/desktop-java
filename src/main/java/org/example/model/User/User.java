package org.example.model.User;
import org.bson.types.ObjectId;
import org.example.model.Activity.Activity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String name;
    private String surname;
    private Date birthdate;
    private String sex;
    private List<Activity> activities;


    private ObjectId objectId;

    public User() {
    }

    public User(String name, String surname, Date birthdate, String sex) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.activities = new ArrayList<>();
    }
    public User(ObjectId objectId, String name, String surname, Date birthdate, String sex) {
        this.name = name;
        this.objectId = objectId;
        this.surname = surname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.activities = new ArrayList<>();
    }

    public User(String name, String surname, Date birthdate, String sex, ArrayList<Activity> activities) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.activities = activities;
    }
    public User(ObjectId objectId, String name, String surname, Date birthdate, String sex, ArrayList<Activity> activities) {
        this.name = name;
        this.objectId = objectId;
        this.surname = surname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.activities = activities;
    }

    // Getters and setters for the properties

    public void setObjectId(ObjectId id) {
        this.objectId = id;
    }

    public ObjectId getObjectId() {
        return objectId;
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

    public List<Activity> getActivityList() {
        return activities;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activities = activityList;
    }
}