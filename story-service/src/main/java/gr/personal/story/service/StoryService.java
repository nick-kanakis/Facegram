package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryImpl;
import gr.personal.story.domain.StoryRequest;
import gr.personal.story.util.FakeDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class StoryService {

    Logger logger = LoggerFactory.getLogger(StoryService.class);

    //TODO: use persistence layer
    public String createStory(StoryRequest story) {
        Assert.notNull(story, "createStory input is null");
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackFetchStory")
    public Story fetchStory(String storyId) {
        Assert.hasLength(storyId, "fetchStory input was null or empty");
        return FakeDataGenerator.generateStory();
    }

    public String deleteStory(String storyId) {
        Assert.hasLength(storyId, "deleteStory input was null or empty");
        return "OK";
    }

    public String likeStory(String storyId) {
        Assert.hasLength(storyId, "likeStory input was null or empty");
        return "OK";
    }

    public String unlikeStory(String storyId) {
        Assert.hasLength(storyId, "unlikeStory input was null or empty");
        return "OK";
    }

    public String createComment(Comment comment) {
        Assert.notNull(comment, "createComment input is null");
        return "OK";
    }

    public String deleteComment(String commentId) {
        Assert.hasLength(commentId, "deleteComment input was null or empty");
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackFetchComment")
    public Comment fetchComment(String commentId) {
        Assert.hasLength(commentId, "fetchComment input was null or empty");
        return FakeDataGenerator.generateComment();
    }

    private Story fallbackFetchStory(String storyId, Throwable t){
        logger.error("Fetch story fallback method for StoryId: " + storyId+". Returning empty object", t);
        return new StoryImpl();
    }

    private Story fallbackFetchComment(String commentId, Throwable t){
        logger.error("Fetch comment fallback method for commentId: " + commentId+". Returning empty object", t);
        return new StoryImpl();
    }
}
