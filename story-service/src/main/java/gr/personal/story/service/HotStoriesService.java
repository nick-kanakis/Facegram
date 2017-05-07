package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.util.FakeDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class HotStoriesService {

    //TODO: apply the specific criteria

    Logger logger = LoggerFactory.getLogger(HotStoriesService.class);

    @HystrixCommand(fallbackMethod = "hotStoriesOfGroupFallback")
    public List<Story> getHotStoriesOfGroup(String groupId) {
        return FakeDataGenerator.generateStories();
    }

    @HystrixCommand(fallbackMethod = "hotStoriesOfLocationFallback")
    public List<Story> getHotStoriesOfLocation(Geolocation geolocation) {
        return  FakeDataGenerator.generateStories();
    }

    @HystrixCommand(fallbackMethod = "hotStoriesOfUserFallback")
    public List<Story> getHotStoriesOfUser(String userId) {
        return  FakeDataGenerator.generateStories();
    }

    private List<Story> hotStoriesOfUserFallback(String userId, Throwable t) {
        logger.error("Hot Stories Fallback for userId: "+ userId, t);
        return  null;
    }

    private List<Story> hotStoriesOfGroupFallback(String groupId, Throwable t) {
        logger.error("Hot Stories Fallback for groupId: "+ groupId, t);
        return  null;
    }

    private List<Story> hotStoriesOfLocationFallback(Geolocation geolocation, Throwable t) {
        logger.error("Hot Stories Fallback for Location: "+ geolocation, t);
        return  null;
    }
}
