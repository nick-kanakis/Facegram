package gr.personal.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import gr.personal.auth.domain.User;
import gr.personal.auth.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nick Kanakis on 4/6/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("noEureka")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        mockMvc.perform(get("/users/current").principal(new UserPrincipal("test")))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewUser() throws Exception {

        final User user = new User("testUsername","testPassword", new ArrayList<>());
       String json = mapper.writeValueAsString(user);

        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

}
