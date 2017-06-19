package gr.personal.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import gr.personal.group.domain.Group;
import gr.personal.group.domain.GroupRequest;
import gr.personal.group.service.AdministrativeService;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by nkanakis on 6/19/2017.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("noEureka")
public class AdministrativeControllerTest {

    @MockBean
    private AdministrativeService administrativeService;

    private MockMvc mockMvc;

    @InjectMocks
    private AdministrativeController administrativeController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(administrativeController).build();
    }

    @Test
    public void shouldFollow() throws Exception{
        when(administrativeService.followGroup(anyString())).thenReturn("OK");

        mockMvc.perform(post("/administrative/follow/testGroupId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldUnfollow() throws Exception{
        when(administrativeService.unfollowGroup(anyString())).thenReturn("OK");

        mockMvc.perform(delete("/administrative/unfollow/testGroupId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldCreateGroup() throws Exception{
        GroupRequest groupRequest = new GroupRequest(null, "testName", "testAbout");
        when(administrativeService.createGroup(anyString(), any(GroupRequest.class))).thenReturn("OK");

        mockMvc.perform(post("/administrative/create").principal(new UserPrincipal("testUserId"))
                .content(asJsonString(groupRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldRetrieveGroup() throws Exception{
        Group group = new Group("testModerator", "testName", "testAbout", null);
        when(administrativeService.retrieveGroup(anyString())).thenReturn(group);

        mockMvc.perform(get("/administrative/retrieve/testGroupId").principal(new UserPrincipal("testUserId"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moderator").value(group.getModerator()));

    }

    @Test
    public void shouldDeleteGroup() throws Exception{
        when(administrativeService.deleteGroup(anyString(), anyString())).thenReturn("OK");

        mockMvc.perform(delete("/administrative/delete/testGroupId").principal(new UserPrincipal("testUserId"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldUpdateGroup() throws Exception{
        when(administrativeService.updateGroup(anyString(), any(GroupRequest.class), anyString())).thenReturn("OK");

        GroupRequest groupRequest = new GroupRequest(null, "testName", "testAbout");
        mockMvc.perform(post("/administrative/update/testGroupId").principal(new UserPrincipal("testUserId"))
                .content(asJsonString(groupRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));

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
