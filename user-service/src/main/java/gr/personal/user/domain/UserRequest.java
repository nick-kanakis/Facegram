package gr.personal.user.domain;

import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
public class UserRequest {

    private String id;
    private String name;
    private String surname;
    private Gender gender;


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
