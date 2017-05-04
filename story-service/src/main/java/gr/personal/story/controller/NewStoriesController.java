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
@RequestMapping("/newStories")
public class NewStoriesController {

    @Autowired
    private StoryService storyService;
    //TODO: return generic result(?)
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<StoryImpl> getNewStoriesOfUser(@PathVariable String userId) throws ParseException {
        return storyService.getNewStoriesOfUser(userId);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<StoryImpl> getNewStoriesOfLocation(@RequestParam Geolocation geolocation) throws ParseException {
        return storyService.getNewStoriesOfLocation(geolocation);
    }

    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<StoryImpl> getNewStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        return storyService.getNewStoriesOfGroup(groupId);
    }

}
