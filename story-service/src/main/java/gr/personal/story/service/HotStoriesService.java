package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.repository.StoryRepository;
import gr.personal.story.util.FakeDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class HotStoriesService {

    Logger logger = LoggerFactory.getLogger(HotStoriesService.class);

    @Autowired
    StoryRepository storyRepository;


    @HystrixCommand(fallbackMethod = "hotStoriesOfGroupFallback")
    public List<Story> getHotStoriesOfGroup(String groupId) {
        Assert.hasLength(groupId, "getHotStoriesOfGroup input was null or empty");
        return storyRepository.findHotStoriesOfGroup(groupId);
    }

    @HystrixCommand(fallbackMethod = "hotStoriesOfLocationFallback")
    public List<Story> getHotStoriesOfLocation(Geolocation geolocation) {
        Assert.notNull(geolocation,"getHotStoriesOfLocation input is null");
        return  storyRepository.findHotStoriesOfLocation(geolocation);
    }

    @HystrixCommand(fallbackMethod = "hotStoriesOfUserFallback")
    public List<Story> getHotStoriesOfUser(String userId) {
        Assert.hasLength(userId, "getHotStoriesOfUser input was null or empty");
        return  storyRepository.findHotStoriesOfUser(userId);
    }

    private List<Story> hotStoriesOfUserFallback(String userId, Throwable t) {
        logger.error("Hot Stories Fallback for userId: "+ userId +". Returning empty list", t);
        return new ArrayList<>();
    }

    private List<Story> hotStoriesOfGroupFallback(String groupId, Throwable t) {
        logger.error("Hot Stories Fallback for groupId: "+ groupId+". Returning empty list", t);
        return new ArrayList<>();
    }

    private List<Story> hotStoriesOfLocationFallback(Geolocation geolocation, Throwable t) {
        logger.error("Hot Stories Fallback for Location: "+ geolocation+". Returning empty list", t);
        return new ArrayList<>();
    }
}
