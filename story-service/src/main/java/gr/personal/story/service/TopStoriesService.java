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
public class TopStoriesService {

    //TODO: apply the specific criteria

    Logger logger = LoggerFactory.getLogger(TopStoriesService.class);

    @HystrixCommand(fallbackMethod = "topStoriesOfUserFallback")
    public List<Story> getTopStoriesOfUser(String userId) {
        return  FakeDataGenerator.generateStories();
    }

    @HystrixCommand(fallbackMethod = "topStoriesOfGroupFallback")
    public List<Story> getTopStoriesOfLocation(Geolocation geolocation) {
        return  FakeDataGenerator.generateStories();
    }

    @HystrixCommand(fallbackMethod = "topStoriesOfLocationFallback")
    public List<Story> getTopStoriesOfGroup(String groupId) {
        return  FakeDataGenerator.generateStories();
    }

    private List<Story> topStoriesOfUserFallback(String userId, Throwable t) {
        logger.error("Top Stories Fallback for userId: "+ userId, t);
        return  null;
    }

    private List<Story> topStoriesOfGroupFallback(String groupId, Throwable t) {
        logger.error("Top Stories Fallback for groupId: "+ groupId, t);
        return  null;
    }

    private List<Story> topStoriesOfLocationFallback(Geolocation geolocation, Throwable t) {
        logger.error("Top Stories Fallback for Location: "+ geolocation, t);
        return  null;
    }
}
