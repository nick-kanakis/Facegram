package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Comment;
import gr.personal.story.domain.CommentRequest;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryRequest;
import gr.personal.story.repository.StoryRepository;
import gr.personal.story.util.Constants;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class StoryServiceImpl implements StoryService{

    private static final Logger logger = LoggerFactory.getLogger(StoryServiceImpl.class);

    @Autowired
    private StoryRepository storyRepository;

    @Override
    @HystrixCommand(fallbackMethod = "fallbackCreateStory", ignoreExceptions = IllegalArgumentException.class)
    public String createStory(String username, StoryRequest storyRequest) {
        Assert.notNull(storyRequest, "createStory input is null");

        Story story = new Story.Builder()
                .userId(username)
                .geolocation(storyRequest.getGeolocation())
                .title(storyRequest.getTitle())
                .story(storyRequest.getStory())
                .groupId(storyRequest.getGroupId())
                .build();

        storyRepository.save(story);

        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackFetchStory", ignoreExceptions = IllegalArgumentException.class)
    public Story fetchStory(String storyId) {
        Assert.hasLength(storyId, "fetchStory input was null or empty");
        return storyRepository.findById(storyId);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackDeleteStory", ignoreExceptions = IllegalArgumentException.class)
    public String deleteStory(String storyId, String userId) {
        Assert.hasLength(storyId, "deleteStory input was null or empty");

        Story story = storyRepository.findById(storyId);
        if(!userId.equals(story.getUserId()))
            throw new UnauthorizedUserException("Unauthorized user for this action");

        storyRepository.delete(storyId);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackLikeStory", ignoreExceptions = IllegalArgumentException.class)
    public String likeStory(String storyId) {
        Assert.hasLength(storyId, "likeStory input was null or empty");

        Story story = storyRepository.findById(storyId);
        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return Constants.NOK;
        }

        story.like();
        storyRepository.save(story);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackUnlikeStory", ignoreExceptions = IllegalArgumentException.class)
    public String unlikeStory(String storyId) {
        Assert.hasLength(storyId, "unlikeStory input was null or empty");

        Story story = storyRepository.findById(storyId);
        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return Constants.NOK;
        }

        story.unlike();
        storyRepository.save(story);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackCreateComment", ignoreExceptions = IllegalArgumentException.class)
    public String createComment(String username, String storyId, CommentRequest commentRequest) {
        Assert.notNull(commentRequest, "createComment input is null");

        Comment comment = new Comment.Builder()
                .header(commentRequest.getHeader())
                .description(commentRequest.getDescription())
                .userId(username)
                .description(commentRequest.getDescription())
                .storyId(storyId)
                .id(String.valueOf(new ObjectId()))
                .build();

        Story story = storyRepository.findById(storyId);
        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return Constants.NOK;
        }
        story.addComment(comment);
        storyRepository.save(story);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackDeleteComment", ignoreExceptions = IllegalArgumentException.class)
    public String deleteComment(String commentId, String userId) {
        Assert.hasLength(commentId, "deleteComment input was null or empty");

        Comment comment = storyRepository.findCommentById(commentId);
        if(!comment.getUserId().equals(userId))
            throw new UnauthorizedUserException("Unauthorized user for this action");

        storyRepository.deleteCommentById(commentId);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackFetchComment", ignoreExceptions = IllegalArgumentException.class)
    public Comment fetchComment(String commentId) {
        Assert.hasLength(commentId, "fetchComment input was null or empty");
        return storyRepository.findCommentById(commentId);
    }

    private String fallbackCreateStory(String username, StoryRequest story, Throwable t) {
        logger.error("Create story fallback method for Story Title: " + story.getTitle()+". Returning NOK", t);
        return Constants.NOK;
    }

    private Story fallbackFetchStory(String storyId, Throwable t){
        logger.error("Fetch story fallback method for StoryId: " + storyId+". Returning empty object", t);
        return new Story();
    }

    private String fallbackDeleteStory(String storyId, String userId, Throwable t) {
        logger.error("Delete story fallback method for StoryId: " + storyId+". Returning NOK", t);
        return Constants.NOK;
    }

    private String fallbackLikeStory(String storyId, Throwable t) {
        logger.error("Like story fallback method for StoryId: " + storyId+". Returning NOK", t);
        return Constants.NOK;
    }

    private String fallbackUnlikeStory(String storyId, Throwable t) {
        logger.error("Unlike story fallback method for StoryId: " + storyId+". Returning NOK", t);
        return Constants.NOK;
    }

    private String fallbackCreateComment(String username, String storyId, CommentRequest comment, Throwable t) {
        logger.error("Create comment fallback method for StoryId: " + storyId+". Returning NOK", t);
        return Constants.NOK;
    }

    private String fallbackDeleteComment(String commentId, String userId, Throwable t) {
        logger.error("Delete comment fallback method for CommentId: " + commentId+". Returning NOK", t);
        return Constants.NOK;
    }

    private Comment fallbackFetchComment(String commentId, Throwable t){
        logger.error("Fetch comment fallback method for commentId: " + commentId+". Returning empty object", t);
        return new Comment();
    }
}
