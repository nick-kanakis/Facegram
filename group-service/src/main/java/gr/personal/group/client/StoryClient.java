package gr.personal.group.client;

import gr.personal.group.domain.Story;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Nick Kanakis on 18/6/2017.
 */
@FeignClient(value = "story-service", fallbackFactory = HystrixClientFallbackFactory.class)
@RequestMapping("/story-service")
public interface StoryClient {

    @CachePut(cacheNames = "HotStories", key = "#a0")
    @RequestMapping(method = RequestMethod.GET, value = "/hotStories/group/{groupId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getHotStories(@PathVariable("groupId") String groupId);

    @CachePut(cacheNames = "TopStories", key = "#a0")
    @RequestMapping(method = RequestMethod.GET, value = "/topStories/group/{groupId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getTopStories(@PathVariable("groupId") String groupId);

    @CachePut(cacheNames = "NewStories", key = "#a0")
    @RequestMapping(method = RequestMethod.GET, value = "/newStories/group/{groupId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getNewStories(@PathVariable("groupId") String groupId);

}
