package gr.personal.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.user.client.StoryClient;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
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

    Logger logger = LoggerFactory.getLogger(HomepageService.class);

    public List<Story> retrieveNewStories(String username, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveNewStories input is null");
        Assert.hasLength(username, "retrieveNewStories input is empty or null");

        List<Story> newStoriesOfUser = client.getNewStoriesOfUser(username);
        List<Story> newStoriesOfLocation = client.getNewStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(newStoriesOfUser, newStoriesOfLocation);
        Collections.sort(stories, Comparator.comparing(Story::getPostDate).reversed());
        return stories;
    }

    public List<Story> retrieveHotStories(String username, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveHotStories input is null");
        Assert.hasLength(username, "retrieveHotStories input is empty or null");

        List<Story> hotStoriesOfUser = client.getHotStoriesOfUser(username);
        List<Story> hotStoriesOfLocation = client.getHotStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(hotStoriesOfUser, hotStoriesOfLocation);
        Collections.sort(stories, ((o2, o1) -> o1.getComments().size() - o2.getComments().size()));
        return stories;
    }

    public List<Story> retrieveTopStories(String username, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveTopStories input is null");
        Assert.hasLength(username, "retrieveTopStories input is empty or null");

        List<Story> topStoriesOfUser = client.getTopStoriesOfUser(username);
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
