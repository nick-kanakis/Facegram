package gr.personal.group.service;

import gr.personal.group.client.StoryClient;
import gr.personal.group.domain.Story;
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

import static gr.personal.group.helper.FakeDataGenerator.generateStories;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by nkanakis on 6/19/2017.
 */
@RunWith(SpringRunner.class)
public class HomepageServiceTest {

    @Autowired
    private HomepageService homepageService;

    @TestConfiguration
    static class AdministrativeTestContextConfiguration{

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

    private List<Story> stories;

    @Before
    public void setUp() throws Exception {
        stories = generateStories();
        when(storyClient.getHotStories(anyString())).thenReturn(stories);
        when(storyClient.getTopStories(anyString())).thenReturn(stories);
        when(storyClient.getNewStories(anyString())).thenReturn(stories);

    }

    @Test
    public void shouldGetHotStories() throws Exception {
        List<Story> stories = homepageService.getHotStories("testGroupId");
        assertTrue(stories.containsAll(this.stories) &&this.stories.containsAll(stories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetHotStories() throws Exception {
        homepageService.getHotStories("");
    }

    @Test
    public void shouldGetTopStories() throws Exception {
        List<Story> stories = homepageService.getTopStories("testGroupId");
        assertTrue(stories.containsAll(this.stories) &&this.stories.containsAll(stories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetTopStories() throws Exception {
        homepageService.getTopStories("");
    }


    @Test
    public void shouldGetNewStories() throws Exception {
        List<Story> stories = homepageService.getNewStories("testGroupId");
        assertTrue(stories.containsAll(this.stories) &&this.stories.containsAll(stories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetNewStories() throws Exception {
        homepageService.getNewStories("");
    }
}
