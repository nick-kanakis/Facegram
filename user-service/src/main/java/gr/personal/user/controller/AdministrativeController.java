package gr.personal.user.controller;

import gr.personal.user.aop.LogTimeExecution;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.service.AdministrativeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
@RestController
@RequestMapping("/administrative")
public class AdministrativeController {

    private Logger logger = LoggerFactory.getLogger(AdministrativeController.class);

    @Autowired
    AdministrativeService administrativeService;

    @LogTimeExecution
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@Valid @RequestBody UserRequest user) {
        logger.debug("Entering createUser (username = {})",user.getUsername());
        String result = administrativeService.createUser(user);
        logger.debug("Exiting createUser (username ={}, result={})",user.getUsername(), result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(Principal principal, @Valid @RequestBody UserRequest user){
        logger.debug("Entering updateUser (username = {})",principal.getName());
        if(!checkUsername(principal, user))
            throw new UnauthorizedUserException("Wrong Username");
        String result =  administrativeService.updateUser(user);
        logger.debug("Exiting updateUser (username ={}, result={})", principal.getName(), result);
        return result;

    }

    @LogTimeExecution
    @RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable String username){
        logger.debug("Entering deleteUser (username = {})",username);
        String result = administrativeService.deleteUser(username);
        logger.debug("Exiting deleteUser (username ={}, result={})",username, result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveUser/{username}", method = RequestMethod.GET)
    @ResponseBody
    public User retrieveUser(@PathVariable String username){
        logger.debug("Entering retrieveUser (username = {})",username);
        User user = administrativeService.retrieveUser(username);
        logger.debug("Exiting retrieveUser (username ={})",username);
        return user;
    }

    @LogTimeExecution
    @RequestMapping(value = "/addFollowing/{followingUsername}", method = RequestMethod.POST)
    @ResponseBody
    public String addFollowing(Principal principal, @PathVariable String followingUsername){
        logger.debug("Entering addFollowing (username = {})",principal.getName());
        String result = administrativeService.addFollowing(principal.getName(), followingUsername);
        logger.debug("Exiting addFollowing (username ={}, result={})",principal.getName(), result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/removeFollowing/{followingUsername}", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeFollowing(Principal principal, @PathVariable String followingUsername){
        logger.debug("Entering removeFollowing (username = {}, followingUsername={})",principal.getName(),followingUsername);
        String result = administrativeService.removeFollowing(principal.getName(), followingUsername);
        logger.debug("Exiting removeFollowing (username ={}, result={})",principal.getName(), result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveFollowings/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<User> retrieveFollowings(@PathVariable String username){
        logger.debug("Entering retrieveFollowings (username = {})",username);
        List<User> users = administrativeService.retrieveFollowings(username);
        logger.debug("Exiting retrieveFollowings (username ={}, numOfFollowings={})",username, users.size());
        return users;
    }


    private boolean checkUsername(Principal principal, UserRequest userRequest){
        if(principal.getName().equals(userRequest.getUsername()))
            return true;
        return false;
    }

}
