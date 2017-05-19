package gr.personal.user.controller;

import gr.personal.user.aop.LogTimeExecution;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.service.AdministrativeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String createUser(@RequestBody UserRequest user){
        logger.debug("Entering createUser (username = {})",user.getUsername());
        String result = administrativeService.createUser(user);
        logger.debug("Exiting createUser (username ={}, result={})",user.getUsername(), result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestBody UserRequest user){
        logger.debug("Entering updateUser (username = {})",user.getUsername());
        String result =  administrativeService.updateUser(user);
        logger.debug("Exiting updateUser (username ={}, result={})",user.getUsername(), result);
        return result;

    }

    @LogTimeExecution
    @RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String username){
        logger.debug("Entering deleteUser (username = {})",username);
        String result = administrativeService.deleteUser(username);
        logger.debug("Exiting deleteUser (username ={}, result={})",username, result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveUser/{username}", method = RequestMethod.GET)
    public User retrieveUser(@PathVariable String username){
        logger.debug("Entering retrieveUser (username = {})",username);
        User user = administrativeService.retrieveUser(username);
        logger.debug("Exiting retrieveUser (username ={})",username);
        return user;
    }

    @LogTimeExecution
    @RequestMapping(value = "/addFollower/{username}/{followedUsername}", method = RequestMethod.POST)
    public String addFollower(@PathVariable String username, @PathVariable String followedUsername){
        logger.debug("Entering addFollower (username = {})",username);
        String result = administrativeService.addFollower(username, followedUsername);
        logger.debug("Exiting addFollower (username ={}, result={})",username, result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/removeFollower/{username}/{followerUsername}", method = RequestMethod.DELETE)
    public String removeFollower(@PathVariable String username, @PathVariable String followerUsername){
        logger.debug("Entering removeFollower (username = {}, followerUsername={})",username,followerUsername);
        String result = administrativeService.removeFollower(username, followerUsername);
        logger.debug("Exiting removeFollower (username ={}, result={})",username, result);
        return result;
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveFollowers/{username}", method = RequestMethod.GET)
    public List<User> retrieveFollowers(@PathVariable String username){
        logger.debug("Entering retrieveFollowers (username = {})",username);
        List<User> users = administrativeService.retrieveFollowers(username);
        logger.debug("Exiting retrieveFollowers (username ={}, numOfFollowers={})",username, users.size());
        return users;
    }

}
