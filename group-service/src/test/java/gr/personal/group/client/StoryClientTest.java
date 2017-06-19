package gr.personal.group.client;

import gr.personal.group.domain.Story;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by nkanakis on 6/19/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryClientTest {

    @Autowired
    private StoryClient client;

    @Test
    public void shouldGetHotStoriesFromCache(){
        List<Story> stories = client.getHotStories("testGroupId");
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetNewtStoriesFromCache(){
        List<Story> stories = client.getNewStories("testGroupId");
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetTopStoriesFromCache(){
        List<Story> stories = client.getTopStories("testGroupId");
        Assert.assertNotNull(stories);
    }
}
