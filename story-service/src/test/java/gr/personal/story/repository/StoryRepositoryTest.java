package gr.personal.story.repository;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.util.FakeDataGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static gr.personal.story.util.FakeDataGenerator.generateComment;
import static gr.personal.story.util.FakeDataGenerator.generateStory;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryRepositoryTest {

    @Autowired
    private StoryRepository storyRepository;

    private Story story;

    @Before
    public void setUp(){

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        story = generateStory();

        Assert.assertNull(story.getId());
        this.storyRepository.save(story);
        Assert.assertNotNull(story.getId());
    }

    @After
    public void tearDown() throws Exception{
        this.storyRepository.delete(story.getId());
    }

    @Test
    public void shouldFetchData(){
        Story retrievedStory = storyRepository.findById(story.getId());
        Assert.assertNotNull(retrievedStory);
        Assert.assertEquals(story.getTitle(), retrievedStory.getTitle());
    }

    @Test
    public void shouldUpdateData(){

        Story retrievedStory = storyRepository.findById(story.getId());
        retrievedStory.addComment(generateComment());
        storyRepository.save(retrievedStory);
        Story updatedStory = storyRepository.findById(story.getId());
        Assert.assertNotNull(updatedStory.getComments());
    }

}
