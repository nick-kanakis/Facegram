package gr.personal.story.controller;

import gr.personal.story.aop.annotations.LogExecutionTime;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.TopStoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */
@RestController
@RequestMapping("/topStories")
public class TopStoriesController {

    Logger logger = LoggerFactory.getLogger(TopStoriesController.class);

    @Autowired
    private TopStoriesService topStoriesService;

    @LogExecutionTime
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Story> getTopStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getTopStoriesOfUser (userId={})", userId);
        List<Story> topStoriesOfUser = topStoriesService.getTopStoriesOfUser(userId);
        logger.debug("Exiting getTopStoriesOfUser (userId={}, numOfStories={})", userId, topStoriesOfUser.size());
        return topStoriesOfUser;
    }
    @LogExecutionTime
    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<Story> getTopStoriesOfLocation(Geolocation geolocation) throws ParseException {
        logger.debug("Entering getTopStoriesOfLocation (geolocation={})", geolocation);
        List<Story> topStoriesOfLocation = topStoriesService.getTopStoriesOfLocation(geolocation);
        logger.debug("Exiting getTopStoriesOfLocation (geolocation={}, numOfStories={})", geolocation, topStoriesOfLocation.size());
        return topStoriesOfLocation;
    }
    @LogExecutionTime
    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<Story> getTopStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getTopStoriesOfGroup (groupId={})", groupId);
        List<Story> topStoriesOfGroup = topStoriesService.getTopStoriesOfGroup(groupId);
        logger.debug("Exiting getTopStoriesOfGroup (groupId={}), numOfStories={}", groupId, topStoriesOfGroup);
        return topStoriesOfGroup;
    }
}
