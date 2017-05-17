package gr.personal.story.controller;

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

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Story> getTopStoriesOfUser(@PathVariable String userId) throws ParseException {
        logger.debug("Entering getTopStoriesOfUser (userId={})", userId);
        return topStoriesService.getTopStoriesOfUser(userId);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<Story> getTopStoriesOfLocation(Geolocation geolocation) throws ParseException {
        logger.debug("Entering getTopStoriesOfLocation (geolocation={})", geolocation);
        return topStoriesService.getTopStoriesOfLocation(geolocation);
    }

    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<Story> getTopStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getTopStoriesOfGroup (groupId={})", groupId);
        return topStoriesService.getTopStoriesOfGroup(groupId);
    }
}
