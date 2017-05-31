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
@SpringBootTest
public class TopStoriesServiceTest {

    @Qualifier("TopStoriesService")
    @Autowired
    private StoriesService topStoriesService;

    @Before
    public void setUp() throws Exception {
        originalStories = generateStories();

        Mockito.when(storyRepository.findTopStoriesOfGroup(anyString())).thenReturn(originalStories);
        Mockito.when(storyRepository.findTopStoriesOfUser(anyString())).thenReturn(originalStories);
        Mockito.when(storyRepository.findTopStoriesOfLocation(any(Geolocation.class))).thenReturn(originalStories);
    }

    @Test
    public void shouldGetTopStoriesOfGroup() throws Exception {
        List<Story> stories = topStoriesService.getStoriesOfGroup("testGroupId");
        assertThat(stories,is(originalStories));
    }

    @Test
    public void shouldGetTopStoriesOfUser() throws Exception {
        List<Story> stories = topStoriesService.getStoriesOfUser("testUserId");
        assertThat(stories,is(originalStories));
    }

    @Test
    public void shouldGetTopStoriesOfLocation() throws Exception {
        List<Story> stories = topStoriesService.getStoriesOfLocation(getRandomGeoLocation());
        assertThat(stories,is(originalStories));
    }


    @MockBean
    private StoryRepository storyRepository;
    private List<Story> originalStories;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetTopStoriesOfGroup(){
        topStoriesService.getStoriesOfGroup("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetTopStoriesOfLocation(){
        topStoriesService.getStoriesOfLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetTopStoriesOfUser(){
        topStoriesService.getStoriesOfUser("");
    }


}
