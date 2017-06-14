package gr.personal.user.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.user.client.AuthClient;
import gr.personal.user.domain.RegistrationUser;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    Logger logger = LoggerFactory.getLogger(AdministrativeServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    AuthClient authClient;

    @Override
    @HystrixCommand(fallbackMethod = "createUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public String createUser(UserRequest userRequest) {
        Assert.notNull(userRequest, "createUser input is null");

        if(userRepository.exists(userRequest.getUsername())){
            logger.warn("User with id={} is already registered.", userRequest.getUsername());
            return "NOK";
        }

        authClient.createUser(generateRegistrationUser(userRequest));

        User user = new User.Builder()
                .gender(userRequest.getGender())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .username(userRequest.getUsername())
                .build();

        userRepository.save(user);

        return "OK";
    }

    private RegistrationUser generateRegistrationUser(UserRequest userRequest) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        return new RegistrationUser(userRequest.getUsername(), userRequest.getPassword(), roles);
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public String updateUser(UserRequest userRequest) {
        Assert.notNull(userRequest, "updateUser input is null");

        //TODO: do it in one operation with query (?)
        User user = userRepository.findByUsername(userRequest.getUsername());

        if(user == null){
            logger.warn("No user with id={} was found.", userRequest.getUsername());
            return "NOK";
        }


        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setGender(userRequest.getGender());

        userRepository.save(user);

        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public String deleteUser(String username) {
        Assert.hasLength(username, "deleteUser input is empty or null");
        authClient.deleteUser(username);
        userRepository.delete(username);
        
        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "retrieveUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public User retrieveUser(String username) {
        Assert.hasLength(username, "retrieveUser input is empty or null");
        return userRepository.findByUsername(username);
    }

    @Override
    @HystrixCommand(fallbackMethod = "addFollowingFallback", ignoreExceptions = IllegalArgumentException.class)
    public String addFollowing(String username, String followingUsername) {
        Assert.hasLength(followingUsername, "addFollowing input is empty");
        User user = userRepository.findByUsername(username);

        if(user == null){
            logger.warn("No user with id={} was found.", username);
            return "NOK";
        }
        if(!userRepository.exists(followingUsername)){
            logger.warn("No user with id={} was found", followingUsername);
            return "NOK";
        }
        for (String followingId:user.getFollowingIds() ) {
            if(followingId.equals(followingUsername)){
                logger.warn("User with id={} is already following {}", username, followingId);
                return "NOK";
            }
        }

        user.getFollowingIds().add(followingUsername);
        userRepository.save(user);
        return "OK";
    }


    @Override
    @HystrixCommand(fallbackMethod = "removeFollowingFallback", ignoreExceptions = IllegalArgumentException.class)
    public String removeFollowing(String username, String followingUsername) {
        Assert.hasLength(username, "removeFollowing input is empty or null");
        Assert.hasLength(followingUsername, "removeFollowing input is empty or null");

        User user = userRepository.findByUsername(username);

        if(user == null){
            logger.warn("No user with id={} was found.", username);
            return "NOK";
        }

        user.getFollowingIds().remove(followingUsername);

        userRepository.save(user);

        return "OK";
    }

    @Override
    @CachePut(cacheNames = "RetrieveFollowings", key = "#username")
    @HystrixCommand(fallbackMethod = "retrieveFollowingsFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<User> retrieveFollowings(String username) {
        Assert.hasLength(username, "retrieveFollowings input is empty or null");

        User user = userRepository.findByUsername(username);

        if(user == null){
            logger.warn("No user with id={} was found.", username);
            return new ArrayList<>();
        }

        Iterable<User> friends = userRepository.findAll(user.getFollowingIds());

        return Lists.newArrayList(friends);
    }

    //TODO move them in different Class

    public String createUserFallback(UserRequest user, Throwable t){

        logger.error("Create User fallback for user: "+ user.getUsername()+ ". Returning empty object", t);

        return "NOK";
    }

    public String updateUserFallback(UserRequest user, Throwable t) {
        logger.error("Update User fallback for user: "+ user.getUsername()+ ". Returning empty object", t);

        return "NOK";
    }

    public String deleteUserFallback(String username, Throwable t) {
        logger.error("Delete User fallback for user: "+ username+ ". Returning empty object", t);

       return "NOK";
    }

    public User retrieveUserFallback(String username, Throwable t) {
        logger.error("Retrieve User fallback for user: "+ username+ ". Returning empty object", t);

        return new User();
    }

    public String addFollowingFallback(String username, String followingUsername, Throwable t) {
        logger.error("Add Following fallback for user: "+ username + ". Returning empty object", t);

        return "NOK";
    }

    public String removeFollowingFallback(String username, String followingUsername, Throwable t) {
        logger.error("Remove following fallback for user: "+ username + ". Returning empty object", t);

        return "NOK";
    }

    public List<User> retrieveFollowingsFallback(String username, Throwable t) {
        logger.error("Retrieve followings fallback for user: "+ username + ". Returning List from Cache", t);

        if (cacheManager.getCache("RetrieveFollowings") != null && cacheManager.getCache("RetrieveFollowings").get(username) != null) {
            return cacheManager.getCache("RetrieveFollowings").get(username, List.class);
        }
        else {
            logger.error("Retrieve followings Fallback for username: "+ username +". Cache is empty.");
            return new ArrayList<>();
        }
    }
}
