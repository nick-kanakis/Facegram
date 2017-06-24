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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */
@RestController
@RequestMapping("/topStories")
public class TopStoriesController {

    private static final Logger logger = LoggerFactory.getLogger(TopStoriesController.class);

    @Autowired
    @Qualifier("TopStoriesService")
    private StoriesService storiesService;

    @LogExecutionTime
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<List<Story>> getTopStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getTopStoriesOfUser (userId={})", userId);
        List<Story> topStoriesOfUser = storiesService.getStoriesOfUser(userId);
        logger.debug("Exiting getTopStoriesOfUser (userId={}, numOfStories={})", userId, topStoriesOfUser.size());
        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(topStoriesOfUser);
    }

    @LogExecutionTime
    @RequestMapping(path = "/location", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<List<Story>> getTopStoriesOfLocation(Geolocation geolocation) throws ParseException {
        logger.debug("Entering getTopStoriesOfLocation (geolocation={})", geolocation);
        List<Story> topStoriesOfLocation = storiesService.getStoriesOfLocation(geolocation);
        logger.debug("Exiting getTopStoriesOfLocation (geolocation={}, numOfStories={})", geolocation, topStoriesOfLocation.size());
        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(topStoriesOfLocation);
    }

    @LogExecutionTime
    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<List<Story>> getTopStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getTopStoriesOfGroup (groupId={})", groupId);
        List<Story> topStoriesOfGroup = storiesService.getStoriesOfGroup(groupId);
        logger.debug("Exiting getTopStoriesOfGroup (groupId={}), numOfStories={}", groupId, topStoriesOfGroup);
        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(topStoriesOfGroup);
    }
}
