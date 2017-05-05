package gr.personal.story.service;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Story;
import gr.personal.story.util.FakeDataGenerator;
import org.springframework.stereotype.Service;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class StoryService {

    //TODO: use persistence layer
    public String createStory(Story story) {
        return "OK";
    }

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

    public Comment fetchComment(String commentId) {
        return FakeDataGenerator.generateComment();
    }
}
