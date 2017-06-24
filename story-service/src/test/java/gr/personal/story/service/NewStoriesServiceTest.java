package gr.personal.story.service;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.repository.StoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static gr.personal.story.helper.FakeDataGenerator.generateStories;
import static gr.personal.story.helper.FakeDataGenerator.getRandomGeoLocation;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
public class NewStoriesServiceTest {
    @Autowired
    private StoriesService newStoriesService;
    @MockBean
    private StoryRepository storyRepository;
    private List<Story> originalStories;

    @TestConfiguration
    static class StoriesServiceTestContextConfiguration {
        @Bean
        public StoriesService newStoriesService() {
            return new NewStoriesService();
        }
        @Bean
        public CacheManager serviceCacheManager(){
            return new ConcurrentMapCacheManager("testCache");
        }
    }

    @Before
    public void setUp() throws Exception {
        originalStories = generateStories();

        Mockito.when(storyRepository.findNewStoriesOfGroup(anyString())).thenReturn(originalStories);
        Mockito.when(storyRepository.findNewStoriesOfUser(anyString())).thenReturn(originalStories);
        Mockito.when(storyRepository.findNewStoriesOfLocation(any(Geolocation.class))).thenReturn(originalStories);
    }

    @Test
    public void shouldGetNewStoriesOfGroup() throws Exception {
        List<Story> stories = newStoriesService.getStoriesOfGroup("testGroupId");
        assertThat(stories,is(originalStories));
    }

    @Test
    public void shouldGetNewStoriesOfUser() throws Exception {
        List<Story> stories = newStoriesService.getStoriesOfUser("testUserId");
        assertThat(stories,is(originalStories));
    }

    @Test
    public void shouldGetNewStoriesOfLocation() throws Exception {
        List<Story> stories = newStoriesService.getStoriesOfLocation(getRandomGeoLocation());
        assertThat(stories,is(originalStories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetNewStoriesOfGroup(){
        newStoriesService.getStoriesOfGroup("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetNewStoriesOfLocation(){
        newStoriesService.getStoriesOfLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetNewStoriesOfUser(){
        newStoriesService.getStoriesOfUser("");
    }
}
