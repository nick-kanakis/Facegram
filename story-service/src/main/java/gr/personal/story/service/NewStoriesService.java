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
public class NewStoriesService {

    //TODO: apply the specific criteria

    Logger logger = LoggerFactory.getLogger(NewStoriesService.class);

    @Autowired
    StoryRepository storyRepository;

    @HystrixCommand(fallbackMethod = "newStoriesOfUserFallback")
    public List<Story> getNewStoriesOfUser(String userId) {
        Assert.hasLength(userId, "getNewStoriesOfUser input was null or empty");

        return storyRepository.findNewStoriesOfUser(userId);
    }

    @HystrixCommand(fallbackMethod = "newStoriesOfLocationFallback")
    public List<Story> getNewStoriesOfLocation(Geolocation geolocation) {
        Assert.notNull(geolocation,"getNewStoriesOfLocation input is null");
        return  storyRepository.findNewStoriesOfLocation(geolocation);
    }

    @HystrixCommand(fallbackMethod = "newStoriesOfGroupFallback")
    public List<Story> getNewStoriesOfGroup(String groupId) {
        Assert.hasLength(groupId, "getNewStoriesOfGroup input was null or empty");
        return storyRepository.findNewStoriesOfGroup(groupId);
    }

    private List<Story> newStoriesOfUserFallback(String userId, Throwable t) {
        logger.error("New Stories Fallback for UserId "+userId+". Returning empty list", t);
        return  new ArrayList<>();
    }

    private List<Story> newStoriesOfLocationFallback(Geolocation geolocation, Throwable t) {
        logger.error("New Stories Fallback for Location " + geolocation+". Returning empty list", t);
        return new ArrayList<>();
    }

    private List<Story> newStoriesOfGroupFallback(String groupId, Throwable t) {
        logger.error("New Stories Fallback for GroupId "+groupId+". Returning empty list", t);
        return new ArrayList<>();
    }

}
