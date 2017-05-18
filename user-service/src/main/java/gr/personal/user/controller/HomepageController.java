package gr.personal.user.controller;

import gr.personal.user.aop.LogTimeExecution;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import gr.personal.user.service.HomepageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
@RestController
@RequestMapping("/homepage")
public class HomepageController {

    private Logger logger = LoggerFactory.getLogger(HomepageController.class);

    @Autowired
    HomepageService homepageService;

    @LogTimeExecution
    @RequestMapping(value = "/retrieveTopStories/{userId}", method = RequestMethod.GET)
    //In order to map Geolocation for GET command do not use @RequestParam
    public List<Story> retrieveTopStories(@PathVariable String userId, Geolocation geolocation){
        logger.debug("Entering retrieveTopStories (userId = {}, geolocation ={})", userId, geolocation);
        List<Story> stories = homepageService.retrieveTopStories(userId, geolocation);
        logger.debug("Exiting retrieveTopStories (userId = {}, numberOfStories={})", userId, stories.size());
        return stories;
    }
    @LogTimeExecution
    @RequestMapping(value = "/retrieveHotStories/{userId}", method = RequestMethod.GET)
    public List<Story> retrieveHotStories(@PathVariable String userId,  Geolocation geolocation){
        logger.debug("Entering retrieveHotStories (userId = {}, geolocation ={})", userId, geolocation);
        List<Story> stories = homepageService.retrieveHotStories(userId, geolocation);
        logger.debug("Exiting retrieveHotStories (userId = {}, numberOfStories={})", userId, stories.size());
        return stories;
    }
    @LogTimeExecution
    @RequestMapping(value = "/retrieveNewStories/{userId}", method = RequestMethod.GET)
    public List<Story> retrieveNewStories(@PathVariable String userId, Geolocation geolocation){
        logger.debug("Entering retrieveNewStories (userId = {}, geolocation ={})", userId, geolocation);
        List<Story> stories = homepageService.retrieveNewStories(userId, geolocation);
        logger.debug("Exiting retrieveNewStories (userId = {}, numberOfStories={})", userId, stories.size());
        return stories;
    }
}
