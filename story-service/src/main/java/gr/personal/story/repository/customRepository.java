package gr.personal.story.repository;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;

import java.util.List;

/**
 * Created by Nick Kanakis on 20/5/2017.
 */
public interface CustomRepository {

    Comment findCommentById(String commentId);

    void deleteCommentById(String commentId);

    List<Story> findNewStoriesOfUser(String userId);

    List<Story> findNewStoriesOfLocation(Geolocation geolocation);

    List<Story> findNewStoriesOfGroup(String groupId);

    List<Story> findTopStoriesOfLocation(Geolocation geolocation);

    List<Story> findTopStoriesOfUser(String userId);

    List<Story> findTopStoriesOfGroup(String groupId);

    List<Story> findHotStoriesOfGroup(String groupId);

    List<Story> findHotStoriesOfLocation(Geolocation geolocation);

    List<Story> findHotStoriesOfUser(String userId);
}
