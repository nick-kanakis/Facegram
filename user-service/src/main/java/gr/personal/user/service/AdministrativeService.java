package gr.personal.user.service;

import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
@Service
public class AdministrativeService {
    public String createUser(UserRequest user) {
        return null;
    }

    public String updateUser(UserRequest user) {
        return null;
    }

    public String deleteUser(String userId) {
        return null;
    }

    public User retrieveUser(String userId) {
        return null;
    }

    public String addFriend(String userId, UserRequest friend) {
        return null;
    }

    public String removeFriend(String userId, String friendId) {
        return null;
    }

    public List<User> retrieveFriends(String userId) {
        return null;
    }
}
