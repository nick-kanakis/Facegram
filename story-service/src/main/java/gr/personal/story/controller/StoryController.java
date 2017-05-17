package gr.personal.story.controller;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryRequest;
import gr.personal.story.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
@RestController
@RequestMapping("/story")
public class StoryController {
    Logger logger = LoggerFactory.getLogger(StoryController.class);

    @Autowired
    StoryService storyService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createStory(@RequestBody StoryRequest story) {
        logger.debug("Entering createStory (storyId={})", story.getId());
        return storyService.createStory(story);
    }

    @RequestMapping(path = "/fetch/{storyId}", method = RequestMethod.GET)
    public Story fetchStory(@PathVariable String storyId) {
        logger.debug("Entering fetchStory (storyId={})", storyId);
        return storyService.fetchStory(storyId);
    }

    @RequestMapping(path = "/delete/{storyId}", method = RequestMethod.DELETE)
    public String deleteStory(@PathVariable String storyId) {
        logger.debug("Entering deleteStory (storyId={})", storyId);
        return storyService.deleteStory(storyId);
    }

    @RequestMapping(path = "/like/{storyId}", method = RequestMethod.POST)
    public String likeStory(@PathVariable String storyId) {
        logger.debug("Entering likeStory (storyId={})", storyId);
        return storyService.likeStory(storyId);
    }

    @RequestMapping(path = "/unlike/{storyId}", method = RequestMethod.POST)
    public String unlikeStory(@PathVariable String storyId) {
        logger.debug("Entering unlikeStory (storyId={})", storyId);
        return storyService.unlikeStory(storyId);
    }

    @RequestMapping(path = "/comment/{storyId}", method = RequestMethod.POST)
    public String createComment(@PathVariable String storyId, @RequestBody Comment comment) {
        logger.debug("Entering createComment (storyId={})", storyId);
        return storyService.createComment(storyId, comment);
    }

    @RequestMapping(path = "/uncomment/{commentId}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable String commentId) {
        logger.debug("Entering deleteComment (commentId={})", commentId);
        return storyService.deleteComment(commentId);
    }

    @RequestMapping(path = "/fetchComment/{commentId}", method = RequestMethod.GET)
    public Comment fetchComment(@PathVariable String commentId) {
        logger.debug("Entering fetchComment (commentId={})", commentId);
        return storyService.fetchComment(commentId);
    }

}
