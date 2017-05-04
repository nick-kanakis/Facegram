package gr.personal.story.controller;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryImpl;
import gr.personal.story.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    StoryService storyService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createStory(@RequestParam Story story) {
        return storyService.createStory(story);
    }

    @RequestMapping(path = "/fetch/{storyId}", method = RequestMethod.GET)
    public StoryImpl fetchStory(@PathVariable String storyId) {
        return storyService.fetchStory(storyId);
    }

    @RequestMapping(path = "/delete/{storyId}", method = RequestMethod.DELETE)
    public String deleteStory(@PathVariable String storyId) {
        return storyService.deleteStory(storyId);
    }

    @RequestMapping(path = "/like/{storyId}", method = RequestMethod.POST)
    public String likeStory(@PathVariable String storyId) {
        return storyService.likeStory(storyId);
    }

    @RequestMapping(path = "/unlike/{storyId}", method = RequestMethod.POST)
    public String unlikeStory(@PathVariable String storyId) {
        return storyService.unlikeStory(storyId);
    }

    @RequestMapping(path = "/comment/{storyId}", method = RequestMethod.POST)
    public String createComment(@RequestParam Comment comment) {
        return storyService.createComment(comment);
    }

    @RequestMapping(path = "/uncomment/{commentId}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable String commentId) {
        return storyService.deleteComment(commentId);
    }

    @RequestMapping(path = "/uncomment/{commentId}", method = RequestMethod.DELETE)
    public String fetchComment(@PathVariable String commentId) {
        return storyService.fetchComment(commentId);
    }

}
