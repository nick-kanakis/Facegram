package gr.personal.story.service;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.repository.StoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static gr.personal.story.helper.FakeDataGenerator.getRandomGeoLocation;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


import java.util.List;

import static gr.personal.story.helper.FakeDataGenerator.generateStories;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotStoriesServiceTest {

    @Qualifier("HotStoriesService")
    @Autowired
    private StoriesService hotStoriesService;

    @MockBean
    private StoryRepository storyRepository;
    private List<Story> originalStories;

    @Before
    public void setup(){
        originalStories = generateStories();

        Mockito.when(storyRepository.findHotStoriesOfGroup(anyString())).thenReturn(originalStories);
        Mockito.when(storyRepository.findHotStoriesOfUser(anyString())).thenReturn(originalStories);
        Mockito.when(storyRepository.findHotStoriesOfLocation(any(Geolocation.class))).thenReturn(originalStories);
    }

    @Test
    public void shouldGetHotStoriesOfGroup() throws Exception {
        List<Story> stories = hotStoriesService.getStoriesOfGroup("testGroupId");
        assertThat(stories,is(originalStories));
    }

    @Test
    public void shouldGetHotStoriesOfUser() throws Exception {
        List<Story> stories = hotStoriesService.getStoriesOfUser("testUserId");
        assertThat(stories,is(originalStories));
    }

    @Test
    public void shouldGetHotStoriesOfLocation() throws Exception {
        List<Story> stories = hotStoriesService.getStoriesOfLocation(getRandomGeoLocation());
        assertThat(stories,is(originalStories));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetHotStoriesOfGroup(){
        hotStoriesService.getStoriesOfGroup("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetHotStoriesOfLocation(){
        hotStoriesService.getStoriesOfLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetHotStoriesOfUser(){
        hotStoriesService.getStoriesOfUser("");
    }


}
