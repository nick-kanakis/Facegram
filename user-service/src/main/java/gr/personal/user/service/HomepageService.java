package gr.personal.user.service;

import gr.personal.user.client.StoryClient;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
@Service
public class HomepageService {

    @Autowired
    StoryClient client;

    public List<Story> retrieveNewStories(String userId, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveNewStories input is null");
        Assert.hasLength(userId, "retrieveNewStories input is empty or null");

        List<Story> newStoriesOfUser = client.getNewStoriesOfUser(userId);
        List<Story> newStoriesOfLocation = client.getNewStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(newStoriesOfUser, newStoriesOfLocation);
        Collections.sort(stories, Comparator.comparing(Story::getPostDate).reversed());
        return stories;
    }

    public List<Story> retrieveHotStories(String userId, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveHotStories input is null");
        Assert.hasLength(userId, "retrieveHotStories input is empty or null");

        List<Story> hotStoriesOfUser = client.getHotStoriesOfUser(userId);
        List<Story> hotStoriesOfLocation = client.getHotStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(hotStoriesOfUser, hotStoriesOfLocation);
        Collections.sort(stories, ((o2, o1) -> o1.getComments().size() - o2.getComments().size()));
        return stories;
    }

    public List<Story> retrieveTopStories(String userId, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveTopStories input is null");
        Assert.hasLength(userId, "retrieveTopStories input is empty or null");

        List<Story> topStoriesOfUser = client.getTopStoriesOfUser(userId);
        List<Story> topStoriesOfLocation = client.getTopStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(topStoriesOfUser, topStoriesOfLocation);
        Collections.sort(stories, Comparator.comparing(Story::getLikes).reversed());
        return stories;
    }

    private List<Story> mergeAndRemoveDuplicates(List<Story> list1, List<Story> list2){
        list2.removeAll(list1);
        list1.addAll(list2);
        return list1;
    }
}
