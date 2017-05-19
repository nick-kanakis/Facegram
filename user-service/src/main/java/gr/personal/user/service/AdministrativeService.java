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

        if(userRepository.exists(userRequest.getUsername()))
            return "NOK";

        User user = new User.Builder()
                .followers(new ArrayList<>())
                .gender(userRequest.getGender())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .username(userRequest.getSurname())
                .build();

        userRepository.save(user);

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "updateUserFallback")
    public String updateUser(UserRequest userRequest) {
        Assert.notNull(userRequest, "updateUser input is null");

        //TODO: do it in one operation with query (?)
        User user = userRepository.findByUsername(userRequest.getUsername());

        if(user == null)
            return "NOK";

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setGender(userRequest.getGender());

        userRepository.save(user);

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "deleteUserFallback")
    public String deleteUser(String username) {
        Assert.hasLength(username, "deleteUser input is empty or null");
        
        userRepository.delete(username);
        
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "retrieveUserFallback")
    public User retrieveUser(String username) {
        Assert.hasLength(username, "retrieveUser input is empty or null");
        return userRepository.findByUsername(username);
    }

    @HystrixCommand(fallbackMethod = "addFollowerFallback")
    public String addFollower(String username, String followedUsername) {
        Assert.hasLength(followedUsername, "addFollower input is empty");

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "removeFollowerFallback")
    public String removeFollower(String username, String followerUsername) {
        Assert.hasLength(username, "removeFollower input is empty or null");
        Assert.hasLength(followerUsername, "removeFollower input is empty or null");

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "retrieveFollowersFallback")
    public List<User> retrieveFollowers(String username) {
        Assert.hasLength(username, "retrieveFollowers input is empty or null");

        return FakeDataGenerator.generateUsers();
    }

    //TODO move them in different Class

    public String createUserFallback(UserRequest user, Throwable t){

        logger.warn("Create User fallback for user: "+ user.getUsername()+ ". Returning empty object", t);

        return "";
    }

    public String updateUserFallback(UserRequest user, Throwable t) {
        logger.warn("Update User fallback for user: "+ user.getUsername()+ ". Returning empty object", t);

        return "";
    }

    public String deleteUserFallback(String username, Throwable t) {
        logger.warn("Delete User fallback for user: "+ username+ ". Returning empty object", t);

        return "";
    }

    private User retrieveUserFallback(String username, Throwable t) {
        logger.warn("Retrieve User fallback for user: "+ username+ ". Returning empty object", t);

        return new User();
    }

    private String addFollowerFallback(String username, UserRequest followerUsername, Throwable t) {
        logger.warn("Add Follower fallback for user: "+ username + ". Returning empty object", t);

        return "";
    }

    private String removeFollowerFallback(String username, String followerUsername, Throwable t) {
        logger.warn("Remove follower fallback for user: "+ username + ". Returning empty object", t);

        return "";
    }

    private List<User> retrieveFollowersFallback(String username, Throwable t) {
        logger.warn("Retrieve followers fallback for user: "+ username + ". Returning empty List", t);

        return new ArrayList<>();
    }
}
