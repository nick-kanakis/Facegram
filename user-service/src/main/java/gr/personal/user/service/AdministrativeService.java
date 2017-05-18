package gr.personal.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.repository.UserRepository;
import gr.personal.user.util.FakeDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
@Service
public class AdministrativeService {

    Logger logger = LoggerFactory.getLogger(AdministrativeService.class);

    @Autowired
    UserRepository userRepository;

    @HystrixCommand(fallbackMethod = "createUserFallback")
    public String createUser(UserRequest userRequest) {
        Assert.notNull(userRequest, "createUser input is null");

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "updateUserFallback")
    public String updateUser(UserRequest userRequest) {
        Assert.notNull(userRequest, "updateUser input is null");
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "deleteUserFallback")
    public String deleteUser(String userId) {
        Assert.hasLength(userId, "deleteUser input is empty or null");
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "retrieveUserFallback")
    public User retrieveUser(String userId) {
        Assert.hasLength(userId, "retrieveUser input is empty or null");

        return FakeDataGenerator.generateUser();
    }

    @HystrixCommand(fallbackMethod = "addFriendFallback")
    public String addFriend(String userId, UserRequest friend) {
        Assert.notNull(friend, "addFriend input is null");
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "removeFriendFallback")
    public String removeFriend(String userId, String friendId) {
        Assert.hasLength(userId, "removeFriend input is empty or null");
        Assert.hasLength(friendId, "removeFriend input is empty or null");

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "retrieveFriendsFallback")
    public List<User> retrieveFriends(String userId) {
        Assert.hasLength(userId, "retrieveFriends input is empty or null");

        return FakeDataGenerator.generateUsers();
    }

    public String createUserFallback(UserRequest user, Throwable t){

        logger.warn("Create User fallback for user: "+ user.getUsername()+ ". Returning empty object", t);

        return "";
    }

    public String updateUserFallback(UserRequest user, Throwable t) {
        logger.warn("Update User fallback for user: "+ user.getUsername()+ ". Returning empty object", t);

        return "";
    }

    public String deleteUserFallback(String userId, Throwable t) {
        logger.warn("Delete User fallback for user: "+ userId+ ". Returning empty object", t);

        return "";
    }

    private User retrieveUserFallback(String userId, Throwable t) {
        logger.warn("Retrieve User fallback for user: "+ userId+ ". Returning empty object", t);

        return new User();
    }

    private String addFriendFallback(String userId, UserRequest friend, Throwable t) {
        logger.warn("Add Friend fallback for user: "+ userId + ". Returning empty object", t);

        return "";
    }

    private String removeFriendFallback(String userId, String friendId, Throwable t) {
        logger.warn("Retrieve Friend fallback for user: "+ userId + ". Returning empty object", t);

        return "";
    }

    private List<User> retrieveFriendsFallback(String userId, Throwable t) {
        logger.warn("Retrieve Users fallback for user: "+ userId + ". Returning empty List", t);

        return new ArrayList<>();
    }
}
