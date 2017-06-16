package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Comment;
import gr.personal.story.domain.CommentRequest;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryRequest;

/**
 * Created by Nick Kanakis on 29/5/2017.
 */
public interface StoryService {
    @HystrixCommand(fallbackMethod = "fallbackCreateStory")
    String createStory(String name, StoryRequest storyRequest);

    @HystrixCommand(fallbackMethod = "fallbackFetchStory")
    Story fetchStory(String storyId);

    @HystrixCommand(fallbackMethod = "fallbackDeleteStory")
    String deleteStory(String storyId, String userId);

    @HystrixCommand(fallbackMethod = "fallbackLikeStory")
    String likeStory(String storyId);

    @HystrixCommand(fallbackMethod = "fallbackUnlikeStory")
    String unlikeStory(String storyId);

    @HystrixCommand(fallbackMethod = "fallbackCreateComment")
    String createComment(String name, String storyId, CommentRequest commentRequest);

    @HystrixCommand(fallbackMethod = "fallbackDeleteComment")
    String deleteComment(String commentId, String userId);

    @HystrixCommand(fallbackMethod = "fallbackFetchComment")
    Comment fetchComment(String commentId);
}
