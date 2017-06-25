package gr.personal.user.controller;

import gr.personal.user.aop.annotation.LogTimeExecution;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import gr.personal.user.service.HomepageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by Nick Kanakis on 9/5/2017.
 */
@RestController
@RequestMapping("/homepage")
public class HomepageController {

    private static final Logger logger = LoggerFactory.getLogger(HomepageController.class);
    @Autowired
    HomepageService homepageService;

    @LogTimeExecution
    @RequestMapping(value = "/retrieveTopStories", method = RequestMethod.GET)
    //In order to map Geolocation for GET command do not use @RequestParam
    public ResponseEntity<List<Story>> retrieveTopStories(Principal principal, Geolocation geolocation) {
        logger.debug("Entering retrieveTopStories (username = {}, geolocation ={})", principal.getName(), geolocation);
        List<Story> stories = homepageService.retrieveTopStories(principal.getName(), geolocation);
        logger.debug("Exiting retrieveTopStories (username = {}, numberOfStories={})", principal.getName(), stories.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(stories);
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveHotStories", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> retrieveHotStories(Principal principal, Geolocation geolocation) {
        logger.debug("Entering retrieveHotStories (username = {}, geolocation ={})", principal.getName(), geolocation);
        List<Story> stories = homepageService.retrieveHotStories(principal.getName(), geolocation);
        logger.debug("Exiting retrieveHotStories (username = {}, numberOfStories={})", principal.getName(), stories.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(stories);
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveNewStories", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> retrieveNewStories(Principal principal, Geolocation geolocation) {
        logger.debug("Entering retrieveNewStories (username = {}, geolocation ={})", principal.getName(), geolocation);
        List<Story> stories = homepageService.retrieveNewStories(principal.getName(), geolocation);
        logger.debug("Exiting retrieveNewStories (username = {}, numberOfStories={})", principal.getName(), stories.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(stories);
    }

    @LogTimeExecution
    @RequestMapping(value = "/retrieveMyStories", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> retrieveMyStories(Principal principal) {
        logger.debug("Entering retrieveNewStories (username = {})", principal.getName());
        List<Story> stories = homepageService.retrieveMyStories(principal.getName());
        logger.debug("Exiting retrieveNewStories (username = {}, numberOfStories={})", principal.getName(), stories.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(stories);
    }
}
