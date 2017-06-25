package gr.personal.user.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.user.client.AuthClient;
import gr.personal.user.client.GroupClient;
import gr.personal.user.domain.RegistrationUser;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.repository.UserRepository;
import gr.personal.user.util.Constants;
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

    private static final Logger logger = LoggerFactory.getLogger(AdministrativeServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private GroupClient groupClient;

    @Override
    @HystrixCommand(fallbackMethod = "createUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public String createUser(UserRequest userRequest) {
        Assert.notNull(userRequest, "createUser input is null");

        if (userRepository.exists(userRequest.getUsername())) {
            logger.warn("User with id={} is already registered.", userRequest.getUsername());
            return Constants.NOK;
        }
        authClient.createUser(generateRegistrationUser(userRequest));

        User user = new User.Builder()
                .gender(userRequest.getGender())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .username(userRequest.getUsername())
                .build();
        userRepository.save(user);

        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public String updateUser(UserRequest userRequest) {
        Assert.notNull(userRequest, "updateUser input is null");

        User user = userRepository.findByUsername(userRequest.getUsername());
        if (user == null) {
            logger.warn("No user with id={} was found.", userRequest.getUsername());
            return Constants.NOK;
        }

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setGender(userRequest.getGender());
        userRepository.save(user);

        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public String deleteUser(String username) {
        Assert.hasLength(username, "deleteUser input is empty or null");

        if (!userRepository.exists(username)) {
            logger.warn("No user with id={} was found.", username);
            return Constants.NOK;
        }

        authClient.deleteUser(username);
        userRepository.delete(username);

        return Constants.OK;
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
        if (user == null) {
            logger.warn("No user with id={} was found.", username);
            return Constants.NOK;
        }
        if (!userRepository.exists(followingUsername)) {
            logger.warn("No user with id={} was found", followingUsername);
            return Constants.NOK;
        }
        if (user.getFollowingIds().contains(username)) {
            logger.warn("User with id={} is already following {}", username, followingUsername);
            return Constants.NOK;
        }

        user.addFollowingId(followingUsername);
        userRepository.save(user);

        return Constants.OK;
    }


    @Override
    @HystrixCommand(fallbackMethod = "removeFollowingFallback", ignoreExceptions = IllegalArgumentException.class)
    public String removeFollowing(String username, String followingUsername) {
        Assert.hasLength(username, "removeFollowing input is empty or null");
        Assert.hasLength(followingUsername, "removeFollowing input is empty or null");

        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("No user with id={} was found.", username);
            return Constants.NOK;
        }

        user.removeFollowingId(followingUsername);
        userRepository.save(user);

        return Constants.OK;
    }

    @Override
    @CachePut(cacheNames = "RetrieveFollowings", key = "#username")
    @HystrixCommand(fallbackMethod = "retrieveFollowingsFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<User> retrieveFollowings(String username) {
        Assert.hasLength(username, "retrieveFollowings input is empty or null");

        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("No user with id={} was found.", username);
            return new ArrayList<>();
        }

        Iterable<User> friends = userRepository.findAll(user.getFollowingIds());
        return Lists.newArrayList(friends);
    }

    @Override
    @HystrixCommand(fallbackMethod = "followGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public String followGroup(String username, String groupId) {
        Assert.hasLength(username, "followGroup input is empty");
        Assert.hasLength(groupId, "followGroup input is empty");

        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("No user with id={} was found.", username);
            return Constants.NOK;
        }
        if (user.getFollowingGroupIds().contains(groupId)) {
            logger.warn("User with id={} already subscribed to group with id ={}.", username, groupId);
            return Constants.NOK;
        }

        String groupResponse = groupClient.follow(groupId);
        if (!Constants.OK.equals(groupResponse)) {
            logger.warn("Group {} subscription of user {} failed", groupId, username);
            return Constants.NOK;
        }

        user.addFollowingGroupId(groupId);
        userRepository.save(user);

        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "unfollowGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public String unFollowGroup(String username, String groupId) {
        Assert.hasLength(username, "unFollowGroup input is empty");
        Assert.hasLength(groupId, "unFollowGroup input is empty");

        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("No user with id={} was found.", username);
            return Constants.NOK;
        }
        if (!user.getFollowingGroupIds().contains(groupId)) {
            logger.warn("User with id={} not subscribed to group with id ={}.", username, groupId);
            return Constants.NOK;
        }

        String groupResponse = groupClient.unFollow(groupId);
        if ((!Constants.OK.equals(groupResponse))) {
            logger.warn("Group {} un-subscription of user {} failed", groupId, username);
            return Constants.NOK;
        }

        user.removeFollowingGroupId(groupId);
        userRepository.save(user);

        return Constants.OK;
    }

    @Override
    @CachePut(cacheNames = "RetrieveGroupIds", key = "#username")
    @HystrixCommand(fallbackMethod = "retrieveGroupIdsFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<String> retrieveGroupIds(String username) {
        Assert.hasLength(username, "retrieveGroupIds input is empty or null");
        if (!userRepository.exists(username)) {
            logger.warn("No user with id={} was found.", username);
            return new ArrayList<>();
        }

        List<String> followingGroupIds = userRepository.getGroupIdsByUsername(username).getFollowingGroupIds();
        return followingGroupIds;
    }

    private RegistrationUser generateRegistrationUser(UserRequest userRequest) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        return new RegistrationUser(userRequest.getUsername(), userRequest.getPassword(), roles);
    }

    /**
     * Hystrix Fallback Classes
     **/

    public String createUserFallback(UserRequest user, Throwable t) {
        logger.error("Create User fallback for user: " + user.getUsername(), t);
        return Constants.NOK;
    }

    public String updateUserFallback(UserRequest user, Throwable t) {
        logger.error("Update User fallback for user: " + user.getUsername(), t);
        return Constants.NOK;
    }

    public String deleteUserFallback(String username, Throwable t) {
        logger.error("Delete User fallback for user: " + username, t);
        return Constants.NOK;
    }

    public User retrieveUserFallback(String username, Throwable t) {
        logger.error("Retrieve User fallback for user: " + username + ". Returning empty object", t);
        return new User();
    }

    public String addFollowingFallback(String username, String followingUsername, Throwable t) {
        logger.error("Add Following fallback for user: " + username, t);
        return Constants.NOK;
    }

    public String removeFollowingFallback(String username, String followingUsername, Throwable t) {
        logger.error("Remove following fallback for user: " + username, t);
        return Constants.NOK;
    }

    public List<User> retrieveFollowingsFallback(String username, Throwable t) {
        logger.error("Retrieve followings fallback for user: " + username + ". Returning List from Cache", t);

        if (cacheManager.getCache("RetrieveFollowings") != null && cacheManager.getCache("RetrieveFollowings").get(username) != null) {
            return cacheManager.getCache("RetrieveFollowings").get(username, List.class);
        } else {
            logger.error("Retrieve followings Fallback for username: " + username + ". Cache is empty.");
            return new ArrayList<>();
        }
    }

    public String followGroupFallback(String username, String groupId, Throwable t) {
        logger.error("Follow group fallback for user: " + username, t);
        return Constants.NOK;
    }

    public String unfollowGroupFallback(String username, String groupId, Throwable t) {
        logger.error("Unfollow group fallback for user: " + username, t);
        return Constants.NOK;
    }

    public List<String> retrieveGroupIdsFallback(String username, Throwable t) {
        logger.error("Retrieve groupIds fallback for user: " + username + ". Returning List from Cache", t);

        if (cacheManager.getCache("RetrieveGroupIds") != null && cacheManager.getCache("RetrieveGroupIds").get(username) != null) {
            return cacheManager.getCache("RetrieveGroupIds").get(username, List.class);
        } else {
            logger.error("Retrieve groupIds Fallback for username: " + username + ". Cache is empty.");
            return new ArrayList<>();
        }
    }

}
