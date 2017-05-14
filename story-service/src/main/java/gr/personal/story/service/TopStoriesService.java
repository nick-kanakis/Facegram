package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.util.FakeDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class TopStoriesService {

    //TODO: apply the specific criteria

    Logger logger = LoggerFactory.getLogger(TopStoriesService.class);

    @HystrixCommand(fallbackMethod = "topStoriesOfUserFallback")
    public List<Story> getTopStoriesOfUser(String userId) {
        Assert.hasLength(userId, "getTopStoriesOfUser input was null or empty");
        return  FakeDataGenerator.generateStories();
    }

    @HystrixCommand(fallbackMethod = "topStoriesOfLocationFallback")
    public List<Story> getTopStoriesOfLocation(Geolocation geolocation) {
        Assert.notNull(geolocation,"getTopStoriesOfLocation input is null");
        return  FakeDataGenerator.generateStories();
    }

    @HystrixCommand(fallbackMethod = "topStoriesOfGroupFallback")
    public List<Story> getTopStoriesOfGroup(String groupId) {
        Assert.hasLength(groupId, "getTopStoriesOfGroup input was null or empty");
        return  FakeDataGenerator.generateStories();
    }

    private List<Story> topStoriesOfUserFallback(String userId, Throwable t) {
        logger.error("Top Stories Fallback for userId: "+ userId+". Returning empty list", t);
        return new ArrayList<>();
    }

    private List<Story> topStoriesOfGroupFallback(String groupId, Throwable t) {
        logger.error("Top Stories Fallback for groupId: "+ groupId+". Returning empty list", t);
        return new ArrayList<>();
    }

    private List<Story> topStoriesOfLocationFallback(Geolocation geolocation, Throwable t) {
        logger.error("Top Stories Fallback for Location: "+ geolocation+". Returning empty list", t);
        return new ArrayList<>();
    }
}
