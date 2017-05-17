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
        logger.debug("Entering createUser (userId = {})",user.getId());
        String result = administrativeService.createUser(user);
        logger.debug("Exiting createUser (userId ={}, result={})",user.getId(), result);
        return result;
    }
    @LogTimeExecution
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestBody UserRequest user){
        logger.debug("Entering updateUser (userId = {})",user.getId());
        String result =  administrativeService.updateUser(user);
        logger.debug("Exiting updateUser (userId ={}, result={})",user.getId(), result);
        return result;

    }
    @LogTimeExecution
    @RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String userId){
        logger.debug("Entering deleteUser (userId = {})",userId);
        String result = administrativeService.deleteUser(userId);
        logger.debug("Exiting deleteUser (userId ={}, result={})",userId, result);
        return result;
    }
    @LogTimeExecution
    @RequestMapping(value = "/retrieveUser/{userId}", method = RequestMethod.GET)
    public User retrieveUser(@PathVariable String userId){
        logger.debug("Entering retrieveUser (userId = {})",userId);
        User user = administrativeService.retrieveUser(userId);
        logger.debug("Exiting retrieveUser (userId ={})",userId);
        return user;
    }
    @LogTimeExecution
    @RequestMapping(value = "/addFriend/{userId}", method = RequestMethod.POST)
    public String addFriend(@PathVariable String userId, @RequestBody UserRequest friend){
        logger.debug("Entering addFriend (userId = {})",userId);
        String result = administrativeService.addFriend(userId, friend);
        logger.debug("Exiting addFriend (userId ={}, result={})",userId, result);
        return result;
    }
    @LogTimeExecution
    @RequestMapping(value = "/removeFriend/{userId}/{friendId}", method = RequestMethod.DELETE)
    public String removeFriend(@PathVariable String userId, @PathVariable String friendId){
        logger.debug("Entering removeFriend (userId = {}, friendId={})",userId,friendId);
        String result = administrativeService.removeFriend(userId, friendId);
        logger.debug("Exiting removeFriend (userId ={}, result={})",userId, result);
        return result;
    }
    @LogTimeExecution
    @RequestMapping(value = "/retrieveFriends/{userId}", method = RequestMethod.GET)
    public List<User> retrieveFriends(@PathVariable String userId){
        logger.debug("Entering retrieveFriends (userId = {})",userId);
        List<User> users = administrativeService.retrieveFriends(userId);
        logger.debug("Exiting retrieveFriends (userId ={}, numOfUsers={})",userId, users.size());
        return users;
    }

}
