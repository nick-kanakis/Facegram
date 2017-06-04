package gr.personal.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import gr.personal.auth.domain.User;
import gr.personal.auth.service.UserService;
import gr.personal.auth.setting.OAuth2AuthorizationConfig;
import gr.personal.auth.setting.WebSecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nick Kanakis on 4/6/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@ActiveProfiles("noEureka")
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void shouldNotReturnCurrentUser() throws Exception {
        mockMvc.perform(get("/users/current")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("unauthorized")));
    }

    private String getAccessToken(String username, String password) throws Exception {
        String authorization = "Basic "
                + new String(Base64Utils.encode("story-service:STORY_SERVICE_PASSWORD".getBytes()));

        String content = mockMvc
                .perform(
                        post("/oauth/token")
                                .header("Authorization", authorization)
                                .contentType(
                                        MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", username)
                                .param("password", password)
                                .param("grant_type", "password")
                                .param("scope", "server")
                                .param("client_id", "story-service")
                                .param("client_secret", "STORY_SERVICE_PASSWORD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                .andExpect(jsonPath("$.scope", is(equalTo("read write"))))
                .andReturn().getResponse().getContentAsString();


        return content.substring(17, 53);
    }

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        String accessToken = getAccessToken("roy", "spring");
        mockMvc.perform(get("/users/current").header("Authorization", "Bearer " + accessToken))
                .andExpect(jsonPath("$.user").value("test"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewUser() throws Exception {

        final User user = new User("testUsername","testPassword");


       String json = mapper.writeValueAsString(user);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

}
