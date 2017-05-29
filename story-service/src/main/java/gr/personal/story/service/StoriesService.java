package gr.personal.story.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nick Kanakis on 29/5/2017.
 */
public interface StoriesService {

    List<Story> getStoriesOfGroup(String groupId);
    List<Story> getStoriesOfLocation(Geolocation geolocation);
    List<Story> getStoriesOfUser(String userId);
}
