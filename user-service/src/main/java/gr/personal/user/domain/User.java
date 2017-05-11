package gr.personal.user.domain;

import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
public class User {

    private String id;
    private String name;
    private String surname;
    private List<String> friendIDs;
    private List<Story> stories;
    private Gender gender;


    public static class Builder{
        String id;
        String name;
        String surname;
        List<String> friendIDs;
        List<Story> stories;
        Gender gender;

        public Builder id(String id) {
            this.id = id;
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

        public Builder friends(List<String> friends) {
            this.friendIDs = friends;
            return this;
        }

        public Builder stories(List<Story> stories) {
            this.stories = stories;
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
        id = builder.id;
        name = builder.name;
        surname = builder.surname;
        friendIDs = builder.friendIDs;
        stories = builder.stories;
        gender = builder.gender;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getFriendIDs() {
        return friendIDs;
    }

    public void setFriendIDs(List<String> friendIDs) {
        this.friendIDs = friendIDs;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
