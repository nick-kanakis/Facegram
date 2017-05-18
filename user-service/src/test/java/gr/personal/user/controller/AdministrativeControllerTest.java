package gr.personal.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.personal.user.domain.Gender;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.service.AdministrativeService;
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
        UserRequest userRequest = new UserRequest();
        userRequest.setGender(Gender.FEMALE);
        userRequest.setId("1");
        userRequest.setName("test");
        userRequest.setSurname("test");

        when(administrativeService.createUser(any(UserRequest.class))).thenReturn("OK");

        mockMvc.perform(post("/administrative/createUser")
                .content(asJsonString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setGender(Gender.FEMALE);
        userRequest.setId("1");
        userRequest.setName("test");
        userRequest.setSurname("test");

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

        User user = new User();
        user.setGender(Gender.FEMALE);
        user.setId("1");
        user.setName("test");
        user.setSurname("test");
        user.setFriendIDs(new ArrayList<>());

        when(administrativeService.retrieveUser(anyString())).thenReturn(user);

        mockMvc.perform(get("/administrative/retrieveUser/testUserId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void shouldAddFriend() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setGender(Gender.FEMALE);
        userRequest.setId("1");
        userRequest.setName("test");
        userRequest.setSurname("test");

        when(administrativeService.addFriend(anyString(), any(UserRequest.class))).thenReturn("OK");

        mockMvc.perform(post("/administrative/addFriend/testUserId")
                .content(asJsonString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));

    }

    @Test
    public void shouldRemoveFriend() throws Exception {

        when(administrativeService.removeFriend(anyString(),anyString())).thenReturn("OK");

        mockMvc.perform(delete("/administrative/removeFriend/testUserId/testFriendId"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "OK".equals(mvcResult));
    }

    @Test
    public void shouldRetrieveFriends() throws Exception {

        User user = new User();
        user.setGender(Gender.FEMALE);
        user.setId("1");
        user.setName("test");
        user.setSurname("test");
        user.setFriendIDs(new ArrayList<>());

        List<User> users = new ArrayList<>();
        users.add(user);

        when(administrativeService.retrieveFriends(anyString())).thenReturn(users);

        mockMvc.perform(get("/administrative/retrieveFriends/testUserId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user.getId()));

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
