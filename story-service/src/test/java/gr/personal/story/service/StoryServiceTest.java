package gr.personal.story.service;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.CommentRequest;
import gr.personal.story.domain.Story;
import gr.personal.story.domain.StoryRequest;
import gr.personal.story.repository.StoryRepository;
import gr.personal.story.util.Constants;
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

import static gr.personal.story.helper.FakeDataGenerator.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */
@RunWith(SpringRunner.class)
public class StoryServiceTest {
    @Autowired
    private StoryService storyService;
    @MockBean
    private StoryRepository storyRepository;
    private Comment originalComment;
    private Story originalStory;
    
    @TestConfiguration
    static class StoryServiceTestContextConfiguration {
        @Bean
        public StoryService storyService() {
            return new StoryServiceImpl();
        }
        @Bean
        public CacheManager serviceCacheManager(){
            return new ConcurrentMapCacheManager("testCache");
        }
    }
    
    @Before
    public void setUp() throws Exception {
        originalStory = generateStory();
        originalComment = generateComment();

        when(storyRepository.findById(anyString())).thenReturn(originalStory);
        when(storyRepository.findCommentById(anyString())).thenReturn(originalComment);
        when(storyRepository.save(any(Story.class))).thenReturn(originalStory);
    }

    @Test
    public void shouldCreateComment() throws Exception {
        CommentRequest commentRequest = generateCommentRequest();
        String response = storyService.createComment("testUserId", "testStoryId",commentRequest);
        assertEquals(response, Constants.OK);
    }

    @Test
    public void shouldCreateStory() throws Exception {
        StoryRequest storyRequest = generateStoryRequest();
        String response = storyService.createStory("testUserId", storyRequest);
        assertEquals(response, Constants.OK);
    }
    @Test
    public void shouldFetchStory() throws Exception {
        Story story = storyService.fetchStory("testStoryId");
        assertEquals(story, originalStory);
    }

    @Test
    public void shouldFetchComment() throws Exception {
        Comment comment = storyService.fetchComment("testStoryId");
        assertEquals(comment, originalComment);
    }

    @Test
    public void shouldLikeStory() throws Exception {
        long likes = originalStory.getLikes();
        storyService.likeStory(originalStory.getId());
        assertEquals(likes+1, originalStory.getLikes());
    }

    @Test
    public void shouldUnLikeStory() throws Exception {
        long unlikes = originalStory.getUnlikes();
        storyService.unlikeStory(originalStory.getId());
        assertEquals(unlikes+1, originalStory.getUnlikes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateStory() {
        storyService.createStory("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToFetchStory() {
        storyService.fetchStory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteStory() {
        storyService.deleteStory("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToLikeStory() {
        storyService.likeStory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUnlikeStory() {
        storyService.unlikeStory("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateComment() {
        storyService.createComment("", "", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteComment() {
        storyService.deleteComment("", null);
    }
}
