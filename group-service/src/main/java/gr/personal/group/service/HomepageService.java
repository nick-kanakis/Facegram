package gr.personal.group.service;

import gr.personal.group.domain.Story;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
public interface HomepageService {
    List<Story> getHotStories(String groupId);

    List<Story> getNewStories(String groupId);

    List<Story> getTopStories(String groupId);
}
