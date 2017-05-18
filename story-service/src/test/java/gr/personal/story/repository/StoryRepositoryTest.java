package gr.personal.story.repository;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
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

/**
 * Created by Nick Kanakis on 17/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryRepositoryTest {

    @Autowired
    private StoryRepository storyRepository;

    @Before
    public void setUp(){

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        Story story = new Story();
        story.setLikes(2);
        story.setComments(new ArrayList<>());
        story.setPostDate(new Date());
        story.setUnlikes(1);
        story.setTitle("Test Title");
        story.setUserId("testUserId");
        story.setStory("My story");
        story.setGeolocation(geolocation);

        Assert.assertNull(story.getId());
        this.storyRepository.save(story);
        Assert.assertNotNull(story.getId());

    }
    @Test
    public void shouldFetchData(){
        List<Story> stories = storyRepository.findByUserId("testUserId");
        Assert.assertNotNull(stories);
        Assert.assertEquals("Test Title", stories.get(0).getTitle());

    }

    @Test
    public void shouldUpdateDate(){

        List<Story> stories = storyRepository.findByUserId("testUserId");
        stories.get(0).setTitle("Test Title 2");
        storyRepository.save(stories);
        List<Story> storiesAfterUpdate = storyRepository.findByUserId("testUserId");
        Assert.assertEquals("Test Title 2", storiesAfterUpdate.get(0).getTitle());


    }

    @After
    public void tearDown() throws Exception{
        this.storyRepository.deleteAll();
    }
}
