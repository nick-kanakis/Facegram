package gr.personal.story.repository;

import gr.personal.story.domain.Story;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static gr.personal.story.helper.FakeDataGenerator.generateComment;
import static gr.personal.story.helper.FakeDataGenerator.generateStoryWithoutId;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */

//TODO: Try embedded mongo db or separate integration tests with unit test!

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("repository")
public class StoryRepositoryTest {

    @Autowired
    private StoryRepository storyRepository;

    private Story story;

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
