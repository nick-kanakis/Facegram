package gr.personal.story.controller;

import gr.personal.story.aop.annotations.LogExecutionTime;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.NewStoriesService;
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
@RequestMapping("/newStories")
public class NewStoriesController {

    Logger logger = LoggerFactory.getLogger(NewStoriesController.class);

    @Autowired
    private NewStoriesService newStoriesService;
    @LogExecutionTime
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Story> getNewStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getNewStoriesOfUser (userId={})", userId);
        List<Story> newStoriesOfUser = newStoriesService.getNewStoriesOfUser(userId);
        logger.debug("Existing getNewStoriesOfUser (userId={}, numberOfStories={})", userId, newStoriesOfUser.size());
        return newStoriesOfUser;
    }
    @LogExecutionTime
    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<Story> getNewStoriesOfLocation(Geolocation geolocation) throws ParseException {
        logger.debug("Entering getNewStoriesOfLocation (geolocation={})", geolocation);
        List<Story> newStoriesOfLocation = newStoriesService.getNewStoriesOfLocation(geolocation);
        logger.debug("Exiting getNewStoriesOfLocation (geolocation={}, numberOfStories={})", geolocation, newStoriesOfLocation.size());
        return newStoriesOfLocation;
    }
    @LogExecutionTime
    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<Story> getNewStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getNewStoriesOfGroup (groupId={})", groupId);
        List<Story> newStoriesOfGroup = newStoriesService.getNewStoriesOfGroup(groupId);
        logger.debug("Exiting getNewStoriesOfGroup (groupId={}, numberOfStories={} )", groupId, newStoriesOfGroup.size());
        return newStoriesOfGroup;
    }

}
