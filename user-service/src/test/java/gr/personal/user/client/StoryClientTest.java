package gr.personal.user.client;

import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Matchers.any;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryClientTest {

    @Autowired
    private StoryClient client;

    @Test
    public void shouldGetHotStoriesOfUser(){
        List<Story> stories = client.getHotStoriesOfUser("test");
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetHotStoriesOfLocation(){
        List<Story> stories = client.getHotStoriesOfLocation(any(Double.class), any(Double.class));
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetNewtStoriesOfUser(){
        List<Story> stories = client.getNewStoriesOfUser("test");
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetNewStoriesOfLocation(){
        List<Story> stories = client.getNewStoriesOfLocation(any(Double.class), any(Double.class));
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldTopNewtStoriesOfUser(){
        List<Story> stories = client.getTopStoriesOfUser("test");
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetTopStoriesOfLocation(){
        List<Story> stories = client.getTopStoriesOfLocation(any(Double.class), any(Double.class));
        Assert.assertNotNull(stories);
    }




}
