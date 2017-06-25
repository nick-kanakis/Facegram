package gr.personal.group.service;


import gr.personal.group.client.StoryClient;
import gr.personal.group.domain.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
@Service
public class HomepageServiceImpl implements HomepageService {

    private static final Logger logger = LoggerFactory.getLogger(HomepageServiceImpl.class);
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private StoryClient storyClient;

    @Override
    public List<Story> getHotStories(String groupId) {
        Assert.hasLength(groupId, "getHotStories input is empty or null");
        return storyClient.getHotStories(groupId);
    }

    @Override
    public List<Story> getTopStories(String groupId) {
        Assert.hasLength(groupId, "getTopStories input is empty or null");
        return storyClient.getTopStories(groupId);
    }

    @Override
    public List<Story> getNewStories(String groupId) {
        Assert.hasLength(groupId, "getNewStories input is empty or null");
        return storyClient.getNewStories(groupId);
    }
}
