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
public class NewStoriesService {

    //TODO: apply the specific criteria
    public List<Story> getNewStoriesOfUser(String userId) {
        return  FakeDataGenerator.generateStories();
    }

    public List<Story> getNewStoriesOfLocation(Geolocation geolocation) {
        return  FakeDataGenerator.generateStories();
    }

    public List<Story> getNewStoriesOfGroup(String groupId) {
        return  FakeDataGenerator.generateStories();
    }

}
