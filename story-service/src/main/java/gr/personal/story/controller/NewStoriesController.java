package gr.personal.story.controller;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.NewStoriesService;
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
    private NewStoriesService newStoriesService;

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Story> getNewStoriesOfUser(@PathVariable String userId) throws ParseException {
        return newStoriesService.getNewStoriesOfUser(userId);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<Story> getNewStoriesOfLocation(Geolocation geolocation) throws ParseException {
        return newStoriesService.getNewStoriesOfLocation(geolocation);
    }

    @RequestMapping(path = "/group/{groupId}", method = RequestMethod.GET)
    public List<Story> getNewStoriesOfGroup(@PathVariable String groupId) throws ParseException {
        return newStoriesService.getNewStoriesOfGroup(groupId);
    }

}
