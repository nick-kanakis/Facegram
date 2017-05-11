package gr.personal.user.controller;

import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.service.AdministrativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
@RestController
@RequestMapping("/administrative")
public class AdministrativeController {

    @Autowired
    AdministrativeService administrativeService;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(@RequestBody UserRequest user){
        return administrativeService.createUser(user);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestBody UserRequest user){
        return administrativeService.updateUser(user);
    }

    @RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String userId){
        return administrativeService.deleteUser(userId);
    }

    @RequestMapping(value = "/retrieveUser/{userId}", method = RequestMethod.GET)
    public User retrieveUser(@PathVariable String userId){
        return administrativeService.retrieveUser(userId);
    }

    @RequestMapping(value = "/addFriend/{userId}", method = RequestMethod.POST)
    public String addFriend(@PathVariable String userId, @RequestBody UserRequest friend){
        return administrativeService.addFriend(userId, friend);
    }

    @RequestMapping(value = "/removeFriend/{userId}/{friendId}", method = RequestMethod.DELETE)
    public String removeFriend(@PathVariable String userId, @PathVariable String friendId){
        return administrativeService.removeFriend(userId, friendId);
    }

    @RequestMapping(value = "/retrieveFriends/{userId}", method = RequestMethod.GET)
    public List<User> retrieveFriends(@PathVariable String userId){
        return administrativeService.retrieveFriends(userId);
    }

}
