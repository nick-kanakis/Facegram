package gr.personal.user.service;

import gr.personal.user.client.StoryClient;
import gr.personal.user.domain.Story;
import gr.personal.user.helper.FakeDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private StoryClient client;

    List<Story> originalStories;

    @Before
    public void setUp() throws Exception {

        originalStories = generateStories();

        when(client.getHotStoriesOfLocation(anyDouble(),anyDouble())).thenReturn(originalStories);
        when(client.getHotStoriesOfUser(anyString())).thenReturn(originalStories);
        when(client.getNewStoriesOfLocation(anyDouble(),anyDouble())).thenReturn(originalStories);
        when(client.getNewStoriesOfUser(anyString())).thenReturn(originalStories);
        when(client.getTopStoriesOfUser(anyString())).thenReturn(originalStories);
        when(client.getTopStoriesOfLocation(anyDouble(),anyDouble())).thenReturn(originalStories);

    }

    @Test
    public void shouldRetrieveNewStories() throws Exception {
        List<Story> stories = homepageService.retrieveNewStories("testUserId", generateRandomGeolocation());
        assertThat(stories,is(originalStories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveNewStories() throws Exception {
        homepageService.retrieveNewStories("", null);
    }

    @Test
    public void shouldRetrieveHotStories() throws Exception {
        List<Story> stories = homepageService.retrieveHotStories("testUserId", generateRandomGeolocation());
        assertThat(stories,is(originalStories));
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
}
