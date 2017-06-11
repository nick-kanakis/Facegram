package gr.personal.user.service;

import gr.personal.user.client.StoryClient;
import gr.personal.user.domain.Story;
import gr.personal.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static gr.personal.user.helper.FakeDataGenerator.generateRandomGeolocation;
import static gr.personal.user.helper.FakeDataGenerator.generateStories;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Nick Kanakis on 15/5/2017.
 */
@RunWith(SpringRunner.class)
public class HomepageServiceTest {

    @Autowired
    HomepageService homepageService;

    @TestConfiguration
    static class HomepageTestContextConfiguration{
        @Bean
        public HomepageService homepageService(){
            return new HomepageServiceImpl();
        }
        @Bean
        public CacheManager cacheManager(){
            return new ConcurrentMapCacheManager("testCache");
        }

    }

    @MockBean
    private StoryClient storyClient;

    @MockBean
    private UserRepository userRepository;

    List<Story> originalStories;

    @Before
    public void setUp() throws Exception {

        originalStories = generateStories();

        when(storyClient.getHotStoriesOfLocation(anyDouble(),anyDouble())).thenReturn(originalStories);
        when(storyClient.getHotStoriesOfUser(anyString())).thenReturn(originalStories);
        when(storyClient.getNewStoriesOfLocation(anyDouble(),anyDouble())).thenReturn(originalStories);
        when(storyClient.getNewStoriesOfUser(anyString())).thenReturn(originalStories);
        when(storyClient.getTopStoriesOfUser(anyString())).thenReturn(originalStories);
        when(storyClient.getTopStoriesOfLocation(anyDouble(),anyDouble())).thenReturn(originalStories);

    }

    @Test
    public void shouldRetrieveNewStories() throws Exception {
        List<Story> stories = homepageService.retrieveNewStories("testUserId", generateRandomGeolocation());
        assertTrue(stories.containsAll(originalStories) &&originalStories.containsAll(stories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveNewStories() throws Exception {
        homepageService.retrieveNewStories("", null);
    }

    @Test
    public void shouldRetrieveHotStories() throws Exception {
        List<Story> stories = homepageService.retrieveHotStories("testUserId", generateRandomGeolocation());
        assertTrue(stories.containsAll(originalStories) &&originalStories.containsAll(stories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveHotStories() throws Exception {
        homepageService.retrieveHotStories("", null);
    }

    @Test
    public void shouldRetrieveTopStories() throws Exception {
        List<Story> stories = homepageService.retrieveTopStories("testUserId", generateRandomGeolocation());
        assertThat(stories,is(originalStories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveTopStories() throws Exception {
        homepageService.retrieveTopStories("", null);
    }

    @Test
    public void shouldRetrieveMyStories() throws Exception {
        List<Story> stories = homepageService.retrieveMyStories("testUserId");
        assertThat(stories,is(originalStories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveMyStories() throws Exception {
        homepageService.retrieveMyStories("");
    }
}
