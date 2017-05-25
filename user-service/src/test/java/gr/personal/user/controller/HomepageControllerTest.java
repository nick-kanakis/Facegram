package gr.personal.user.controller;

import com.google.common.collect.ImmutableList;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import gr.personal.user.service.HomepageService;
import gr.personal.user.util.FakeDataGenerator;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static gr.personal.user.util.FakeDataGenerator.generateStory;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nick Kanakis on 15/5/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomepageControllerTest {

    @InjectMocks
    private HomepageController homepageController;

    @Mock
    private HomepageService homepageService;

    private MockMvc mockMvc;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homepageController).build();

    }

    @Test
    public void shouldRetrieveTopStories() throws Exception {

        Story story = generateStory();

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("latitude",String.valueOf(geolocation.getLatitude()));
        params.add("longitude",String.valueOf(geolocation.getLongitude()));

        when(homepageService.retrieveTopStories(anyString(),any(Geolocation.class))).thenReturn(ImmutableList.of(story));

        mockMvc.perform(get("/homepage/retrieveTopStories/testUserId").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(story.getId()));
    }

    @Test
    public void shouldRetrieveHotStories() throws Exception{

        Story story = generateStory();

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("latitude",String.valueOf(geolocation.getLatitude()));
        params.add("longitude",String.valueOf(geolocation.getLongitude()));

        when(homepageService.retrieveHotStories(anyString(),any(Geolocation.class))).thenReturn(ImmutableList.of(story));

        mockMvc.perform(get("/homepage/retrieveHotStories/testUserId").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(story.getId()));

    }

    @Test
    public void shouldRetrieveNewStories() throws Exception{
        Story story = generateStory();

        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(0);
        geolocation.setLongitude(0);

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("latitude",String.valueOf(geolocation.getLatitude()));
        params.add("longitude",String.valueOf(geolocation.getLongitude()));

        when(homepageService.retrieveNewStories(anyString(),any(Geolocation.class))).thenReturn(ImmutableList.of(story));

        mockMvc.perform(get("/homepage/retrieveNewStories/testUserId").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(story.getId()));
    }


}
