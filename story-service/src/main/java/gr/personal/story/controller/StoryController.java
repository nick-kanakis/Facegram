package gr.personal.story.controller;

import gr.personal.story.aop.annotations.LogExecutionTime;
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

    @LogExecutionTime
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createStory(@RequestBody StoryRequest storyRequest) {
        logger.debug("Entering createStory (storyId={})", storyRequest.getId());
        String result = storyService.createStory(storyRequest);
        logger.debug("Exiting createStory (storyId={}, result={})", storyRequest.getId(),result);
        return result;
    }
    @LogExecutionTime
    @RequestMapping(path = "/fetch/{storyId}", method = RequestMethod.GET)
    public Story fetchStory(@PathVariable String storyId) {
        logger.debug("Entering fetchStory (storyId={})", storyId);
        Story story = storyService.fetchStory(storyId);
        logger.debug("Exiting fetchStory (storyId={})", storyId);
        return story;
    }
    @LogExecutionTime
    @RequestMapping(path = "/delete/{storyId}", method = RequestMethod.DELETE)
    public String deleteStory(@PathVariable String storyId) {
        logger.debug("Entering deleteStory (storyId={})", storyId);
        String result = storyService.deleteStory(storyId);
        logger.debug("Exiting deleteStory (storyId={}, result={})", storyId, result);
        return result;
    }
    @LogExecutionTime
    @RequestMapping(path = "/like/{storyId}", method = RequestMethod.POST)
    public String likeStory(@PathVariable String storyId) {
        logger.debug("Entering likeStory (storyId={})", storyId);
        String result = storyService.likeStory(storyId);
        logger.debug("Exiting likeStory (storyId={}, result={})", storyId, result);
        return result;
    }
    @LogExecutionTime
    @RequestMapping(path = "/unlike/{storyId}", method = RequestMethod.POST)
    public String unlikeStory(@PathVariable String storyId) {
        logger.debug("Entering unlikeStory (storyId={})", storyId);
        String result = storyService.unlikeStory(storyId);
        logger.debug("Exiting unlikeStory (storyId={}, result={})", storyId, result);
        return result;
    }
    @LogExecutionTime
    @RequestMapping(path = "/comment/{storyId}", method = RequestMethod.POST)
    public String createComment(@PathVariable String storyId, @RequestBody Comment comment) {
        logger.debug("Entering createComment (storyId={})", storyId);
        String result = storyService.createComment(storyId, comment);
        logger.debug("Exiting createComment (storyId={}, result={})", storyId, result);
        return result;
    }
    @LogExecutionTime
    @RequestMapping(path = "/uncomment/{commentId}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable String commentId) {
        logger.debug("Entering deleteComment (commentId={})", commentId);
        String result = storyService.deleteComment(commentId);
        logger.debug("Exiting deleteComment (commentId={}, result={})", commentId, result);
        return result;
    }
    @LogExecutionTime
    @RequestMapping(path = "/fetchComment/{commentId}", method = RequestMethod.GET)
    public Comment fetchComment(@PathVariable String commentId) {
        logger.debug("Entering fetchComment (commentId={})", commentId);
        Comment comment = storyService.fetchComment(commentId);
        logger.debug("Exiting fetchComment (commentId={})", commentId);
        return comment;
    }

}
