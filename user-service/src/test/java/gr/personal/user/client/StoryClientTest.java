package gr.personal.user.client;

import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

//TODO: review the unit testing of Feign client

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryClientTest {

    @Autowired
    private StoryClient client;

    @MockBean
    private  GroupClient groupClient;

    @Test
    public void shouldGetHotStoriesOfUserFromCache(){
        List<Story> stories = client.getHotStoriesOfUser("test");
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetNewtStoriesOfUserFromCache(){
        List<Story> stories = client.getNewStoriesOfUser("test");
        Assert.assertNotNull(stories);
    }

    @Test
    public void shouldGetTopStoriesOfUserFromCache(){
        List<Story> stories = client.getTopStoriesOfUser("test");
        Assert.assertNotNull(stories);
    }


}
