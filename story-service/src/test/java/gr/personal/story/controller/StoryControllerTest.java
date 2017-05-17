package gr.personal.story.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.ior.ObjectKey;
import gr.personal.story.domain.*;
import gr.personal.story.service.StoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoryControllerTest {

    @InjectMocks
    private StoryController storyController;

    @Mock
    private StoryService storyService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(storyController).build();
    }

    @Test
    public void shouldFetchStory() throws Exception {
        StoryImpl story = new StoryImpl();
        story.setId("test");

        when(storyService.fetchStory(any(String.class))).thenReturn(story);

        mockMvc.perform(get("/story/fetch/test"))
                .andExpect(jsonPath("$.id").value(story.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateStory() throws Exception {

        StoryRequest storyRequest = new StoryRequest();
        storyRequest.setId("1");
        storyRequest.setPostDate(new Date());
        storyRequest.setStory("test");
        storyRequest.setTitle("Test");
        storyRequest.setUserId("1");
        storyRequest.setGeoLocation(new Geolocation(0.0,0.0));


        when(storyService.createStory(any(StoryRequest.class))).thenReturn("OK");

        mockMvc.perform(post("/story/create")
                    .content(asJsonString(storyRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));

    }


    @Test
    public void shouldDeleteStory() throws Exception {
        StoryImpl story = new StoryImpl();
        story.setId("test");

        when(storyService.deleteStory(any(String.class))).thenReturn("OK");

        mockMvc.perform(delete("/story/delete/test"))
                .andExpect(mvcResult -> "OK".equals(mvcResult))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldLikeStory() throws Exception {
        StoryImpl story = new StoryImpl();
        story.setId("test");

        when(storyService.likeStory(any(String.class))).thenReturn("OK");

        mockMvc.perform(post("/story/like/test"))
                .andExpect(mvcResult -> "OK".equals(mvcResult))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUnLikeStory() throws Exception {
        StoryImpl story = new StoryImpl();
        story.setId("test");

        when(storyService.unlikeStory(any(String.class))).thenReturn("OK");

        mockMvc.perform(post("/story/unlike/test"))
                .andExpect(mvcResult -> "OK".equals(mvcResult))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateComment() throws Exception{

        Comment comment = new Comment();
        comment.setDescription("test");
        comment.setHeader("testHeader");
        comment.setUserId("1");
        comment.setId("1");
        comment.setPostDate(new Date());

        when(storyService.createComment(anyString(), any(Comment.class))).thenReturn("OK");

        mockMvc.perform(post("/story/comment/testStoryId").contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(comment)))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldDeleteComment() throws Exception{

        when(storyService.deleteComment(any(String.class))).thenReturn("OK");

        mockMvc.perform(delete("/story/uncomment/testCommentId"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldFetchComment() throws Exception{

        Comment comment = new Comment();
        comment.setDescription("test");
        comment.setHeader("testHeader");
        comment.setUserId("1");
        comment.setId("1");
        comment.setPostDate(new Date());

        when(storyService.fetchComment(any(String.class))).thenReturn(comment);

        mockMvc.perform(get("/story/fetchComment/testCommentId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()));
    }


    private String asJsonString(Object input) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(input);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
