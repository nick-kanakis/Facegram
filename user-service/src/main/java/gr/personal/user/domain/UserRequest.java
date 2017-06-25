package gr.personal.user.domain;

import java.io.Serializable;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
public class UserRequest implements Serializable{

    private static final long serialVersionUID = 1L;
    private String username;
    private String name;
    private String surname;
    private String password;
    private Gender gender;

    public UserRequest(String username, String password, String name, String surname, Gender gender) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.password = password;
    }

    public UserRequest() {
    }

    public Gender getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
