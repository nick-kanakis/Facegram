package gr.personal.story.service;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.util.FakeDataGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Service
public class HotStoriesService {

    //TODO: apply the specific criteria
    public List<Story> getHotStoriesOfGroup(String groupId) {
        return FakeDataGenerator.generateStories();
    }

    public List<Story> getHotStoriesOfLocation(Geolocation geolocation) {
        return  FakeDataGenerator.generateStories();
    }

    public List<Story> getHotStoriesOfUser(String userId) {
        return  FakeDataGenerator.generateStories();
    }

}
