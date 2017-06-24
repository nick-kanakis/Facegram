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
@RequestMapping("/hotStories")
public class HotStoriesController {

    private static final Logger logger = LoggerFactory.getLogger(HotStoriesController.class);

    @Autowired
    @Qualifier("HotStoriesService")
    private StoriesService storiesService;

    @LogExecutionTime
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<List<Story>> getHotStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getHotStoriesOfUser (userId={})", userId);
        List<Story> hotStoriesOfUser = storiesService.getStoriesOfUser(userId);
        logger.debug("Exiting getHotStoriesOfUser (userId={}, numberOfStories={})", userId, hotStoriesOfUser.size());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(hotStoriesOfUser);
    }

    @LogExecutionTime
    @RequestMapping(path = "/location", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<List<Story>> getHotStoriesOfLocation(Geolocation geolocation) throws ParseException {
        logger.debug("Entering getHotStoriesOfLocation (geolocation={})", geolocation);
        List<Story> hotStoriesOfLocation = storiesService.getStoriesOfLocation(geolocation);
        logger.debug("Exiting getHotStoriesOfLocation (geolocation={}, numberOfStories={})", geolocation, hotStoriesOfLocation.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(hotStoriesOfLocation);
    }

    @LogExecutionTime
    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<List<Story>> getHotStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getHotStoriesOfGroup (groupId={})", groupId);
        List<Story> hotStoriesOfGroup = storiesService.getStoriesOfGroup(groupId);
        logger.debug("Exiting getHotStoriesOfGroup (groupId={}, numberOfStories={})", groupId, hotStoriesOfGroup.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(hotStoriesOfGroup);
    }
}
