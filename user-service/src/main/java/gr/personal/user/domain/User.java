package gr.personal.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
@Document(collection = "users")
public class User {

    @Id
    private String username;
    private String name;
    private String surname;
    private List<String> followingIds;
    private Gender gender;

    public User() {

    }


    public static class Builder{
        String username;
        String name;
        String surname;
        List<String> followingIds;
        Gender gender;

        public Builder username(String id) {
            this.username = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder followings(List<String> followingIds) {
            this.followingIds = followingIds;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public User(Builder builder) {
        username = builder.username;
        name = builder.name;
        surname = builder.surname;
        followingIds = builder.followingIds;
        gender = builder.gender;
    }

    public User(String username, Gender gender, String name, String surname, List<String> followingIds) {
        this.username = username;
        this.gender = gender;
        this.name = name;
        this.surname = surname;
        this.followingIds = followingIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<String> getFollowingIds() {
        return followingIds;
    }

    public void setFollowingIds(List<String> followingIds) {
        this.followingIds = followingIds;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
