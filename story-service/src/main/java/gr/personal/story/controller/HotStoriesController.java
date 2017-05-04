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
@RequestMapping("/hotStories")
public class HotStoriesController {

    @Autowired
    private StoryService storyService;

    //TODO: return generic result(?)
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<StoryImpl> getHotStoriesOfUser(@PathVariable String userId) throws ParseException {
        return storyService.getHotStoriesOfUser(userId);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<StoryImpl> getHotStoriesOfLocation(@RequestParam Geolocation geolocation) throws ParseException {
        return storyService.getHotStoriesOfLocation(geolocation);
    }

    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<StoryImpl> getHotStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        return storyService.getHotStoriesOfGroup(groupId);
    }
}
