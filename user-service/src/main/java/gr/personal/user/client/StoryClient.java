package gr.personal.user.client;

import feign.hystrix.FallbackFactory;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 13/5/2017.
 */
/*
*   TODO: Find a workaround for the lack of @RequestParam POJO of Feign.
* */
@FeignClient(value = "story-service", fallbackFactory = HystrixClientFallbackFactory.class)
public interface StoryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/hotStories/user/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getHotStoriesOfUser(@PathVariable("userId") String userId);

    @RequestMapping(method = RequestMethod.GET, path = "/hotStories/location",  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getHotStoriesOfLocation(@RequestParam("latitude")double latitude, @RequestParam("longitude")double longitude);

    @RequestMapping(method = RequestMethod.GET, value = "/newStories/user/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getNewStoriesOfUser(@PathVariable("userId") String userId);

    @RequestMapping(method = RequestMethod.GET, path = "/newStories/location",  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getNewStoriesOfLocation(@RequestParam("latitude")double latitude, @RequestParam("longitude") double longitude);

    @RequestMapping(method = RequestMethod.GET, value = "/topStories/user/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getTopStoriesOfUser(@PathVariable("userId") String userId);

    @RequestMapping(method = RequestMethod.GET, path = "/topStories/location",  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<Story> getTopStoriesOfLocation(@RequestParam("latitude")double latitude, @RequestParam("longitude") double longitude);
}



