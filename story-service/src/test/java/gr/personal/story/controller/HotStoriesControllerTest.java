package gr.personal.story.controller;

import com.google.common.collect.ImmutableList;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import gr.personal.story.service.StoriesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static gr.personal.story.helper.FakeDataGenerator.generateStory;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotStoriesControllerTest {

    @InjectMocks
    private HotStoriesController hotStoriesController;

    @Qualifier("HotStoriesService")
    @Mock
    private StoriesService hotStoriesService;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(hotStoriesController).build();
    }

    @Test
    public void shouldGetHotStoriesOfUser() throws Exception{

        Story story = generateStory();

        when(hotStoriesService.getStoriesOfUser("test")).thenReturn(ImmutableList.of(story));

        mockMvc.perform(get("/hotStories/user/test")).andExpect(jsonPath("$[0].id").value(story.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetHotStoriesOfLocation() throws Exception{

        Story story = generateStory();

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        when(hotStoriesService.getStoriesOfLocation(any(Geolocation.class))).thenReturn(ImmutableList.of(story));

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("latitude",String.valueOf(geolocation.getLatitude()));
        params.add("longitude",String.valueOf(geolocation.getLongitude()));

        mockMvc.perform(get("/hotStories/location").params(params))
                .andExpect(jsonPath("$[0].id").value(story.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetHotStoriesOfGroup() throws Exception{

        Story story = generateStory();

        when(hotStoriesService.getStoriesOfGroup("test")).thenReturn(ImmutableList.of(story));

        mockMvc.perform(get("/hotStories/group/test")).andExpect(jsonPath("$[0].id").value(story.getId()))
                .andExpect(status().isOk());
    }


}
