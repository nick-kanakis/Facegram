package gr.personal.story.repository;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.GroupStoryImpl;
import gr.personal.story.domain.StoryImpl;
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
public class GroupStoryRepositoryTest {

    @Autowired
    private GroupStoryRepository groupStoryRepository;

    @Before
    public void setUp(){

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        GroupStoryImpl groupStory = new GroupStoryImpl();
        groupStory.setLikes(2);
        groupStory.setComments(new ArrayList<>());
        groupStory.setPostDate(new Date());
        groupStory.setUnlikes(1);
        groupStory.setTitle("Test Title");
        groupStory.setUserId("testUserId");
        groupStory.setStory("My story");
        groupStory.setGeolocation(geolocation);
        groupStory.setGroupId("testGroupId");

        Assert.assertNull(groupStory.getId());
        this.groupStoryRepository.save(groupStory);
        Assert.assertNotNull(groupStory.getId());

    }
    @Test
    public void shouldFetchData(){
        List<GroupStoryImpl> groupStories = groupStoryRepository.findByGroupId("testGroupId");
        Assert.assertNotNull(groupStories);
        Assert.assertEquals("Test Title", groupStories.get(0).getTitle());

    }

    @Test
    public void shouldUpdateDate(){

        List<GroupStoryImpl> groupStories = groupStoryRepository.findByGroupId("testGroupId");
        groupStories.get(0).setTitle("Test Title 2");
        groupStoryRepository.save(groupStories);
        List<GroupStoryImpl> storiesAfterUpdate = groupStoryRepository.findByGroupId("testGroupId");
        Assert.assertEquals("Test Title 2", storiesAfterUpdate.get(0).getTitle());


    }

    @After
    public void tearDown() throws Exception{
        this.groupStoryRepository.deleteAll();
    }
}
