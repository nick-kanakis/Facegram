package gr.personal.group.client;

import feign.hystrix.FallbackFactory;
import gr.personal.group.domain.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 13/5/2017.
 */
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<StoryClient> {

    private static final Logger logger = LoggerFactory.getLogger(HystrixClientFallbackFactory.class);
    @Autowired
    private CacheManager cacheManager;

    @Override
    public StoryClient create(Throwable throwable) {
        logger.error("HystrixClientFallbackFactory: ", throwable);
        return new StoryClient() {
            @Override
            public List<Story> getHotStories(@PathVariable("groupId") String groupId) {
                logger.error("Retrieve Hot Stories fallback for groupId: "+ groupId + ". Returning List from Cache");
                if (cacheManager.getCache("HotStories") != null && cacheManager.getCache("HotStories").get(groupId) != null) {
                    return cacheManager.getCache("HotStories").get(groupId, List.class);
                }
                else {
                    logger.error("Retrieve Hot Stories fallback for groupId: "+ groupId +". Cache is empty.");
                    return new ArrayList<>();
                }
            }

            @Override
            public List<Story> getNewStories(@PathVariable("groupId") String groupId) {
                logger.error("Retrieve New Stories fallback for groupId: "+ groupId + ". Returning List from Cache");
                if (cacheManager.getCache("NewStories") != null && cacheManager.getCache("NewStories").get(groupId) != null) {
                    return cacheManager.getCache("NewStories").get(groupId, List.class);
                }
                else {
                    logger.error("Retrieve New Stories fallback for groupId: "+ groupId +". Cache is empty.");
                    return new ArrayList<>();
                }
            }

            @Override
            public List<Story> getTopStories(@PathVariable("groupId") String groupId) {
                logger.error("Retrieve Top Stories fallback for groupId: "+ groupId + ". Returning List from Cache");
                if (cacheManager.getCache("TopStories") != null && cacheManager.getCache("TopStories").get(groupId) != null) {
                    return cacheManager.getCache("TopStories").get(groupId, List.class);
                }
                else {
                    logger.error("Retrieve Top Stories fallback for groupId: "+ groupId +". Cache is empty.");
                    return new ArrayList<>();
                }
            }
        };
    }
}
