package gr.personal.story.controller;

import gr.personal.story.aop.annotations.LogExecutionTime;
import gr.personal.story.domain.*;
import gr.personal.story.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */

@RestController
@RequestMapping("/story")
public class StoryController {
    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);

    @Autowired
    private StoryService storyService;

    @LogExecutionTime
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public GenericJson createStory(Principal principal, @Valid @RequestBody StoryRequest storyRequest) {
        logger.debug("Entering createStory (storyTitle={})", storyRequest.getTitle());
        String result = storyService.createStory(principal.getName(), storyRequest);
        logger.debug("Exiting createStory (storyTitle={}, result={})", storyRequest.getTitle(),result);
        return new GenericJson(result);
    }

    @LogExecutionTime
    @RequestMapping(path = "/fetch/{storyId}", method = RequestMethod.GET)
    public ResponseEntity<Story> fetchStory(@PathVariable String storyId) {
        logger.debug("Entering fetchStory (storyId={})", storyId);
        Story story = storyService.fetchStory(storyId);
        logger.debug("Exiting fetchStory (storyId={})", storyId);
        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(story);
    }

    @LogExecutionTime
    @RequestMapping(path = "/delete/{storyId}", method = RequestMethod.DELETE)
    public GenericJson deleteStory(Principal principal, @PathVariable String storyId) {
        logger.debug("Entering deleteStory (storyId={})", storyId);
        String result = storyService.deleteStory(storyId, principal.getName());
        logger.debug("Exiting deleteStory (storyId={}, result={})", storyId, result);
        return new GenericJson(result);
    }

    @LogExecutionTime
    @RequestMapping(path = "/like/{storyId}", method = RequestMethod.POST)
    public GenericJson likeStory(@PathVariable String storyId) {
        logger.debug("Entering likeStory (storyId={})", storyId);
        String result = storyService.likeStory(storyId);
        logger.debug("Exiting likeStory (storyId={}, result={})", storyId, result);
        return new GenericJson(result);
    }
    @LogExecutionTime
    @RequestMapping(path = "/unlike/{storyId}", method = RequestMethod.POST)
    public GenericJson unlikeStory(@PathVariable String storyId) {
        logger.debug("Entering unlikeStory (storyId={})", storyId);
        String result = storyService.unlikeStory(storyId);
        logger.debug("Exiting unlikeStory (storyId={}, result={})", storyId, result);
        return new GenericJson(result);
    }

    @LogExecutionTime
    @RequestMapping(path = "/comment/{storyId}", method = RequestMethod.POST)
    public GenericJson createComment(Principal principal, @PathVariable String storyId,@Valid @RequestBody CommentRequest comment) {
        logger.debug("Entering createComment (storyId={})", storyId);
        String result = storyService.createComment(principal.getName(), storyId, comment);
        logger.debug("Exiting createComment (storyId={}, result={})", storyId, result);
        return new GenericJson(result);
    }

    @LogExecutionTime
    @RequestMapping(path = "/uncomment/{commentId}", method = RequestMethod.DELETE)
    public GenericJson deleteComment(Principal principal, @PathVariable String commentId) {
        logger.debug("Entering deleteComment (commentId={})", commentId);
        String result = storyService.deleteComment(commentId, principal.getName());
        logger.debug("Exiting deleteComment (commentId={}, result={})", commentId, result);
        return new GenericJson(result);
    }

    @LogExecutionTime
    @RequestMapping(path = "/fetchComment/{commentId}", method = RequestMethod.GET)
    public ResponseEntity<Comment> fetchComment(@PathVariable String commentId) {
        logger.debug("Entering fetchComment (commentId={})", commentId);
        Comment comment = storyService.fetchComment(commentId);
        logger.debug("Exiting fetchComment (commentId={})", commentId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(comment);
    }

}
