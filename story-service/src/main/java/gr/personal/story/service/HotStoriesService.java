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

@Service("HotStoriesService")
public class HotStoriesService implements StoriesService {

    private static final Logger logger = LoggerFactory.getLogger(HotStoriesService.class);

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private CacheManager cacheManager;

    @Override
    @CachePut(cacheNames = "HotStoriesOfGroup", key = "#groupId")
    @HystrixCommand(fallbackMethod = "hotStoriesOfGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<Story> getStoriesOfGroup(String groupId) {
        Assert.hasLength(groupId, "Hot stories of group input was null or empty");
        return storyRepository.findHotStoriesOfGroup(groupId);
    }

    @Override
    @CachePut(cacheNames = "HotStoriesOfLocation", key = "#geolocation")
    @HystrixCommand(fallbackMethod = "hotStoriesOfLocationFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<Story> getStoriesOfLocation(Geolocation geolocation) {
        Assert.notNull(geolocation,"Hot stories of location input is null");
        return  storyRepository.findHotStoriesOfLocation(geolocation);
    }

    @Override
    @CachePut(cacheNames = "HotStoriesOfUser", key = "#userId")
    @HystrixCommand(fallbackMethod = "hotStoriesOfUserFallback", ignoreExceptions = IllegalArgumentException.class )
    public List<Story> getStoriesOfUser(String userId) {
        Assert.hasLength(userId, "Hot stories of user input was null or empty");
        return  storyRepository.findHotStoriesOfUser(userId);
    }

    /**
     * Hystrix Fallback Classes
     **/

    private List<Story> hotStoriesOfUserFallback(String userId, Throwable t) {
        logger.warn("Hot Stories Fallback for userId: "+ userId +". Returning list from cache", t);
        if (cacheManager.getCache("HotStoriesOfUser") != null && cacheManager.getCache("HotStoriesOfUser").get(userId) != null) {
            return cacheManager.getCache("HotStoriesOfUser").get(userId, List.class);
        }
        else {
            logger.warn("Hot Stories Fallback for userId: "+ userId +". Cache is empty.");
            return new ArrayList<>();
        }
    }

    private List<Story> hotStoriesOfGroupFallback(String groupId, Throwable t) {
        logger.warn("Hot Stories Fallback for groupId: "+ groupId+". Returning list from cache", t);
        if (cacheManager.getCache("HotStoriesOfGroup") != null && cacheManager.getCache("HotStoriesOfGroup").get(groupId) != null) {
            return cacheManager.getCache("HotStoriesOfGroup").get(groupId, List.class);
        }
        else {
            logger.warn("Hot Stories Fallback for groupId: "+ groupId +". Cache is empty.");
            return new ArrayList<>();
        }

    }

    private List<Story> hotStoriesOfLocationFallback(Geolocation geolocation, Throwable t) {
        logger.warn("Hot Stories Fallback for Location: "+ geolocation+". Returning list from cache", t);
        if (cacheManager.getCache("HotStoriesOfLocation") != null && cacheManager.getCache("HotStoriesOfLocation").get(geolocation) != null) {
            return cacheManager.getCache("HotStoriesOfLocation").get(geolocation, List.class);
        }
        else {
            logger.warn("Hot Stories Fallback for geolocation: "+ geolocation +". Cache is empty.");
            return new ArrayList<>();
        }
    }
}
