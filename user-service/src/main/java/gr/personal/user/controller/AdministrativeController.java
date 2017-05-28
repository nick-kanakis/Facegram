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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest user){
        logger.debug("Entering createUser (username = {})",user.getUsername());
        String result = administrativeService.createUser(user);
        logger.debug("Exiting createUser (username ={}, result={})",user.getUsername(), result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(result);
    }

    @LogTimeExecution
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequest user){
        logger.debug("Entering updateUser (username = {})",user.getUsername());
        String result =  administrativeService.updateUser(user);
        logger.debug("Exiting updateUser (username ={}, result={})",user.getUsername(), result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(result);

    }

    @LogTimeExecution
    @RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        logger.debug("Entering deleteUser (username = {})",username);
        String result = administrativeService.deleteUser(username);
        logger.debug("Exiting deleteUser (username ={}, result={})",username, result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(result);
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveUser/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> retrieveUser(@PathVariable String username){
        logger.debug("Entering retrieveUser (username = {})",username);
        User user = administrativeService.retrieveUser(username);
        logger.debug("Exiting retrieveUser (username ={})",username);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(user);
    }

    @LogTimeExecution
    @RequestMapping(value = "/addFollowing/{username}/{followingUsername}", method = RequestMethod.POST)
    public ResponseEntity<String> addFollowing(@PathVariable String username, @PathVariable String followingUsername){
        logger.debug("Entering addFollowing (username = {})",username);
        String result = administrativeService.addFollowing(username, followingUsername);
        logger.debug("Exiting addFollowing (username ={}, result={})",username, result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(result);
    }

    @LogTimeExecution
    @RequestMapping(value = "/removeFollowing/{username}/{followingUsername}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeFollowing(@PathVariable String username, @PathVariable String followingUsername){
        logger.debug("Entering removeFollowing (username = {}, followingUsername={})",username,followingUsername);
        String result = administrativeService.removeFollowing(username, followingUsername);
        logger.debug("Exiting removeFollowing (username ={}, result={})",username, result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(result);
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveFollowings/{username}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> retrieveFollowings(@PathVariable String username){
        logger.debug("Entering retrieveFollowings (username = {})",username);
        List<User> users = administrativeService.retrieveFollowings(username);
        logger.debug("Exiting retrieveFollowings (username ={}, numOfFollowings={})",username, users.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(users);
    }

}
