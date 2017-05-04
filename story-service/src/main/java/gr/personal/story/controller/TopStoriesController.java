package gr.personal.story.controller;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.StoryImpl;
import gr.personal.story.service.StoryService;
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

    @Autowired
    private StoryService storyService;

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<StoryImpl> getTopStoriesOfUser(@PathVariable String userId) throws ParseException {
        return storyService.getTopStoriesOfUser(userId);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<StoryImpl> getTopStoriesOfLocation(@RequestParam Geolocation geolocation) throws ParseException {
        return storyService.getTopStoriesOfLocation(geolocation);
    }

    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<StoryImpl> getTopStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        return storyService.getTopStoriesOfGroup(groupId);
    }
}
