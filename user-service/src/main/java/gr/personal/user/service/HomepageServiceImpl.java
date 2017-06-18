package gr.personal.user.service;

import gr.personal.user.client.StoryClient;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import gr.personal.user.domain.User;
import gr.personal.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
@Service
public class HomepageServiceImpl implements HomepageService {

    @Autowired
    StoryClient client;

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(HomepageServiceImpl.class);

    @Override
    public List<Story> retrieveNewStories(String username, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveNewStories input is null");
        Assert.hasLength(username, "retrieveNewStories input is empty or null");

        List<Story> newStoriesOfUser = new ArrayList<>();
        List<String> followingIds = new ArrayList<>();

        User user = userRepository.findByUsername(username);

        if(user!=null)
            followingIds.addAll(user.getFollowingIds());

        for (String followingsUsername : followingIds) {
            newStoriesOfUser.addAll(client.getNewStoriesOfUser(followingsUsername));
        }

        List<Story> newStoriesOfLocation = client.getNewStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(newStoriesOfUser, newStoriesOfLocation);
        Collections.sort(stories, Comparator.comparing(Story::getPostDate).reversed());
        return stories;
    }

    @Override
    public List<Story> retrieveHotStories(String username, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveHotStories input is null");
        Assert.hasLength(username, "retrieveHotStories input is empty or null");

        List<Story> hotStoriesOfUser = new ArrayList<>();
        List<String> followingIds = new ArrayList<>();
        User user = userRepository.findByUsername(username);

        if(user!=null)
            followingIds.addAll(user.getFollowingIds());

        for (String followingsUsername : followingIds) {
            hotStoriesOfUser.addAll(client.getHotStoriesOfUser(followingsUsername));
        }

        List<Story> hotStoriesOfLocation = client.getHotStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(hotStoriesOfUser, hotStoriesOfLocation);
        Collections.sort(stories, ((o2, o1) -> o1.getComments().size() - o2.getComments().size()));
        return stories;
    }

    @Override
    public List<Story> retrieveTopStories(String username, Geolocation geolocation) {
        Assert.notNull(geolocation, "retrieveTopStories input is null");
        Assert.hasLength(username, "retrieveTopStories input is empty or null");

        List<Story> topStoriesOfUser = new ArrayList<>();
        List<String> followingIds = new ArrayList<>();

        User user = userRepository.findByUsername(username);

        if(user!=null)
            followingIds.addAll(user.getFollowingIds());

        for (String followingsUsername : followingIds) {
            topStoriesOfUser.addAll(client.getTopStoriesOfUser(followingsUsername));
        }

        List<Story> topStoriesOfLocation = client.getTopStoriesOfLocation(geolocation.getLatitude(), geolocation.getLongitude());

        List<Story> stories = mergeAndRemoveDuplicates(topStoriesOfUser, topStoriesOfLocation);
        Collections.sort(stories, Comparator.comparing(Story::getLikes).reversed());
        return stories;
    }

    @Override
    public List<Story> retrieveMyStories(String username) {
        Assert.hasLength(username, "retrieveMyStories input is empty or null");
        List<Story> stories = client.getNewStoriesOfUser(username);
        return stories;
    }

    @Override
    public List<Story> mergeAndRemoveDuplicates(List<Story> list1, List<Story> list2) {
        list2.removeAll(list1);
        list1.addAll(list2);
        return list1;
    }


}
