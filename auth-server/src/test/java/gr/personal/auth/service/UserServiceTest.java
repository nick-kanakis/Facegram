package gr.personal.auth.service;

import gr.personal.auth.domain.User;
import gr.personal.auth.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;

/**
 * Created by Nick Kanakis on 4/6/2017.
 */
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    private User user;

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Before
    public void setUp() throws Exception {
        user = new User("testUsername", "testPassword", new ArrayList<>());
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
        Mockito.when(userRepository.findByUsername("myUsername")).thenReturn(null);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        userService.create(new User("myUsername", "myPass", new ArrayList<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateUser() throws Exception {
        userService.create(new User("myUsername2", "myPass2", new ArrayList<>()));
    }

    @Test
    public void shouldLoadUserByUsername() throws Exception {
        UserDetails retrievedUser = userService.loadUserByUsername(user.getUsername());
        assertThat(retrievedUser, is(user));
    }
}
