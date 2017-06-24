package gr.personal.story.repository;

import gr.personal.story.domain.Story;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static gr.personal.story.helper.FakeDataGenerator.generateComment;
import static gr.personal.story.helper.FakeDataGenerator.generateStoryWithoutId;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */

//TODO: Add integration testing!

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("repository")
public class StoryRepositoryTest {
    @Autowired
    private StoryRepository storyRepository;
    private Story story;

    @TestConfiguration
    static class TestContextConfiguration{

        @Bean
        public ResourceServerProperties resourceServerProperties(){
            return new ResourceServerProperties();
        }
    }

    @Before
    public void setUp(){
        story = generateStoryWithoutId();
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
