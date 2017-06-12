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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static gr.personal.story.helper.FakeDataGenerator.generateStory;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("noEureka")
public class TopStoriesControllerTest {


    @Qualifier("TopStoriesService")
    @MockBean
    private StoriesService topStoriesService;

    MockMvc mockMvc;

    @InjectMocks
    private TopStoriesController topStoriesController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(topStoriesController).build();
    }

    @Test
    public void shouldGetTopStoriesOfUser() throws Exception{

        Story story = generateStory();

        when(topStoriesService.getStoriesOfUser("test")).thenReturn(ImmutableList.of(story));

        mockMvc.perform(get("/topStories/user/test")).andExpect(jsonPath("$[0].id").value(story.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetTopStoriesOfLocation() throws Exception{

        Story story = generateStory();

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        when(topStoriesService.getStoriesOfLocation(any(Geolocation.class))).thenReturn(ImmutableList.of(story));

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("latitude",String.valueOf(geolocation.getLatitude()));
        params.add("longitude",String.valueOf(geolocation.getLongitude()));

        mockMvc.perform(get("/topStories/location").params(params))
                .andExpect(jsonPath("$[0].id").value(story.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetTopStoriesOfGroup() throws Exception{

        Story story = generateStory();

        when(topStoriesService.getStoriesOfGroup("test")).thenReturn(ImmutableList.of(story));

        mockMvc.perform(get("/topStories/group/test")).andExpect(jsonPath("$[0].id").value(story.getId()))
                .andExpect(status().isOk());
    }


}
