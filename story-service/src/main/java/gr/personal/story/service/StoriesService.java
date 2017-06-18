package gr.personal.story.service;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;

import java.util.List;

/**
 * Created by Nick Kanakis on 29/5/2017.
 */
public interface StoriesService {

    List<Story> getStoriesOfGroup(String groupId);
    List<Story> getStoriesOfLocation(Geolocation geolocation);
    List<Story> getStoriesOfUser(String userId);
}
