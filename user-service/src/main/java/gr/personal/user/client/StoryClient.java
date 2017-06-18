package gr.personal.user.client;


import gr.personal.user.domain.Story;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Nick Kanakis on 13/5/2017.
 */
/*
*   TODO: Find a workaround for the lack of @RequestParam POJO of Feign.
* */
@FeignClient(value = "story-service", fallbackFactory = HystrixClientFallbackFactory.class)
@RequestMapping("/story-service")
public interface StoryClient {

    //Key will be the First argument of the method more here: http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html#cache-spel-context
    @CachePut(cacheNames = "HotStories", key = "#a0")
    @RequestMapping(method = RequestMethod.GET, value = "/hotStories/user/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getHotStoriesOfUser(@PathVariable("username") String username);

    @RequestMapping(method = RequestMethod.GET, path = "/hotStories/location",  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getHotStoriesOfLocation(@RequestParam("latitude")double latitude, @RequestParam("longitude")double longitude);

    @CachePut(cacheNames = "NewStories", key = "#a0")
    @RequestMapping(method = RequestMethod.GET, value = "/newStories/user/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getNewStoriesOfUser(@PathVariable("username") String username);

    @RequestMapping(method = RequestMethod.GET, path = "/newStories/location",  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getNewStoriesOfLocation(@RequestParam("latitude")double latitude, @RequestParam("longitude") double longitude);

    @CachePut(cacheNames = "TopStories", key = "#a0")
    @RequestMapping(method = RequestMethod.GET, value = "/topStories/user/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getTopStoriesOfUser(@PathVariable("username") String username);

    @RequestMapping(method = RequestMethod.GET, path = "/topStories/location",  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getTopStoriesOfLocation(@RequestParam("latitude")double latitude, @RequestParam("longitude") double longitude);
}



