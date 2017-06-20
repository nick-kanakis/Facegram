package gr.personal.group.controller;

import gr.personal.group.domain.Story;
import gr.personal.group.helper.FakeDataGenerator;
import gr.personal.group.service.HomepageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by nkanakis on 6/19/2017.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("noEureka")
public class HomepageControllerTest {

    @MockBean
    private HomepageService homepageService;

    private MockMvc mockMvc;

    @InjectMocks
    private HomepageController homepageController;

    private List<Story> stories = FakeDataGenerator.generateStories();

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homepageController).build();
    }


    @Test
    public void shouldGetHotStories() throws Exception {
        when(homepageService.getHotStories(anyString())).thenReturn(stories);

        mockMvc.perform(get("/homepage/hot/testGroupId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].story").value(stories.get(0).getStory()));
    }

    @Test
    public void shouldGetTopStories() throws Exception {
        when(homepageService.getTopStories(anyString())).thenReturn(stories);

        mockMvc.perform(get("/homepage/top/testGroupId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].story").value(stories.get(0).getStory()));
    }

    @Test
    public void shouldGetNewStories() throws Exception {
        when(homepageService.getNewStories(anyString())).thenReturn(stories);

        mockMvc.perform(get("/homepage/new/testGroupId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].story").value(stories.get(0).getStory()));

    }
}
