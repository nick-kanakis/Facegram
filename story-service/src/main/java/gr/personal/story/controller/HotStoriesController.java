package gr.personal.story.controller;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.HotStoriesService;
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
@RequestMapping("/hotStories")
public class HotStoriesController {

    Logger logger = LoggerFactory.getLogger(HotStoriesController.class);

    @Autowired
    private HotStoriesService hotStoriesService;

    //TODO: return generic result(?)
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Story> getHotStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getHotStoriesOfUser (userId={})", userId);
        List<Story> hotStoriesOfUser = hotStoriesService.getHotStoriesOfUser(userId);
        logger.debug("Exiting getHotStoriesOfUser (userId={}, numberOfStories={})", userId, hotStoriesOfUser.size());
        return hotStoriesOfUser;
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<Story> getHotStoriesOfLocation( Geolocation geolocation) throws ParseException {
        logger.debug("Entering getHotStoriesOfLocation (geolocation={})", geolocation);
        List<Story> hotStoriesOfLocation = hotStoriesService.getHotStoriesOfLocation(geolocation);
        logger.debug("Exiting getHotStoriesOfLocation (geolocation={}, numberOfStories={})", geolocation, hotStoriesOfLocation.size());
        return hotStoriesOfLocation;
    }

    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<Story> getHotStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getHotStoriesOfGroup (groupId={})", groupId);
        List<Story> hotStoriesOfGroup = hotStoriesService.getHotStoriesOfGroup(groupId);
        logger.debug("Exiting getHotStoriesOfGroup (groupId={}, numberOfStories={})", groupId, hotStoriesOfGroup.size());
        return hotStoriesOfGroup;
    }
}
