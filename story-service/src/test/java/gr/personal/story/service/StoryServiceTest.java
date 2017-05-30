package gr.personal.story.service;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Story;
import gr.personal.story.repository.StoryRepository;
import gr.personal.story.util.FakeDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static gr.personal.story.util.FakeDataGenerator.generateComment;
import static gr.personal.story.util.FakeDataGenerator.generateStory;
import static org.mockito.Matchers.anyString;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryServiceTest {

    @Autowired
    private StoryService storyService;
    @MockBean
    private StoryRepository storyRepository;
    private Comment comment;
    private Story story;

    @Before
    public void setUp() throws Exception {
        story = generateStory();
        comment = generateComment();

        Mockito.when(storyRepository.findById(anyString())).thenReturn(story);
        Mockito.when(storyRepository.findCommentById(anyString())).thenReturn(comment);
    }

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
