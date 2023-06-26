package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class UserTest {

    @Test
    public void testUserProperties() {
        // Create a User instance
        String name = "John";
        String surname = "Doe";
        Date birthdate = new Date();
        String sex = "Male";
        User user = new User(name, surname, birthdate, sex);

        // Verify the user's properties
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(surname, user.getSurname());
        Assertions.assertEquals(birthdate, user.getBirthdate());
        Assertions.assertEquals(sex, user.getSex());
    }
}