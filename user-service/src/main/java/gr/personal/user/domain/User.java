package gr.personal.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
    private List<String> followingGroupIds;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<String> getFollowingIds() {
        return followingIds;
    }

    public List<String> getFollowingGroupIds() {return followingGroupIds;}

    public void addFollowingId(String followingId) {
        this.followingIds.add(followingId);
    }

    public Gender getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public static class Builder{
        String username;
        String name;
        String surname;
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
        followingIds = new ArrayList<>();
        followingGroupIds = new ArrayList<>();
        gender = builder.gender;
    }

    //Will be used only to avoid returning null object in case of exception
    public User() {
    }

}
