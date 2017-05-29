package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Comment;
import gr.personal.story.domain.CommentRequest;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryRequest;
import gr.personal.story.repository.StoryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class StoryServiceImpl implements StoryService{

    Logger logger = LoggerFactory.getLogger(StoryServiceImpl.class);

    @Autowired
    StoryRepository storyRepository;

    @Override
    @HystrixCommand(fallbackMethod = "fallbackCreateStory", ignoreExceptions = IllegalArgumentException.class)
    public String createStory(StoryRequest storyRequest) {
        Assert.notNull(storyRequest, "createStory input is null");

        Story story = new Story.Builder<>()
                .userId(storyRequest.getUserId())
                .geolocation(storyRequest.getGeolocation())
                .title(storyRequest.getTitle())
                .story(storyRequest.getStory())
                .groupId(storyRequest.getGroupId())
                .build();

        storyRepository.save(story);

        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackFetchStory", ignoreExceptions = IllegalArgumentException.class)
    public Story fetchStory(String storyId) {
        Assert.hasLength(storyId, "fetchStory input was null or empty");
        return storyRepository.findById(storyId);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackDeleteStory", ignoreExceptions = IllegalArgumentException.class)
    public String deleteStory(String storyId) {
        Assert.hasLength(storyId, "deleteStory input was null or empty");
         storyRepository.delete(storyId);

        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackLikeStory", ignoreExceptions = IllegalArgumentException.class)
    public String likeStory(String storyId) {
        Assert.hasLength(storyId, "likeStory input was null or empty");

        //TODO: refactor move  the following lines in separate private method
        Story story = storyRepository.findById(storyId);

        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return "NOK";
        }

        story.like();
        storyRepository.save(story);
        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackUnlikeStory", ignoreExceptions = IllegalArgumentException.class)
    public String unlikeStory(String storyId) {
        Assert.hasLength(storyId, "unlikeStory input was null or empty");

        Story story = storyRepository.findById(storyId);

        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return "NOK";
        }

        story.unlike();
        storyRepository.save(story);
        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackCreateComment", ignoreExceptions = IllegalArgumentException.class)
    public String createComment(String storyId, CommentRequest commentRequest) {
        Assert.notNull(commentRequest, "createComment input is null");

        Comment comment = new Comment.Builder()
                .header(commentRequest.getHeader())
                .description(commentRequest.getDescription())
                .userId(commentRequest.getUserId())
                .description(commentRequest.getDescription())
                .storyId(storyId)
                .id(String.valueOf(new ObjectId()))
                .build();

        Story story = storyRepository.findById(storyId);

        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return "NOK";
        }
        story.getComments().add(comment);
        storyRepository.save(story);
        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackDeleteComment", ignoreExceptions = IllegalArgumentException.class)
    public String deleteComment(String commentId) {
        //TODO: refactor, remove all asserts with AOP
        Assert.hasLength(commentId, "deleteComment input was null or empty");
        storyRepository.deleteCommentById(commentId);
        return "OK";
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackFetchComment", ignoreExceptions = IllegalArgumentException.class)
    public Comment fetchComment(String commentId) {
        Assert.hasLength(commentId, "fetchComment input was null or empty");
        return storyRepository.findCommentById(commentId);
    }

    private String fallbackCreateStory(StoryRequest story, Throwable t) {
        logger.error("Create story fallback method for Story Title: " + story.getTitle()+". Returning NOK", t);
        return "NOK";
    }

    private Story fallbackFetchStory(String storyId, Throwable t){
        logger.error("Fetch story fallback method for StoryId: " + storyId+". Returning empty object", t);
        return new Story();
    }

    private String fallbackDeleteStory(String storyId, Throwable t) {
        logger.error("Delete story fallback method for StoryId: " + storyId+". Returning NOK", t);
        return "NOK";
    }

    private String fallbackLikeStory(String storyId, Throwable t) {
        logger.error("Like story fallback method for StoryId: " + storyId+". Returning NOK", t);
        return "NOK";
    }

    private String fallbackUnlikeStory(String storyId, Throwable t) {
        logger.error("Unlike story fallback method for StoryId: " + storyId+". Returning NOK", t);
        return "NOK";
    }

    private String fallbackCreateComment(String storyId, CommentRequest comment, Throwable t) {
        logger.error("Create comment fallback method for StoryId: " + storyId+". Returning NOK", t);
        return "NOK";
    }

    private String fallbackDeleteComment(String commentId, Throwable t) {
        logger.error("Delete comment fallback method for CommentId: " + commentId+". Returning NOK", t);
        return "NOK";
    }

    private Comment fallbackFetchComment(String commentId, Throwable t){
        logger.error("Fetch comment fallback method for commentId: " + commentId+". Returning empty object", t);
        return new Comment();
    }
}
