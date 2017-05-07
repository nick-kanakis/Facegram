package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Story;
import gr.personal.story.util.FakeDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class StoryService {

    Logger logger = LoggerFactory.getLogger(StoryService.class);

    //TODO: use persistence layer
    public String createStory(Story story) {
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackFetchStory")
    public Story fetchStory(String storyId) {
        return FakeDataGenerator.generateStory();
    }

    public String deleteStory(String storyId) {
        return "OK";
    }

    public String likeStory(String storyId) {
        return "OK";
    }

    public String unlikeStory(String storyId) {
        return "OK";
    }

    public String createComment(Comment comment) {
        return "OK";
    }

    public String deleteComment(String commentId) {
        return "OK";
    }

    @HystrixCommand(fallbackMethod = "fallbackFetchComment")
    public Comment fetchComment(String commentId) {
        return FakeDataGenerator.generateComment();
    }

    private Story fallbackFetchStory(String storyId, Throwable t){
        logger.error("Fetch story fallback method for StoryId: " + storyId, t);
        return null;
    }

    private Story fallbackFetchComment(String commentId, Throwable t){
        logger.error("Fetch comment fallback method for commentId: " + commentId, t);
        return null;
    }
}
