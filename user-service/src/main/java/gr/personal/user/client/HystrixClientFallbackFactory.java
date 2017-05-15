package gr.personal.user.client;

import feign.hystrix.FallbackFactory;
import gr.personal.user.domain.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 13/5/2017.
 */
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<StoryClient> {
    Logger logger = LoggerFactory.getLogger(HystrixClientFallbackFactory.class);
    @Override
    public StoryClient create(Throwable throwable) {
        logger.error("HystrixClientFallbackFactory: ", throwable);

        return new StoryClient() {
            @Override
            public List<Story> getHotStoriesOfUser(@PathVariable String userId) {
                logger.warn("getHotStoriesOfUser did not respond, returning empty object");
                return new ArrayList<>();
            }

            @Override
            public List<Story> getHotStoriesOfLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
                logger.warn("getHotStoriesOfLocation did not respond, returning empty object");
                return new ArrayList<>();
            }

            @Override
            public List<Story> getNewStoriesOfUser(@PathVariable("userId") String userId) {
                logger.warn("getNewStoriesOfUser did not respond, returning empty object");
                return new ArrayList<>();
            }

            @Override
            public List<Story> getNewStoriesOfLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
                logger.warn("getNewStoriesOfLocation did not respond, returning empty object");
                return new ArrayList<>();
            }

            @Override
            public List<Story> getTopStoriesOfUser(@PathVariable("userId") String userId) {
                logger.warn("getTopStoriesOfUser did not respond, returning empty object");
                return new ArrayList<>();
            }

            @Override
            public List<Story> getTopStoriesOfLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
                logger.warn("getTopStoriesOfLocation did not respond, returning empty object");
                return new ArrayList<>();
            }

        };
    }
}
