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

import java.util.ArrayList;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class StoryService {

    Logger logger = LoggerFactory.getLogger(StoryService.class);

    @Autowired
    StoryRepository storyRepository;

    @HystrixCommand(fallbackMethod = "fallbackCreateStory")
    public String createStory(StoryRequest storyRequest) {
        Assert.notNull(storyRequest, "createStory input is null");

        Story story = new Story.Builder<>()
                .comments(new ArrayList<>())
                .postDate(storyRequest.getPostDate())
                .userId(storyRequest.getUserId())
                .geolocation(storyRequest.getGeoLocation())
                .title(storyRequest.getTitle())
                .story(storyRequest.getStory())
                .likes(0)
                .unlikes(0)
                .groupId(storyRequest.getGroupId())
                .build();

        storyRepository.save(story);

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackFetchStory")
    public Story fetchStory(String storyId) {
        Assert.hasLength(storyId, "fetchStory input was null or empty");
        return storyRepository.findById(storyId);
    }

    @HystrixCommand(fallbackMethod = "fallbackDeleteStory")
    public String deleteStory(String storyId) {
        Assert.hasLength(storyId, "deleteStory input was null or empty");
         storyRepository.delete(storyId);

        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackLikeStory")
    public String likeStory(String storyId) {
        Assert.hasLength(storyId, "likeStory input was null or empty");

        //TODO: refactor move  the following lines in separate private method
        Story story = storyRepository.findById(storyId);

        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return "NOK";
        }

        story.setLikes(story.getLikes()+1);
        storyRepository.save(story);
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackUnlikeStory")
    public String unlikeStory(String storyId) {
        Assert.hasLength(storyId, "unlikeStory input was null or empty");

        Story story = storyRepository.findById(storyId);

        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return "NOK";
        }

        story.setUnlikes(story.getUnlikes()+1);
        storyRepository.save(story);
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackCreateComment")
    public String createComment(String storyId, CommentRequest commentRequest) {
        Assert.notNull(commentRequest, "createComment input is null");

        Comment comment = new Comment();
        comment.setHeader(commentRequest.getHeader());
        comment.setPostDate(commentRequest.getPostDate());
        comment.setUserId(commentRequest.getUserId());
        comment.setDescription(commentRequest.getDescription());
        comment.setId(String.valueOf(new ObjectId()));

        Story story = storyRepository.findById(storyId);

        if(story ==null){
            logger.error("There is no story with id={}", storyId);
            return "NOK";
        }
        story.getComments().add(comment);
        storyRepository.save(story);
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackDeleteComment")
    public String deleteComment(String commentId) {
        //TODO: refactor, remove all asserts with AOP
        Assert.hasLength(commentId, "deleteComment input was null or empty");
        storyRepository.deleteCommentById(commentId);
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackFetchComment")
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
