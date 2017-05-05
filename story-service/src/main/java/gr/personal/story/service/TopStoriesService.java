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
public class TopStoriesService {

    //TODO: apply the specific criteria
    public List<Story> getTopStoriesOfUser(String userId) {
        return  FakeDataGenerator.generateStories();
    }

    public List<Story> getTopStoriesOfLocation(Geolocation geolocation) {
        return  FakeDataGenerator.generateStories();
    }

    public List<Story> getTopStoriesOfGroup(String groupId) {
        return  FakeDataGenerator.generateStories();
    }

}
