package gr.personal.story.controller;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.HotStoriesService;
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
    private HotStoriesService hotStoriesService;

    //TODO: return generic result(?)
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Story> getHotStoriesOfUser(@PathVariable String userId) throws ParseException {
        return hotStoriesService.getHotStoriesOfUser(userId);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<Story> getHotStoriesOfLocation(@RequestParam Geolocation geolocation) throws ParseException {
        return hotStoriesService.getHotStoriesOfLocation(geolocation);
    }

    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<Story> getHotStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        return hotStoriesService.getHotStoriesOfGroup(groupId);
    }
}
