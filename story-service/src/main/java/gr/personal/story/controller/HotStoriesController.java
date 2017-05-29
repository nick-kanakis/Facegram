package gr.personal.story.controller;

import gr.personal.story.aop.annotations.LogExecutionTime;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.StoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */
@RestController
@RequestMapping("/hotStories")
public class HotStoriesController {

    Logger logger = LoggerFactory.getLogger(HotStoriesController.class);

    @Qualifier("HotStoriesService")
    @Autowired
    private StoriesService storiesService;


    @LogExecutionTime
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getHotStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getStoriesOfUser (userId={})", userId);
        List<Story> hotStoriesOfUser = storiesService.getStoriesOfUser(userId);
        logger.debug("Exiting getStoriesOfUser (userId={}, numberOfStories={})", userId, hotStoriesOfUser.size());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(hotStoriesOfUser);
    }
    @LogExecutionTime
    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getHotStoriesOfLocation( Geolocation geolocation) throws ParseException {
        logger.debug("Entering getStoriesOfLocation (geolocation={})", geolocation);
        List<Story> hotStoriesOfLocation = storiesService.getStoriesOfLocation(geolocation);
        logger.debug("Exiting getStoriesOfLocation (geolocation={}, numberOfStories={})", geolocation, hotStoriesOfLocation.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(hotStoriesOfLocation);
    }
    @LogExecutionTime
    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getHotStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getStoriesOfGroup (groupId={})", groupId);
        List<Story> hotStoriesOfGroup = storiesService.getStoriesOfGroup(groupId);
        logger.debug("Exiting getStoriesOfGroup (groupId={}, numberOfStories={})", groupId, hotStoriesOfGroup.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(hotStoriesOfGroup);
    }
}
