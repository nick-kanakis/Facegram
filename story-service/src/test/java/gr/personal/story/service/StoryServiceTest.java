package gr.personal.story.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryServiceTest {

    @Autowired
    private StoryService storyService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateStory(){
        storyService.createStory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToFetchStory(){
        storyService.fetchStory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteStory(){
        storyService.deleteStory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToLikeStory(){
        storyService.likeStory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUnlikeStory(){
        storyService.unlikeStory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateComment(){
        storyService.createComment("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteComment(){
        storyService.deleteComment("");
    }




}
