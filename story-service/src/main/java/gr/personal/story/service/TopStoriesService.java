package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.repository.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service("TopStoriesService")
public class TopStoriesService implements StoriesService {

    private static final Logger logger = LoggerFactory.getLogger(TopStoriesService.class);

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private CacheManager cacheManager;

    @CachePut(cacheNames = "TopStoriesOfUser", key = "#userId")
    @HystrixCommand(fallbackMethod = "topStoriesOfUserFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<Story> getStoriesOfUser(String userId) {
        Assert.hasLength(userId, "Top stories of user input was null or empty");
        return storyRepository.findTopStoriesOfUser(userId);
    }

    @CachePut(cacheNames = "TopStoriesOfLocation", key = "#geolocation")
    @HystrixCommand(fallbackMethod = "topStoriesOfLocationFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<Story> getStoriesOfLocation(Geolocation geolocation) {
        Assert.notNull(geolocation,"Top stories of location input is null");
        return storyRepository.findTopStoriesOfLocation(geolocation);
    }

    @CachePut(cacheNames = "TopStoriesOfGroup", key = "#groupId")
    @HystrixCommand(fallbackMethod = "topStoriesOfGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<Story> getStoriesOfGroup(String groupId) {
        Assert.hasLength(groupId, "Top stories of group input was null or empty");
        return storyRepository.findTopStoriesOfGroup(groupId);
    }

    /**
     * Hystrix Fallback Classes
     **/

    private List<Story> topStoriesOfUserFallback(String userId, Throwable t) {
        logger.warn("Top Stories Fallback for userId: "+ userId+". Returning list from cache", t);
        if (cacheManager.getCache("TopStoriesOfUser") != null && cacheManager.getCache("TopStoriesOfUser").get(userId) != null) {
            return cacheManager.getCache("TopStoriesOfUser").get(userId, List.class);
        }
        else {
            logger.warn("Top Stories Fallback for userId: "+ userId +". Cache is empty.");
            return new ArrayList<>();
        }
    }

    private List<Story> topStoriesOfGroupFallback(String groupId, Throwable t) {
        logger.warn("Top Stories Fallback for groupId: "+ groupId+". Returning list from cache", t);
        if (cacheManager.getCache("TopStoriesOfGroup") != null && cacheManager.getCache("TopStoriesOfGroup").get(groupId) != null) {
            return cacheManager.getCache("TopStoriesOfGroup").get(groupId, List.class);
        }
        else {
            logger.warn("Top Stories Fallback for groupId: "+ groupId +". Cache is empty.");
            return new ArrayList<>();
        }
    }

    private List<Story> topStoriesOfLocationFallback(Geolocation geolocation, Throwable t) {
        logger.warn("Top Stories Fallback for Location: "+ geolocation+". Returning list from cache", t);
        if (cacheManager.getCache("TopStoriesOfLocation") != null && cacheManager.getCache("TopStoriesOfLocation").get(geolocation) != null) {
            return cacheManager.getCache("TopStoriesOfLocation").get(geolocation, List.class);
        }
        else {
            logger.warn("Top Stories Fallback for geolocation: "+ geolocation +". Cache is empty.");
            return new ArrayList<>();
        }
    }
}
