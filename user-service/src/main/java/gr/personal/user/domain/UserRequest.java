package gr.personal.user.domain;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
public class UserRequest {

    private String username;
    private String name;
    private String surname;
    private Gender gender;

    public UserRequest(String username, String name, String surname, Gender gender) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
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
}
