package gr.personal.user.client;

import feign.hystrix.FallbackFactory;
import gr.personal.user.domain.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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

    @Autowired
    CacheManager cacheManager;

    @Override
    public StoryClient create(Throwable throwable) {
        logger.error("HystrixClientFallbackFactory: ", throwable);

        return new StoryClient() {
            @Override
            public List<Story> getHotStoriesOfUser(@PathVariable("username") String username) {
                logger.error("Retrieve Hot Stories fallback for user: "+ username + ". Returning List from Cache");

                if (cacheManager.getCache("HotStories") != null && cacheManager.getCache("HotStories").get(username) != null) {
                    return cacheManager.getCache("HotStories").get(username, List.class);
                }
                else {
                    logger.error("Retrieve Hot Stories fallback for username: "+ username +". Cache is empty.");
                    return new ArrayList<>();
                }
            }

            @Override
            public List<Story> getHotStoriesOfLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
                logger.warn("getHotStoriesOfLocation did not respond, returning empty object");
                return new ArrayList<>();
            }

            @Override
            public List<Story> getNewStoriesOfUser(@PathVariable("username") String username) {
                logger.error("Retrieve New Stories fallback for user: "+ username + ". Returning List from Cache");

                if (cacheManager.getCache("NewStories") != null && cacheManager.getCache("NewStories").get(username) != null) {
                    return cacheManager.getCache("NewStories").get(username, List.class);
                }
                else {
                    logger.error("Retrieve New Stories fallback for username: "+ username +". Cache is empty.");
                    return new ArrayList<>();
                }
            }

            @Override
            public List<Story> getNewStoriesOfLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
                logger.warn("getNewStoriesOfLocation did not respond, returning empty object");
                return new ArrayList<>();
            }

            @Override
            public List<Story> getTopStoriesOfUser(@PathVariable("username") String username) {
                logger.error("Retrieve Top Stories fallback for user: "+ username + ". Returning List from Cache");

                if (cacheManager.getCache("TopStories") != null && cacheManager.getCache("TopStories").get(username) != null) {
                    return cacheManager.getCache("TopStories").get(username, List.class);
                }
                else {
                    logger.error("Retrieve Top Stories fallback for username: "+ username +". Cache is empty.");
                    return new ArrayList<>();
                }
            }

            @Override
            public List<Story> getTopStoriesOfLocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
                logger.warn("getTopStoriesOfLocation did not respond, returning empty object");
                return new ArrayList<>();
            }

        };
    }
}
