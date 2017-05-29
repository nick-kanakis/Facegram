package gr.personal.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

/**
 * Created by Nick Kanakis on 29/5/2017.
 */
public interface AdministrativeService {

    String createUser(UserRequest userRequest);
    String updateUser(UserRequest userRequest);
    String deleteUser(String username);
    User retrieveUser(String username);
    String addFollowing(String username, String followingUsername);
    String removeFollowing(String username, String followingUsername);
    List<User> retrieveFollowings(String username);
}
