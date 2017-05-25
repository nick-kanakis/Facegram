package gr.personal.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.personal.user.domain.Gender;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.service.AdministrativeService;
import gr.personal.user.util.FakeDataGenerator;
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

import java.util.ArrayList;
import java.util.List;

import static gr.personal.user.util.FakeDataGenerator.generateUser;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nick Kanakis on 15/5/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministrativeControllerTest {

    @InjectMocks
    AdministrativeController administrativeController;

    @Mock
    AdministrativeService administrativeService;

    MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(administrativeController).build();
    }

    @Test
    public void shouldCreateUser() throws Exception{
        UserRequest userRequest = new UserRequest("testUsername","testName","testSurname",Gender.FEMALE);

        when(administrativeService.createUser(any(UserRequest.class))).thenReturn("testUserId");

        mockMvc.perform(post("/administrative/createUser")
                .content(asJsonString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "testUserId".equals(mvcResult));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        UserRequest userRequest = new UserRequest("testUsername","testName","testSurname",Gender.FEMALE);


        when(administrativeService.updateUser(any(UserRequest.class))).thenReturn("OK");

        mockMvc.perform(post("/administrative/updateUser")
                .content(asJsonString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        when(administrativeService.deleteUser(anyString())).thenReturn("OK");

        mockMvc.perform(delete("/administrative/deleteUser/testUserId"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldRetrieveUser() throws Exception {

        User user = generateUser();

        when(administrativeService.retrieveUser(anyString())).thenReturn(user);

        mockMvc.perform(get("/administrative/retrieveUser/testUserId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("1"));
    }

    @Test
    public void shouldAddFollowing() throws Exception {

        when(administrativeService.addFollowing(anyString(), anyString())).thenReturn("OK");

        mockMvc.perform(post("/administrative/addFollowing/testUserId/testFollowingId"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));

    }

    @Test
    public void shouldRemoveFollowing() throws Exception {

        when(administrativeService.removeFollowing(anyString(),anyString())).thenReturn("OK");

        mockMvc.perform(delete("/administrative/removeFollowing/testUserId/testFollowingId"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldRetrieveFollowings() throws Exception {

        User user = generateUser();

        List<User> users = new ArrayList<>();
        users.add(user);

        when(administrativeService.retrieveFollowings(anyString())).thenReturn(users);

        mockMvc.perform(get("/administrative/retrieveFollowings/testUserId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(user.getUsername()));

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
