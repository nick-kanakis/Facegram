package gr.personal.user.service;

import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.util.FakeDataGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
@Service
public class AdministrativeService {
    public String createUser(UserRequest user) {
        return "OK";
    }

    public String updateUser(UserRequest user) {
        return "OK";
    }

    public String deleteUser(String userId) {
        return "OK";
    }

    public User retrieveUser(String userId) {
        return FakeDataGenerator.generateUser();
    }

    public String addFriend(String userId, UserRequest friend) {
        return "OK";
    }

    public String removeFriend(String userId, String friendId) {
        return "OK";
    }

    public List<User> retrieveFriends(String userId) {
        return FakeDataGenerator.generateUsers();
    }
}
