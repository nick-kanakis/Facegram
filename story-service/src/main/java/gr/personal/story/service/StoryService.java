package gr.personal.story.service;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class StoryService {

    //TODO: actually implement the service layer and maybe break it apart?
    public List<StoryImpl> getTopStoriesOfUser(String userId) {
        return  null;
    }

    public List<StoryImpl> getTopStoriesOfLocation(Geolocation geolocation) {
        return  null;
    }

    public List<StoryImpl> getTopStoriesOfGroup(String groupId) {
        return  null;
    }

    public List<StoryImpl> getNewStoriesOfUser(String userId) {
        return  null;
    }

    public List<StoryImpl> getNewStoriesOfLocation(Geolocation geolocation) {
        return  null;
    }

    public List<StoryImpl> getNewStoriesOfGroup(String groupId) {
        return  null;
    }

    public List<StoryImpl> getHotStoriesOfGroup(String groupId) {
        return  null;
    }

    public List<StoryImpl> getHotStoriesOfLocation(Geolocation geolocation) {
        return  null;
    }

    public List<StoryImpl> getHotStoriesOfUser(String userId) {
        return  null;
    }

    public String createStory(Story story) {
        return null;
    }

    public StoryImpl fetchStory(String storyId) {
        return null;
    }

    public String deleteStory(String storyId) {
        return null;
    }

    public String likeStory(String storyId) {
        return null;
    }

    public String unlikeStory(String storyId) {
        return null;
    }

    public String createComment(Comment comment) {
        return null;
    }

    public String deleteComment(String commentId) {
        return null;
    }

    public String fetchComment(String commentId) {
        return null;
    }
}
