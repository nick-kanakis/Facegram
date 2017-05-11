package gr.personal.user.controller;

import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import gr.personal.user.service.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
@RestController
@RequestMapping("/homepage")
public class HomepageController {

    @Autowired
    HomepageService homepageService;

    @RequestMapping(value = "/retrieveTopStories/{userId}", method = RequestMethod.GET)
    public List<Story> retrieveTopStories(@PathVariable String userId, Geolocation geolocation){
        return homepageService.retrieveTopStories(userId, geolocation);
    }

    @RequestMapping(value = "/retrieveHotStories/{userId}", method = RequestMethod.GET)
    public List<Story> retrieveHotStories(@PathVariable String userId, @RequestParam Geolocation geolocation){
        return homepageService.retrieveHotStories(userId, geolocation);
    }

    @RequestMapping(value = "/retrieveNewStories/{userId}", method = RequestMethod.GET)
    public List<Story> retrieveNewStories(@PathVariable String userId, @RequestParam Geolocation geolocation){
        return homepageService.retrieveNewStories(userId, geolocation);
    }
}
