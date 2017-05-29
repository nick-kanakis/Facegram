package gr.personal.story.controller;

import gr.personal.story.aop.annotations.LogExecutionTime;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.NewStoriesService;
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
@RequestMapping("/newStories")
public class NewStoriesController {

    Logger logger = LoggerFactory.getLogger(NewStoriesController.class);

    @Qualifier("NewStoriesService")
    @Autowired
    private StoriesService storiesService;

    @LogExecutionTime
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getNewStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getNewStoriesOfUser (userId={})", userId);
        List<Story> newStoriesOfUser = storiesService.getStoriesOfUser(userId);
        logger.debug("Existing getNewStoriesOfUser (userId={}, numberOfStories={})", userId, newStoriesOfUser.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(newStoriesOfUser);
    }
    @LogExecutionTime
    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getNewStoriesOfLocation(Geolocation geolocation) throws ParseException {
        logger.debug("Entering getNewStoriesOfLocation (geolocation={})", geolocation);
        List<Story> newStoriesOfLocation = storiesService.getStoriesOfLocation(geolocation);
        logger.debug("Exiting getNewStoriesOfLocation (geolocation={}, numberOfStories={})", geolocation, newStoriesOfLocation.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(newStoriesOfLocation);
    }
    @LogExecutionTime
    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getNewStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getNewStoriesOfGroup (groupId={})", groupId);
        List<Story> newStoriesOfGroup = storiesService.getStoriesOfGroup(groupId);
        logger.debug("Exiting getNewStoriesOfGroup (groupId={}, numberOfStories={} )", groupId, newStoriesOfGroup.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(newStoriesOfGroup);
    }

}
